package fr.ramenetalaine.backend.dto;

import fr.ramenetalaine.backend.model.ListingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import fr.ramenetalaine.backend.dto.CompositionRequestDto;
import lombok.Data;

@Data
public class ListingRequestDto {

    @NotBlank
    private String title;

    private String description;

    // Soit brandId, soit customBrand (au moins un requis)
    private Long brandId;
    private String customBrand;

    private String composition;
    private List<ListingColorRequestDto> colors;
    private Integer weight;
    private Integer length;

    @NotNull
    private ListingType type;

    private Double price;
    private String city;
    private String postalCode;
    private List<String> imageUrls;

    // Ajout pour compositions
    private List<CompositionRequestDto> compositions;
}
