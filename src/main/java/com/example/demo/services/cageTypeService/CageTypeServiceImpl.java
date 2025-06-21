package com.example.demo.services.cageTypeService;

import com.example.demo.dto.CageTypeDTO;
import com.example.demo.models.CageType;
import com.example.demo.repositories.CageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CageTypeServiceImpl implements CageTypeService {

    private final CageTypeRepository cageTypeRepository;

    @Override
    public List<CageTypeDTO> getAllCageTypes() {
        List<CageType> cageTypes = cageTypeRepository.findAll();

        return cageTypes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CageTypeDTO findById(Long id) {
        CageType cageType = cageTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cage type not found with id: " + id));

        return convertToDTO(cageType);
    }

    private CageTypeDTO convertToDTO(CageType cageType) {
        CageTypeDTO dto = new CageTypeDTO();
        dto.setId(cageType.getId());
        dto.setName(cageType.getName());
        dto.setDisplayName(cageType.getDisplayName());
        dto.setDifficultyLevel(cageType.getDifficultyLevel());
        dto.setBasePrice(cageType.getBasePrice());
        dto.setMathTopics(cageType.getMathTopics());
        dto.setDescription(cageType.getDescription());

        return dto;
    }
}
