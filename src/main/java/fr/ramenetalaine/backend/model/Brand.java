package fr.ramenetalaine.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Brand(String name) {
        this.name = name;
    }
}
