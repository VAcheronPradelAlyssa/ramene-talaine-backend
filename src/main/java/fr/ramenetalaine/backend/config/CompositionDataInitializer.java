package fr.ramenetalaine.backend.config;

import fr.ramenetalaine.backend.model.Composition;
import fr.ramenetalaine.backend.repository.CompositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CompositionDataInitializer {
    @Bean
    CommandLineRunner initCompositions(CompositionRepository compositionRepository) {
        return args -> {
            List<String> compositions = List.of(
                "Coton",
                "Laine",
                "Acrylique",
                "Polyester",
                "Soie",
                "Lin"
            );
            for (String name : compositions) {
                compositionRepository.findAll()
                    .stream()
                    .filter(c -> c.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseGet(() -> compositionRepository.save(new Composition(null, name)));
            }
        };
    }
}
