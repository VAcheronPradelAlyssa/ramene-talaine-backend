package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.ListingColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingColorRepository extends JpaRepository<ListingColor, Long> {
}
