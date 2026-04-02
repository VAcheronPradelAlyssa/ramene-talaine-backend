package fr.ramenetalaine.backend.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentUserDto {
    private String id;
    private String prenom;
    private String nom;
    private String surnom;
    private String email;
    private LocalDateTime createdAt;
}
