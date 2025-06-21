package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer purchasePrice;

    @ManyToOne
    @JoinColumn(name = "cage_id")
    private Cage cage;

    @ManyToOne
    @JoinColumn(name = "animal_template_id", nullable = false)
    private AnimalTemplate template;

    @Column(nullable = false)
    private java.time.LocalDateTime purchaseDate;

    public Animal(AnimalTemplate template, Cage cage) {
        this.name = template.getName();
        this.purchasePrice = template.getBasePrice();
        this.template = template;
        this.cage = cage;
        this.purchaseDate = java.time.LocalDateTime.now();
    }

    public Animal() {}
}