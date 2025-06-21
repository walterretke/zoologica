package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CageDTO {
    private Long id;
    private Long characterId;
    private Long cageTypeId;
    private String cageTypeName;
    private String cageTypeDisplayName;
    private Integer difficultyLevel;
    private Integer purchasePrice;
    private LocalDateTime purchaseDate;
    private List<Long> animalIds;
    private Integer animalCount;
    private Double animalMultiplier;
    private String mathTopics;
}