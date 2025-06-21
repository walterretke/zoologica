package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ProblemMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private MathProblem mathProblem;

    private Long solutionTime; // in milliseconds

    private Double givenAnswer;

    private Boolean correct;

    private Integer coinsEarned;

    private LocalDateTime dateTime;
}