package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositionRepository extends JpaRepository<Composition, Long> {
}
