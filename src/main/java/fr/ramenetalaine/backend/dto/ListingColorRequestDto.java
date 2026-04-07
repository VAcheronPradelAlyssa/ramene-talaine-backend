package fr.ramenetalaine.backend.dto;

import lombok.Data;

@Data
public class ListingColorRequestDto {
    private Long colorId;      // id d'une couleur connue
    private String customColor; // nom libre si couleur personnalisée
}
