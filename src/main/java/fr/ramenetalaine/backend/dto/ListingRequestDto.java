package fr.ramenetalaine.backend.dto;

import fr.ramenetalaine.backend.model.ListingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListingRequestDto {

    @NotBlank
    private String title;

    private String description;
    private String brand;
    private String composition;
    private String color;
    private Integer weight;
    private Integer length;

    @NotNull
    private ListingType type;

    private Double price;
    private String city;
    private String postalCode;
}
