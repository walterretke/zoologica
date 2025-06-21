package com.example.demo.dto;

import lombok.Data;

@Data
public class AnimalDTO {
    private Long id;
    private String name;
    private Integer basePrice;
    private Long cageId;
    private String cageTypeName; // Novo campo para mostrar o tipo
}