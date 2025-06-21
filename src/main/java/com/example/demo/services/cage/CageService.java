package com.example.demo.services.cage;

import com.example.demo.dto.CageDTO;

import java.util.List;

public interface CageService {

    CageDTO create(CageDTO cageDTO);

    List<CageDTO> findByCharacterId(Long characterId);

    CageDTO findById(Long id);
}