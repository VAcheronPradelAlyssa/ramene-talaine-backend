package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.ListingComposition;
import fr.ramenetalaine.backend.repository.ListingCompositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingCompositionService {
    private final ListingCompositionRepository listingCompositionRepository;

    public List<ListingComposition> getByListingId(Long listingId) {
        return listingCompositionRepository.findAll().stream()
                .filter(lc -> lc.getListing().getId().equals(listingId))
                .toList();
    }

    public ListingComposition save(ListingComposition lc) {
        return listingCompositionRepository.save(lc);
    }
}
