package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.CurrentUserDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = extractBearerToken(authorizationHeader);
            User currentUser = authenticationService.getCurrentUser(token);

            CurrentUserDto response = CurrentUserDto.builder()
                    .id(currentUser.getId())
                    .prenom(currentUser.getPrenom())
                    .nom(currentUser.getNom())
                    .surnom(currentUser.getSurnom())
                    .email(currentUser.getEmail())
                    .createdAt(currentUser.getCreatedAt())
                    .build();

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Header Authorization invalide");
        }
        return authorizationHeader.substring(7).trim();
    }
}
