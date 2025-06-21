package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "animal_template")
public class AnimalTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer basePrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cage_type_id", nullable = false)
    private CageType cageType;

    // Construtor para DataLoader
    public AnimalTemplate(String name, Integer basePrice, String description, CageType cageType) {
        this.name = name;
        this.basePrice = basePrice;
        this.description = description;
        this.cageType = cageType;
    }

    public AnimalTemplate() {}
}