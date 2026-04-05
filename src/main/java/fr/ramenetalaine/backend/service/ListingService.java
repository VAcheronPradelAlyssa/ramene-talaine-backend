            public Listing updateListingByIdForUser(Long id, fr.ramenetalaine.backend.dto.ListingUpdateRequestDto dto, User user) {
                Listing listing = listingRepository.findById(id)
                        .orElseThrow(() -> new ListingNotFoundException(id));
                if (listing.getSeller() == null || !listing.getSeller().getId().equals(user.getId())) {
                    throw new SecurityException("Vous n'êtes pas autorisé à modifier cette annonce.");
                }
                if (dto.getTitle() != null) listing.setTitle(dto.getTitle());
                if (dto.getDescription() != null) listing.setDescription(dto.getDescription());
                if (dto.getPrice() != null) listing.setPrice(dto.getPrice());
                if (dto.getCity() != null) listing.setCity(dto.getCity());
                if (dto.getPostalCode() != null) listing.setPostalCode(dto.getPostalCode());
                if (dto.getWeight() != null) listing.setWeight(dto.getWeight());
                if (dto.getLength() != null) listing.setLength(dto.getLength());
                if (dto.getCustomBrand() != null) listing.setCustomBrand(dto.getCustomBrand());
                if (dto.getComposition() != null) listing.setComposition(dto.getComposition());
                if (dto.getImageUrls() != null) listing.setImageUrls(dto.getImageUrls());
                // Ajoute d'autres champs modifiables si besoin
                return listingRepository.save(listing);
            }
        public void deleteListingByIdForUser(Long id, User user) {
            Listing listing = listingRepository.findById(id)
                    .orElseThrow(() -> new ListingNotFoundException(id));
            if (listing.getSeller() == null || !listing.getSeller().getId().equals(user.getId())) {
                throw new SecurityException("Vous n'êtes pas autorisé à supprimer cette annonce.");
            }
            listingRepository.deleteById(id);
        }
    public List<Listing> getListingsForUser(User user) {
        return listingRepository.findBySeller(user);
    }
package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.*;
import fr.ramenetalaine.backend.repository.*;
import fr.ramenetalaine.backend.exception.ListingNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final BrandRepository brandRepository;

    public Listing createListing(Listing listing) {
        // Validation simple : au moins brand ou customBrand
        if (listing.getBrand() == null && (listing.getCustomBrand() == null || listing.getCustomBrand().isBlank())) {
            throw new IllegalArgumentException("brandId ou customBrand doit être renseigné");
        }
        applyPriceRules(listing);
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException(id));
    }

    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new ListingNotFoundException(id);
        }
        listingRepository.deleteById(id);
    }

    private void applyPriceRules(Listing listing) {
        if (listing.getType() != ListingType.SALE) {
            listing.setPrice(null);
        }
    }
}
