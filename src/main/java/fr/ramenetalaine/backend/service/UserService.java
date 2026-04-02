package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.dto.UserSignupDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserSignupDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        if (userRepository.findBySurnom(dto.getSurnom()).isPresent()) {
            throw new IllegalArgumentException("Surnom déjà utilisé");
        }
        User user = User.builder()
                .prenom(dto.getPrenom())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .surnom(dto.getSurnom())
                .ville(dto.getVille())
                .img(dto.getImg())
                .build();
        return userRepository.save(user);
    }

}