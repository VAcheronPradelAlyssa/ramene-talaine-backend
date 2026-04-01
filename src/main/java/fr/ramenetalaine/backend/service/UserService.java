package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.dto.UserSignupDto;
import fr.ramenetalaine.backend.model.User;
import fr.ramenetalaine.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(UserSignupDto dto) {
        User user = User.builder()
                .prenom(dto.getPrenom())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .password(dto.getPassword()) // à encoder !
                .surnom(dto.getSurnom())
                .ville(dto.getVille())
                .img(dto.getImg())
                .build();
        return userRepository.save(user);
    }

}