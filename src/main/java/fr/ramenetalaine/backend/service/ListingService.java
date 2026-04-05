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
