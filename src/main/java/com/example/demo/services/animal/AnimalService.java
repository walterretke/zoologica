package com.example.demo.services.animal;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.AnimalTemplateDTO;

import java.util.List;

public interface AnimalService {

    AnimalDTO buyAnimal(Long templateId, Long cageId);

    List<AnimalTemplateDTO> getAvailableAnimals();

    List<AnimalTemplateDTO> getCompatibleAnimals(Long cageId);

    List<AnimalDTO> findByCageId(Long cageId);

    List<AnimalDTO> findByCharacterId(Long characterId);
}