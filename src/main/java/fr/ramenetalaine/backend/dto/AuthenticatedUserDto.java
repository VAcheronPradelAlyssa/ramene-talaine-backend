package fr.ramenetalaine.backend.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticatedUserDto {
    private String id;
    private String prenom;
    private String nom;
    private String email;
    private String surnom;
    private String ville;
    private String img;
    private LocalDateTime createdAt;
}
