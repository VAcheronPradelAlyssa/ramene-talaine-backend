package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "composition")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
