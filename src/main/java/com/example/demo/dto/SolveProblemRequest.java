package com.example.demo.dto;

import lombok.Data;

@Data
public class SolveProblemRequest {
    private Long characterId;
    private Long problemId;
    private Double givenAnswer;
    private Long solutionTime;
}