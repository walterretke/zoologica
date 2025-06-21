package com.example.demo.http.controllers;

import com.example.demo.dto.CageTypeDTO;
import com.example.demo.services.cageTypeService.CageTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cage-type")
public class CageTypeController {

    private final CageTypeService cageTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<CageTypeDTO>> getAllCageTypes() {
        List<CageTypeDTO> cageTypes = cageTypeService.getAllCageTypes();
        return ResponseEntity.ok(cageTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CageTypeDTO> findById(@PathVariable Long id) {
        CageTypeDTO cageType = cageTypeService.findById(id);
        return ResponseEntity.ok(cageType);
    }
}