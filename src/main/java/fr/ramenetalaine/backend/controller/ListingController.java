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
                image
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
                .color(request.getColor())
                .weight(request.getWeight())
                .length(request.getLength())
                .type(request.getType())
                .price(request.getPrice())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .imageUrls(request.getImageUrls())
                .seller(seller)
                .build();
        return listing;
    }

    private ListingResponseDto toResponseDto(Listing listing) {
        BrandDto brandDto = null;
        if (listing.getBrand() != null) {
            brandDto = new BrandDto(listing.getBrand().getId(), listing.getBrand().getName());
        }
        return ListingResponseDto.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .brand(brandDto)
                .customBrand(listing.getCustomBrand())
                .composition(listing.getComposition())
                .color(listing.getColor())
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
