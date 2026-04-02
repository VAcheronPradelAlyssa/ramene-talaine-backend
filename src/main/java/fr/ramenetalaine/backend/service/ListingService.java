package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.Listing;
import fr.ramenetalaine.backend.model.ListingType;
import fr.ramenetalaine.backend.repository.ListingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    public Listing createListing(Listing listing) {
        applyPriceRules(listing);
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Annonce introuvable"));
    }

    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new IllegalArgumentException("Annonce introuvable");
        }
        listingRepository.deleteById(id);
    }

    private void applyPriceRules(Listing listing) {
        if (listing.getType() != ListingType.SALE) {
            listing.setPrice(null);
        }
    }
}
