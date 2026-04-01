package fr.ramenetalaine.backend.repository;

import java.util.Optional;
import fr.ramenetalaine.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySurnom(String surnom);
    
}
