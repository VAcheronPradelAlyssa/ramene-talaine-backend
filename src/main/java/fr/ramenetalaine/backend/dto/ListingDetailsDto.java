package fr.ramenetalaine.backend.dto;

import fr.ramenetalaine.backend.model.ListingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingDetailsDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String city;
    private String postalCode;
    private Integer weight;
    private Integer length;
    private BrandDto brand;
    private String customBrand;
    private String username;
    private LocalDateTime createdAt;
    private String image;
    private List<String> imageUrls;
    private List<ListingColorResponseDto> colors;
    private ListingType type;

    // Ajouté : liste des compositions (matériaux)
    private List<CompositionResponseDto> compositions;
}
