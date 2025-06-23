package com.example.demo.services.character;

import com.example.demo.dto.CharacterDTO;
import com.example.demo.models.Achievement;
import com.example.demo.models.Character;
import com.example.demo.repositories.CharacterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Override
    public CharacterDTO create(CharacterDTO characterDTO) {
        Character newCharacter = new Character();

        newCharacter.setName(characterDTO.getName());
        newCharacter.setTotalCoins(characterDTO.getTotalCoins() != null ? characterDTO.getTotalCoins() : 100); // moedas iniciais
        newCharacter.setOutfit(null);
        newCharacter.setCurrentCage(null);
        newCharacter.setCages(new ArrayList<>());
        newCharacter.setProblemMatches(new ArrayList<>());

        Character savedCharacter = characterRepository.save(newCharacter);

        return convertToDTO(savedCharacter);
    }


    @Override
    @Transactional
    public CharacterDTO findById(Long id) {
        Character character = characterRepository.findById(id).orElseThrow();

        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setOutfitId(character.getOutfit() != null ? character.getOutfit().getId() : null);
        characterDTO.setCurrentCageId(character.getCurrentCage() != null ? character.getCurrentCage().getId() : null);
        characterDTO.setTotalCoins(character.getTotalCoins());

        List<Long> cageIds = character.getCages() != null ?
                character.getCages().stream().map(cage -> cage.getId()).collect(Collectors.toList()) :
                new ArrayList<>();
        characterDTO.setCagesIds(cageIds);

        List<Long> problemMatchIds = character.getProblemMatches() != null ?
                character.getProblemMatches().stream().map(match -> match.getId()).collect(Collectors.toList()) :
                new ArrayList<>();
        characterDTO.setProblemMatchIds(problemMatchIds);

        List<Long> achievementIds = characterRepository.findAchievementIdsByCharacterId(id);
        characterDTO.setAchievementIds(achievementIds);

        return characterDTO;
    }

    @Override
    public CharacterDTO updateCoins(Long id, Integer coins) {
        Character character = characterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Character not found with id: " + id)
        );

        character.setTotalCoins(coins);
        Character savedCharacter = characterRepository.save(character);

        return convertToDTO(savedCharacter);
    }

    private CharacterDTO convertToDTO(Character character) {
        List<Long> cageIds = character.getCages() != null ?
                character.getCages().stream().map(cage -> cage.getId()).collect(Collectors.toList()) :
                new ArrayList<>();

        List<Long> problemMatchIds = character.getProblemMatches() != null ?
                character.getProblemMatches().stream().map(match -> match.getId()).collect(Collectors.toList()) :
                new ArrayList<>();

        List<Long> achievementIds = character.getAchievements() != null ?
                character.getAchievements().stream().map(achievement -> achievement.getId()).collect(Collectors.toList()) :
                new ArrayList<>();

        return CharacterDTO.builder()
                .id(character.getId())
                .name(character.getName())
                .outfitId(character.getOutfit() != null ? character.getOutfit().getId() : null)
                .currentCageId(character.getCurrentCage() != null ? character.getCurrentCage().getId() : null)
                .totalCoins(character.getTotalCoins())
                .cagesIds(cageIds)
                .problemMatchIds(problemMatchIds)
                .achievementIds(achievementIds)
                .build();
    }
}