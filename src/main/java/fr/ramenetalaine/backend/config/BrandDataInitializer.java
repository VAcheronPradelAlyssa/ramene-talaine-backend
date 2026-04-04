package fr.ramenetalaine.backend.config;

import fr.ramenetalaine.backend.model.Brand;
import fr.ramenetalaine.backend.repository.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BrandDataInitializer {
    @Bean
    CommandLineRunner initBrands(BrandRepository brandRepository) {
        return args -> {
            List<String> brands = List.of(
                "Phildar",
                "Bergère de France",
                "Katia",
                "Drops",
                "Rico Design"
            );
            for (String name : brands) {
                brandRepository.findByName(name)
                    .orElseGet(() -> brandRepository.save(new Brand(name)));
            }
        };
    }
}
