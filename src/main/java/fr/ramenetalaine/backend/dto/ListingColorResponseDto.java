package fr.ramenetalaine.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListingColorResponseDto {
    private Long colorId;      // id d'une couleur connue
    private String customColor; // nom libre si couleur personnalisée
    private String colorName;   // nom de la couleur si connue
    private String groupName;   // groupe de la couleur si connue
}
