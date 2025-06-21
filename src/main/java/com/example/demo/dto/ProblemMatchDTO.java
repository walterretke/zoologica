package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProblemMatchDTO {
    private Long id;
    private Long characterId;
    private Long problemId;
    private Double givenAnswer;
    private Boolean correct;
    private Integer coinsEarned;
    private Long solutionTime;
    private LocalDateTime dateTime;
    private String message;
}