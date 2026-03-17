package fr.ramenetalaine.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
               .allowedOrigins("http://localhost:4200", "https://ton-domaine-en-prod.fr") // Autoriser toutes les origines (vous pouvez spécifier des origines spécifiques si nécessaire)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Autoriser les méthodes HTTP nécessaires
                .allowedHeaders("*") // Autoriser tous les en-têtes (vous pouvez spécifier des en-têtes spécifiques si nécessaire)
                .allowCredentials(true);   
             }
    
}
