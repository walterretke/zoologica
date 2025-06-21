package com.example.demo.dto;

import lombok.Data;

@Data
public class CageTypeDTO {
    private Long id;
    private String name;
    private String displayName;
    private Integer difficultyLevel;
    private Integer basePrice;
    private String mathTopics;
    private String description;
}