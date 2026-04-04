package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.*;
import fr.ramenetalaine.backend.model.*;
import fr.ramenetalaine.backend.repository.BrandRepository;
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

    @PostMapping
    public ResponseEntity<?> createListing(@Valid @RequestBody ListingRequestDto request) {
        try {
            Listing listingToCreate = toEntity(request);
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
    public ResponseEntity<ListingResponseDto> getListingById(@PathVariable Long id) {
        Listing listing = listingService.getListingById(id);
        return ResponseEntity.ok(toResponseDto(listing));
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

    private Listing toEntity(ListingRequestDto request) {
        Brand brand = null;
        if (request.getBrandId() != null) {
            brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Marque non trouvée"));
        }
        return Listing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .brand(brand)
                .customBrand(request.getCustomBrand())
                .composition(request.getComposition())
                .color(request.getColor())
                .weight(request.getWeight())
                .length(request.getLength())
                .type(request.getType())
                .price(request.getPrice())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .imageUrls(request.getImageUrls())
                .build();
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
