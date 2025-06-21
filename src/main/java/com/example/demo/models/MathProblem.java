package com.example.demo.models;

import com.example.demo.common.enums.ProblemType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class MathProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProblemType type;

    private String question;

    private Double correctAnswer;

    @ManyToOne
    @JoinColumn(name = "cage_id")
    private Cage cage;

    @OneToMany(mappedBy = "mathProblem")
    private List<ProblemMatch> problemMatches;
}