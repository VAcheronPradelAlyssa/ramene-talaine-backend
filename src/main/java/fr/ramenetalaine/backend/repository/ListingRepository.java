package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import fr.ramenetalaine.backend.model.User;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
	@Transactional(readOnly = true)
	List<Listing> findBySeller(User seller);
}
