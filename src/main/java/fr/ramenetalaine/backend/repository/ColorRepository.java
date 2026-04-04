package fr.ramenetalaine.backend.repository;

import fr.ramenetalaine.backend.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
