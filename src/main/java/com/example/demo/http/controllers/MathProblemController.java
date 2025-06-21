package com.example.demo.http.controllers;

import com.example.demo.dto.MathProblemDTO;
import com.example.demo.common.enums.ProblemType;
import com.example.demo.services.mathProblem.MathProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/math-problem")
public class MathProblemController {

    private final MathProblemService mathProblemService;

    @PostMapping("/create")
    public ResponseEntity<MathProblemDTO> create(@RequestBody MathProblemDTO mathProblemDTO) {
        MathProblemDTO createdMathProblem = mathProblemService.create(mathProblemDTO);
        return ResponseEntity.ok(createdMathProblem);
    }

    @GetMapping("/cage/{cageId}")
    public ResponseEntity<List<MathProblemDTO>> findByCageId(@PathVariable Long cageId) {
        List<MathProblemDTO> problems = mathProblemService.findByCageId(cageId);
        return ResponseEntity.ok(problems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MathProblemDTO> findById(@PathVariable Long id) {
        MathProblemDTO problem = mathProblemService.findById(id);
        return ResponseEntity.ok(problem);
    }

    @PostMapping("/generate")
    public ResponseEntity<MathProblemDTO> generateRandomProblem(
            @RequestParam ProblemType type,
            @RequestParam Integer difficulty,
            @RequestParam Long cageId) {
        MathProblemDTO problem = mathProblemService.generateRandomProblem(type, difficulty, cageId);
        return ResponseEntity.ok(problem);
    }
}