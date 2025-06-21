package com.example.demo.services.character;

import com.example.demo.dto.CharacterDTO;

public interface CharacterService {

    CharacterDTO create(CharacterDTO characterDTO);

    CharacterDTO findById(Long id);

    CharacterDTO updateCoins(Long id, Integer coins);
}