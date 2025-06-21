package com.example.demo.dto;

import lombok.Data;

@Data
public class CageResponseDTO {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private String description;
}
