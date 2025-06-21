package com.example.demo.dto;

import lombok.Data;

@Data
public class CageUpdateDTO {
    private String name;
    private String location;
    private Integer capacity;
    private String description;
}
