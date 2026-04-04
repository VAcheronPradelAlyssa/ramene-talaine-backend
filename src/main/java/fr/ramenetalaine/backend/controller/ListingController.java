package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.*;
import fr.ramenetalaine.backend.model.Composition;
import fr.ramenetalaine.backend.model.*;
import fr.ramenetalaine.backend.exception.ListingNotFoundException;
import fr.ramenetalaine.backend.repository.BrandRepository;
import fr.ramenetalaine.backend.repository.UserRepository;
import fr.ramenetalaine.backend.service.AuthenticationService;
import fr.ramenetalaine.backend.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {
    private final ListingService listingService;
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final fr.ramenetalaine.backend.repository.CompositionRepository compositionRepository;
    private final fr.ramenetalaine.backend.repository.ColorRepository colorRepository;

    @PostMapping
    public ResponseEntity<?> createListing(@Valid @RequestBody ListingRequestDto request, @RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Récupère le token (suppose format "Bearer <token>")
            String token = authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
                    ? authorizationHeader.substring(7)
                    : authorizationHeader;
            User currentUser = authenticationService.getCurrentUser(token);
            Listing listingToCreate = toEntity(request, currentUser);
            Listing created = listingService.createListing(listingToCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ListingResponseDto>> getAllListings() {
        List<ListingResponseDto> response = listingService.getAllListings()
                .stream()
                .map(this::toResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDetailsDto> getListingDetailsById(@PathVariable Long id) {
        try {
            Listing listing = listingService.getListingById(id);
            BrandDto brandDto = null;
            if (listing.getBrand() != null) {
                brandDto = new BrandDto(listing.getBrand().getId(), listing.getBrand().getName());
            }
            String username = listing.getSeller() != null ? listing.getSeller().getSurnom() : null;
            String image = (listing.getImageUrls() != null && !listing.getImageUrls().isEmpty()) ? listing.getImageUrls().get(0) : null;

            // Construction de la liste des compositions (matériaux)
            List<CompositionResponseDto> compositions = new ArrayList<>();
            if (listing.getListingCompositions() != null) {
                for (ListingComposition lc : listing.getListingCompositions()) {
                    if (lc.getComposition() != null) {
                        compositions.add(new CompositionResponseDto(
                                lc.getComposition().getId(),
                                lc.getComposition().getName(),
                                lc.getPercentage()
                        ));
                    }
                }
            }

            ListingDetailsDto dto = new ListingDetailsDto(
                listing.getId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getCity(),
                listing.getPostalCode(),
                listing.getWeight(),
                listing.getLength(),
                brandDto,
                listing.getCustomBrand(),
                username,
                listing.getCreatedAt(),
                image,
                compositions
            );
            return ResponseEntity.ok(dto);
        } catch (ListingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(@PathVariable Long id) {
        try {
            listingService.deleteListing(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private Listing toEntity(ListingRequestDto request, User seller) {
        Brand brand = null;
        String customBrand = request.getCustomBrand();
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Marque non trouvée"));
        } else if (customBrand != null && !customBrand.isBlank()) {
            brand = brandRepository.findByName(customBrand.trim())
                    .orElseGet(() -> brandRepository.save(new Brand(customBrand.trim())));
        }
        Listing listing = Listing.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .brand(brand)
            .customBrand((customBrand != null && !customBrand.isBlank()) ? customBrand.trim() : null)
            .weight(request.getWeight())
            .length(request.getLength())
            .type(request.getType())
            .price(request.getPrice())
            .city(request.getCity())
            .postalCode(request.getPostalCode())
            .imageUrls(request.getImageUrls())
            .seller(seller)
            .build();

        // Ajout des couleurs si présentes dans le DTO
        if (request.getColors() != null && !request.getColors().isEmpty()) {
            List<ListingColor> listingColors = new ArrayList<>();
            for (ListingColorRequestDto colorDto : request.getColors()) {
                ListingColor lc = new ListingColor();
                lc.setListing(listing);
                if (colorDto.getColorId() != null) {
                    Color color = colorRepository.findById(colorDto.getColorId())
                        .orElseThrow(() -> new IllegalArgumentException("Color non trouvée pour id : " + colorDto.getColorId()));
                    lc.setColor(color);
                }
                if (colorDto.getCustomColor() != null && !colorDto.getCustomColor().isBlank()) {
                    lc.setCustomColor(colorDto.getCustomColor().trim());
                }
                if (lc.getColor() == null && (lc.getCustomColor() == null || lc.getCustomColor().isBlank())) {
                    throw new IllegalArgumentException("Chaque couleur doit avoir un colorId ou un customColor non vide");
                }
                listingColors.add(lc);
            }
            listing.setListingColors(listingColors);
        }

        // Ajout des compositions si présentes dans le DTO (par id)
        if (request.getCompositions() != null && !request.getCompositions().isEmpty()) {
            List<ListingComposition> listingCompositions = new ArrayList<>();
            for (CompositionRequestDto compoDto : request.getCompositions()) {
                if (compoDto.getCompositionId() == null) {
                    throw new IllegalArgumentException("compositionId manquant dans CompositionRequestDto");
                }
                Composition composition = compositionRepository.findById(compoDto.getCompositionId())
                        .orElseThrow(() -> new IllegalArgumentException("Composition non trouvée pour id : " + compoDto.getCompositionId()));
                ListingComposition lc = new ListingComposition();
                lc.setListing(listing);
                lc.setComposition(composition);
                lc.setPercentage(compoDto.getPercentage());
                listingCompositions.add(lc);
            }
            listing.setListingCompositions(listingCompositions);
        }
        return listing;
    }

    private ListingResponseDto toResponseDto(Listing listing) {
        BrandDto brandDto = null;
        if (listing.getBrand() != null) {
            brandDto = new BrandDto(listing.getBrand().getId(), listing.getBrand().getName());
        }
        List<ListingColorResponseDto> colorDtos = new ArrayList<>();
        if (listing.getListingColors() != null) {
            for (ListingColor lc : listing.getListingColors()) {
                ListingColorResponseDto.ListingColorResponseDtoBuilder builder = ListingColorResponseDto.builder();
                if (lc.getColor() != null) {
                    builder.colorId(lc.getColor().getId())
                           .colorName(lc.getColor().getName())
                           .groupName(lc.getColor().getGroupName());
                }
                if (lc.getCustomColor() != null && !lc.getCustomColor().isBlank()) {
                    builder.customColor(lc.getCustomColor());
                }
                colorDtos.add(builder.build());
            }
        }
        return ListingResponseDto.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .brand(brandDto)
                .customBrand(listing.getCustomBrand())
                .composition(listing.getComposition())
                .colors(colorDtos)
                .weight(listing.getWeight())
                .length(listing.getLength())
                .type(listing.getType())
                .price(listing.getPrice())
                .city(listing.getCity())
                .postalCode(listing.getPostalCode())
                .imageUrls(listing.getImageUrls())
                .createdAt(listing.getCreatedAt())
                .build();
    }
}
