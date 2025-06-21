package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer coinPrice;

    private String visualDescription;

    @OneToOne(mappedBy = "outfit")
    private Character character;
}