package com.example.demo.services.mathProblem;

import com.example.demo.dto.MathProblemDTO;
import com.example.demo.common.enums.ProblemType;

import java.util.List;

public interface MathProblemService {

    MathProblemDTO create(MathProblemDTO mathProblemDTO);

    List<MathProblemDTO> findByCageId(Long cageId);

    MathProblemDTO findById(Long id);

    MathProblemDTO generateRandomProblem(ProblemType type, Integer difficulty, Long cageId);
}