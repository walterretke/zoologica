package com.example.demo.services.animal;

import com.example.demo.dto.AnimalDTO;
import com.example.demo.dto.AnimalTemplateDTO;
import com.example.demo.models.Animal;
import com.example.demo.models.AnimalTemplate;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.repositories.AnimalRepository;
import com.example.demo.repositories.AnimalTemplateRepository;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.CharacterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalTemplateRepository animalTemplateRepository;
    private final CageRepository cageRepository;
    private final CharacterRepository characterRepository;

    @Override
    public AnimalDTO buyAnimal(Long templateId, Long cageId) {
        // Buscar template do animal (agora na tabela AnimalTemplate)
        AnimalTemplate template = animalTemplateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Animal template not found with id: " + templateId));

        // Buscar jaula
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        // VALIDAR COMPATIBILIDADE: template deve ser do mesmo tipo da jaula
        if (!isAnimalCompatibleWithCage(template.getCageType().getName(), cage.getCageType().getName())) {
            throw new RuntimeException("Animal type " + template.getCageType().getDisplayName() +
                    " is not compatible with cage type " + cage.getCageType().getDisplayName() +
                    ". You can only put " + cage.getCageType().getDisplayName() + " animals in this cage.");
        }

        // Verificar se a jaula pode receber mais animais (máximo 10)
        if (!cage.canAddMoreAnimals()) {
            throw new RuntimeException("This cage is full! Maximum 10 animals per cage.");
        }

        Character character = cage.getCharacter();

        // Verificar se o personagem tem moedas suficientes
        if (character.getTotalCoins() < template.getBasePrice()) {
            throw new RuntimeException("Insufficient coins. Required: " + template.getBasePrice() +
                    ", Available: " + character.getTotalCoins());
        }

        // Deduzir as moedas do personagem
        character.setTotalCoins(character.getTotalCoins() - template.getBasePrice());
        characterRepository.save(character);

        // Criar o animal comprado usando o construtor
        Animal newAnimal = new Animal(template, cage);

        Animal savedAnimal = animalRepository.save(newAnimal);

        return convertToDTO(savedAnimal);
    }

    @Override
    public List<AnimalTemplateDTO> getAvailableAnimals() {
        List<AnimalTemplate> templates = animalTemplateRepository.findAll();

        return templates.stream()
                .map(this::convertToTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalTemplateDTO> getCompatibleAnimals(Long cageId) {
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        // Buscar apenas animais compatíveis com o tipo da jaula
        List<AnimalTemplate> compatibleTemplates = animalTemplateRepository.findByCageType(cage.getCageType());

        return compatibleTemplates.stream()
                .map(this::convertToTemplateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDTO> findByCageId(Long cageId) {
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        List<Animal> animals = animalRepository.findByCage(cage);

        return animals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnimalDTO> findByCharacterId(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<Animal> animals = character.getCages().stream()
                .flatMap(cage -> cage.getAnimals().stream())
                .collect(Collectors.toList());

        return animals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para validar compatibilidade por nome do tipo
    private boolean isAnimalCompatibleWithCage(String animalTypeName, String cageTypeName) {
        return animalTypeName.equals(cageTypeName);
    }

    private AnimalDTO convertToDTO(Animal animal) {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setId(animal.getId());
        animalDTO.setName(animal.getName());
        animalDTO.setBasePrice(animal.getPurchasePrice());

        if (animal.getCage() != null) {
            animalDTO.setCageId(animal.getCage().getId());
        }

        return animalDTO;
    }

    private AnimalTemplateDTO convertToTemplateDTO(AnimalTemplate template) {
        AnimalTemplateDTO dto = new AnimalTemplateDTO();
        dto.setId(template.getId());
        dto.setName(template.getName());
        dto.setBasePrice(template.getBasePrice());
        dto.setDescription(template.getDescription());
        dto.setCageTypeId(template.getCageType().getId());
        dto.setCageTypeName(template.getCageType().getDisplayName());

        return dto;
    }
}