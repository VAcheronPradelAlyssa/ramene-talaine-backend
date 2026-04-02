package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.LoginResponseDto;
import fr.ramenetalaine.backend.dto.UserLoginDto;
import fr.ramenetalaine.backend.dto.UserSignupDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.service.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    @PostMapping({"/auth/signup", "/auth/register"})
    public ResponseEntity<?> signup(@RequestBody UserSignupDto dto) {
        try {
            User created = userService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'inscription");
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginAuth(@RequestBody UserLoginDto dto) {
        try {
            LoginResponseDto response = authenticationService.login(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}