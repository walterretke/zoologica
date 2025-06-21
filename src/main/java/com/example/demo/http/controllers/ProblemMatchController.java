package com.example.demo.http.controllers;

import com.example.demo.dto.ProblemMatchDTO;
import com.example.demo.dto.SolveProblemRequest;
import com.example.demo.services.problemMatch.ProblemMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/problem-match")
public class ProblemMatchController {

    private final ProblemMatchService problemMatchService;

    @PostMapping("/solve")
    public ResponseEntity<ProblemMatchDTO> solveProblem(@RequestBody SolveProblemRequest request) {
        ProblemMatchDTO result = problemMatchService.solveProblem(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats/{characterId}")
    public ResponseEntity<List<ProblemMatchDTO>> getPlayerStats(@PathVariable Long characterId) {
        List<ProblemMatchDTO> stats = problemMatchService.getPlayerStats(characterId);
        return ResponseEntity.ok(stats);
    }
}