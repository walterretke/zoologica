package com.example.demo.services.problemMatch;

import com.example.demo.dto.ProblemMatchDTO;
import com.example.demo.dto.SolveProblemRequest;

import java.util.List;

public interface ProblemMatchService {

    ProblemMatchDTO solveProblem(SolveProblemRequest request);

    List<ProblemMatchDTO> getPlayerStats(Long characterId);
}