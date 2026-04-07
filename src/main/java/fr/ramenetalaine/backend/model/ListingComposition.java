package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "listing_composition")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "listing_id")
    private Listing listing;

    @ManyToOne(optional = false)
    @JoinColumn(name = "composition_id")
    private Composition composition;

    private Integer percentage;
}
