package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "listing")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    private String customBrand;
    private String composition;
    private String color;

    private Integer weight;

    private Integer length;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingType type;

    private Double price;

    private String city;
    private String postalCode;

    @ElementCollection
    @CollectionTable(name = "listing_image_urls", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @AssertTrue(message = "price must be null when type is FREE or EXCHANGE")
    public boolean isPriceConsistentWithType() {
        if (type == null) {
            return true;
        }
        if (type == ListingType.FREE || type == ListingType.EXCHANGE) {
            return price == null;
        }
        return true;
    }
}
