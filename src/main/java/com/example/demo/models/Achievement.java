package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private String icon;
    private Integer rewardCoins;
    private String category;
    private Boolean isActive;

    @ManyToMany(mappedBy = "achievements")
    @JsonIgnore
    private Set<Character> characters;
}
