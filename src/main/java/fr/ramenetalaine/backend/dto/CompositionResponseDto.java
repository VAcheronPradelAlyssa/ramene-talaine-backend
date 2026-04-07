package fr.ramenetalaine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompositionResponseDto {
    private Long compositionId;
    private String name;
    private Integer percentage;
}