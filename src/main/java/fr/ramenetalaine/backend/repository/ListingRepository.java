package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.ramenetalaine.backend.model.User;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
	List<Listing> findBySeller(User seller);
}
