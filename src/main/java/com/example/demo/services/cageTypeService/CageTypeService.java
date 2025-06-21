package com.example.demo.services.cageTypeService;

import com.example.demo.dto.CageTypeDTO;

import java.util.List;

public interface CageTypeService {
    List<CageTypeDTO> getAllCageTypes();
    CageTypeDTO findById(Long id);
}
