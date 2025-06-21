package com.example.demo.models;

import com.example.demo.common.enums.MedalType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RewardSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MedalType medalType;

    private Double multiplier;

    private Long timeLimit; // Note: fixed typo from "timeLimite"

    private String calculationFormula;
}