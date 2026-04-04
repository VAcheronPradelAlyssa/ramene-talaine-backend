package fr.ramenetalaine.backend.dto;

import lombok.Data;

@Data
public class CompositionRequestDto {
    private String material;
    private Integer percentage; // Peut être null
}
