package com.example.demo.dto;

import lombok.Data;

@Data
public class AnimalTemplateDTO {
    private Long id;
    private String name;
    private Integer basePrice;
    private String description;
    private Long cageTypeId;
    private String cageTypeName;
}