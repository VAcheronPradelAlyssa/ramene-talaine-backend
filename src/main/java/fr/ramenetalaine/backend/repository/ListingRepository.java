package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
}
