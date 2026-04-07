package fr.ramenetalaine.backend.dto;

import fr.ramenetalaine.backend.model.ListingType;
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
    private Long brandId;
    private String customBrand;
    private ListingType type;
    private String composition;
    private List<ListingColorRequestDto> colors;
    private List<CompositionRequestDto> compositions;
    private List<String> imageUrls;
}
