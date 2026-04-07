package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // ex: cyan, navy, turquoise

    @Column(nullable = false)
    private String groupName; // ex: blue, green, red
}
