package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.Listing;
import fr.ramenetalaine.backend.model.ListingType;
import fr.ramenetalaine.backend.repository.ListingRepository;
import fr.ramenetalaine.backend.exception.ListingNotFoundException;
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
