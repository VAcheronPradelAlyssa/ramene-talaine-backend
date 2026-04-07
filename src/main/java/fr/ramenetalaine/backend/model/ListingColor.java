package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "listing_color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    private String customColor;
}
