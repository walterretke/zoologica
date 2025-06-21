package com.example.demo.services.cage;

import com.example.demo.dto.CageDTO;
import com.example.demo.models.Cage;
import com.example.demo.models.CageType;
import com.example.demo.models.Character;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.CageTypeRepository;
import com.example.demo.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CageServiceImpl implements CageService {

    private final CageRepository cageRepository;
    private final CharacterRepository characterRepository;
    private final CageTypeRepository cageTypeRepository;

    @Override
    public CageDTO create(CageDTO cageDTO) {
        Character character = characterRepository.findById(cageDTO.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + cageDTO.getCharacterId()));

        // Buscar o tipo de jaula
        CageType cageType = cageTypeRepository.findById(cageDTO.getCageTypeId())
                .orElseThrow(() -> new RuntimeException("Cage type not found with id: " + cageDTO.getCageTypeId()));

        // Verificar se o personagem tem moedas suficientes (usar preço do CageType)
        if (character.getTotalCoins() < cageType.getBasePrice()) {
            throw new RuntimeException("Insufficient coins. Required: " + cageType.getBasePrice() +
                    ", Available: " + character.getTotalCoins());
        }

        // Deduzir as moedas do personagem
        character.setTotalCoins(character.getTotalCoins() - cageType.getBasePrice());
        characterRepository.save(character);

        // Criar a jaula
        Cage newCage = new Cage();
        newCage.setCharacter(character);
        newCage.setCageType(cageType);
        newCage.setPurchasePrice(cageType.getBasePrice());
        newCage.setPurchaseDate(LocalDateTime.now());
        newCage.setAnimals(new ArrayList<>());
        newCage.setMathProblems(new ArrayList<>());

        Cage savedCage = cageRepository.save(newCage);

        return convertToDTO(savedCage);
    }

    @Override
    public List<CageDTO> findByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<Cage> cages = cageRepository.findByCharacter(character);

        return cages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CageDTO findById(Long id) {
        Cage cage = cageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + id));

        return convertToDTO(cage);
    }

    private CageDTO convertToDTO(Cage cage) {
        CageDTO dto = new CageDTO();
        dto.setId(cage.getId());
        dto.setCharacterId(cage.getCharacter().getId());
        dto.setCageTypeId(cage.getCageType().getId());
        dto.setCageTypeName(cage.getCageType().getName());
        dto.setCageTypeDisplayName(cage.getCageType().getDisplayName());
        dto.setDifficultyLevel(cage.getCageType().getDifficultyLevel());
        dto.setPurchasePrice(cage.getPurchasePrice());
        dto.setPurchaseDate(cage.getPurchaseDate());
        dto.setMathTopics(cage.getCageType().getMathTopics());

        // Lista de IDs dos animais
        List<Long> animalIds = cage.getAnimals() != null ?
                cage.getAnimals().stream().map(animal -> animal.getId()).collect(Collectors.toList()) :
                new ArrayList<>();
        dto.setAnimalIds(animalIds);

        // Estatísticas dos animais
        dto.setAnimalCount(animalIds.size());
        dto.setAnimalMultiplier(cage.getAnimalMultiplier());

        return dto;
    }
}