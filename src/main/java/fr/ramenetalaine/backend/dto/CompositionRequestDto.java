package fr.ramenetalaine.backend.dto;

import lombok.Data;

@Data
public class CompositionRequestDto {
    private Long compositionId;
    private Integer percentage; // Peut être null
}
