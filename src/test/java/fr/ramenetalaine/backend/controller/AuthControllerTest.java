package fr.ramenetalaine.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ramenetalaine.backend.dto.UserSignupDto;
import fr.ramenetalaine.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void inscription_succes() throws Exception {
        UserSignupDto dto = new UserSignupDto();
        dto.setPrenom("Alyssa");
        dto.setNom("Test");
        dto.setEmail("alyssa@example.com");
        dto.setPassword("secret");
        dto.setSurnom("aly");
        dto.setVille("Paris");
        dto.setImg("");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("alyssa@example.com"));
    }

    @Test
    void inscription_doublon_email() throws Exception {
        // Création d'un premier utilisateur
        UserSignupDto dto = new UserSignupDto();
        dto.setPrenom("Alyssa");
        dto.setNom("Test");
        dto.setEmail("alyssa@example.com");
        dto.setPassword("secret");
        dto.setSurnom("aly");
        dto.setVille("Paris");
        dto.setImg("");
        userRepository.save(fr.ramenetalaine.backend.model.User.builder()
                .prenom(dto.getPrenom())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .password("hash")
                .surnom(dto.getSurnom())
                .ville(dto.getVille())
                .img(dto.getImg())
                .build());

        // Tentative d'inscription avec le même email
        UserSignupDto doublon = new UserSignupDto();
        doublon.setPrenom("Alyssa2");
        doublon.setNom("Test2");
        doublon.setEmail("alyssa@example.com");
        doublon.setPassword("secret2");
        doublon.setSurnom("aly2");
        doublon.setVille("Lyon");
        doublon.setImg("");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doublon)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Email déjà utilisé"));
    }

    @Test
    void inscription_doublon_surnom() throws Exception {
        // Création d'un premier utilisateur
        UserSignupDto dto = new UserSignupDto();
        dto.setPrenom("Alyssa");
        dto.setNom("Test");
        dto.setEmail("alyssa@example.com");
        dto.setPassword("secret");
        dto.setSurnom("aly");
        dto.setVille("Paris");
        dto.setImg("");
        userRepository.save(fr.ramenetalaine.backend.model.User.builder()
                .prenom(dto.getPrenom())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .password("hash")
                .surnom(dto.getSurnom())
                .ville(dto.getVille())
                .img(dto.getImg())
                .build());

        // Tentative d'inscription avec le même surnom
        UserSignupDto doublon = new UserSignupDto();
        doublon.setPrenom("Alyssa2");
        doublon.setNom("Test2");
        doublon.setEmail("alyssa2@example.com");
        doublon.setPassword("secret2");
        doublon.setSurnom("aly");
        doublon.setVille("Lyon");
        doublon.setImg("");

        mockMvc.perform(post("/api/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doublon)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Surnom déjà utilisé"));
    }
}