package com.example.demo.http.controllers;

import com.example.demo.dto.CageDTO;
import com.example.demo.services.cage.CageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/cage")
public class CageController {

    private final CageService cageService;

    @PostMapping("/create")
    public ResponseEntity<CageDTO> create(@RequestBody CageDTO cageDTO) {
        CageDTO createdCageDTO = cageService.create(cageDTO);
        return ResponseEntity.ok(createdCageDTO);
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<CageDTO>> findByCharacterId(@PathVariable Long characterId) {
        List<CageDTO> cages = cageService.findByCharacterId(characterId);
        return ResponseEntity.ok(cages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CageDTO> findById(@PathVariable Long id) {
        CageDTO cage = cageService.findById(id);
        return ResponseEntity.ok(cage);
    }
}