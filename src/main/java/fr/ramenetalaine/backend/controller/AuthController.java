package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.UserSignupDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupDto dto) {
        try {
            User created = userService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            // Email déjà utilisé, etc.
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ou surnom déjà utilisé");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'inscription");
        }
    }
}