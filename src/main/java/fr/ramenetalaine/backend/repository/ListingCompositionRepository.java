package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.ListingComposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingCompositionRepository extends JpaRepository<ListingComposition, Long> {
}
