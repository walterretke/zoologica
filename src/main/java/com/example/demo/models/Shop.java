package com.example.demo.models;

import com.example.demo.common.enums.ItemType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    private Long itemId;

    private Boolean available;

    private Integer currentPrice;
}