package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne
    @JoinColumn(name = "cage_type_id", nullable = false)
    private CageType cageType;

    @Column(nullable = false)
    private Integer purchasePrice; // Preço que foi pago

    @Column(nullable = false)
    private java.time.LocalDateTime purchaseDate;

    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    private List<Animal> animals;

    @OneToMany(mappedBy = "cage", cascade = CascadeType.ALL)
    private List<MathProblem> mathProblems;

    public Double getAnimalMultiplier() {
        if (animals == null || animals.isEmpty()) {
            return 1.0;
        }
        return 1.0 + ((animals.size() - 1) * 0.25);
    }

    public boolean canAddMoreAnimals() {
        return animals == null || animals.size() < 10;
    }

    public Cage() {}
}