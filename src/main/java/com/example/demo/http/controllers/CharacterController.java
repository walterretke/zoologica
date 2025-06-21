package com.example.demo.http.controllers;

import com.example.demo.dto.CharacterDTO;
import com.example.demo.services.character.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/create")
    public ResponseEntity<CharacterDTO> create(@RequestBody CharacterDTO dto) {
        CharacterDTO createdCharacter = characterService.create(dto);
        return ResponseEntity.ok(createdCharacter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> findById(@PathVariable Long id) {
        CharacterDTO character = characterService.findById(id);
        return ResponseEntity.ok(character);
    }

    @PutMapping("/{id}/coins")
    public ResponseEntity<CharacterDTO> updateCoins(@PathVariable Long id, @RequestParam Integer coins) {
        CharacterDTO updatedCharacter = characterService.updateCoins(id, coins);
        return ResponseEntity.ok(updatedCharacter);
    }
}