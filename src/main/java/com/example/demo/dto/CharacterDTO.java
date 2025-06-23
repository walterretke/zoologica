package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {
    private Long id;
    private String name;
    private Long outfitId;
    private Long currentCageId;
    private Integer totalCoins;
    private List<Long> cagesIds;
    private List<Long> problemMatchIds;
    private List<Long> achievementIds;
}