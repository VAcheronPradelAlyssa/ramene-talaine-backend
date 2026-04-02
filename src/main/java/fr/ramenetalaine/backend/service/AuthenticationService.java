package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.dto.AuthenticatedUserDto;
import fr.ramenetalaine.backend.dto.LoginResponseDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe invalide"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Email ou mot de passe invalide");
        }

        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        AuthenticatedUserDto userDto = AuthenticatedUserDto.builder()
                .id(user.getId())
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .email(user.getEmail())
                .surnom(user.getSurnom())
                .ville(user.getVille())
                .img(user.getImg())
                .createdAt(user.getCreatedAt())
                .build();

        return new LoginResponseDto(token, userDto);
    }

    public boolean validateToken(String token) {
        return token != null && tokenStore.containsKey(token);
    }

    public Optional<String> getUserIdFromToken(String token) {
        if (!validateToken(token)) {
            return Optional.empty();
        }
        return Optional.ofNullable(tokenStore.get(token));
    }
}
