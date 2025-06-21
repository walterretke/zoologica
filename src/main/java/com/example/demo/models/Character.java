package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "game_character") // "character" is a reserved keyword in SQL
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;

    private Integer totalCoins;

    @ManyToOne
    @JoinColumn(name = "current_cage_id")
    private Cage currentCage;

    @OneToMany(mappedBy = "character")
    private List<Cage> cages;

    @OneToMany(mappedBy = "character")
    private List<ProblemMatch> problemMatches;
}