package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cage_type")
public class CageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // ELEPHANT, LION, GIRAFFE, MONKEY, ZEBRA

    @Column(nullable = false)
    private String displayName; // "Elefante", "Leão", etc.

    @Column(nullable = false)
    private Integer difficultyLevel; // 1-5

    @Column(nullable = false)
    private Integer basePrice; // Preço base da jaula

    @Column(nullable = false)
    private String mathTopics; // "Soma, Subtração", "Multiplicação, Divisão", etc.

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "cageType", cascade = CascadeType.ALL)
    private List<Cage> cages;

    @OneToMany(mappedBy = "cageType", cascade = CascadeType.ALL)
    private List<AnimalTemplate> animalTemplates;

    // Construtor para o DataLoader
    public CageType(String name, String displayName, Integer difficultyLevel,
                    Integer basePrice, String mathTopics, String description) {
        this.name = name;
        this.displayName = displayName;
        this.difficultyLevel = difficultyLevel;
        this.basePrice = basePrice;
        this.mathTopics = mathTopics;
        this.description = description;
    }

    public CageType() {}
}