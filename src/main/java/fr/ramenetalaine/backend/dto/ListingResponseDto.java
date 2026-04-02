package fr.ramenetalaine.backend.dto;

import fr.ramenetalaine.backend.model.ListingType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListingResponseDto {
    private Long id;
    private String title;
    private String description;
    private String brand;
    private String composition;
    private String color;
    private Integer weight;
    private Integer length;
    private ListingType type;
    private Double price;
    private String city;
    private String postalCode;
    private List<String> imageUrls;
    private LocalDateTime createdAt;
}
