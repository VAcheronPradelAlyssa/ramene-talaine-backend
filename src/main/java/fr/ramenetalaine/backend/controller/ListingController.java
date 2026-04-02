package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.ListingRequestDto;
import fr.ramenetalaine.backend.dto.ListingResponseDto;
import fr.ramenetalaine.backend.model.Listing;
import fr.ramenetalaine.backend.service.ListingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

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
    public ResponseEntity<?> getListingById(@PathVariable Long id) {
        try {
            Listing listing = listingService.getListingById(id);
            return ResponseEntity.ok(toResponseDto(listing));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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

    private Listing toEntity(ListingRequestDto request) {
        return Listing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .brand(request.getBrand())
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
        return ListingResponseDto.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .brand(listing.getBrand())
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
