package com.example.demo.http.controllers;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.AnimalTemplateDTO;
import com.example.demo.services.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/buy")
    public ResponseEntity<AnimalDTO> buyAnimal(@RequestParam Long templateId, @RequestParam Long cageId) {
        AnimalDTO boughtAnimal = animalService.buyAnimal(templateId, cageId);
        return ResponseEntity.ok(boughtAnimal);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<AnimalTemplateDTO>> getAvailableAnimals() {
        List<AnimalTemplateDTO> templates = animalService.getAvailableAnimals();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/templates/cage/{cageId}")
    public ResponseEntity<List<AnimalTemplateDTO>> getCompatibleAnimals(@PathVariable Long cageId) {
        List<AnimalTemplateDTO> compatibleAnimals = animalService.getCompatibleAnimals(cageId);
        return ResponseEntity.ok(compatibleAnimals);
    }

    @GetMapping("/cage/{cageId}")
    public ResponseEntity<List<AnimalDTO>> findByCageId(@PathVariable Long cageId) {
        List<AnimalDTO> animals = animalService.findByCageId(cageId);
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<AnimalDTO>> findByCharacterId(@PathVariable Long characterId) {
        List<AnimalDTO> animals = animalService.findByCharacterId(characterId);
        return ResponseEntity.ok(animals);
    }
}