package fr.ramenetalaine.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class ListingUpdateRequestDto {
    private String title;
    private String description;
    private Double price;
    private String city;
    private String postalCode;
    private Integer weight;
    private Integer length;
    private String customBrand;
    private String composition;
    private List<String> imageUrls;
    // Ajoute d'autres champs modifiables si besoin
}
