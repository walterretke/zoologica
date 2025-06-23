package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "game_character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;

    private Integer totalCoins;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "character_achievements",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private Set<Achievement> achievements;

    @ManyToOne
    @JoinColumn(name = "current_cage_id")
    private Cage currentCage;

    @OneToMany(mappedBy = "character")
    private List<Cage> cages;

    @OneToMany(mappedBy = "character")
    private List<ProblemMatch> problemMatches;
}