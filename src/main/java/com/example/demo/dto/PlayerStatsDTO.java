package com.example.demo.dto;

import com.example.demo.models.Achievement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerStatsDTO {
    private Long characterId;
    private Long totalAttempts;
    private Long correctAnswers;
    private Long incorrectAnswers;
    private Double accuracy;
    private List<Achievement> achievements;
}