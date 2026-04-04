package fr.ramenetalaine.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}
