package com.example.demo.services.mathProblem;

import com.example.demo.dto.MathProblemDTO;
import com.example.demo.common.enums.ProblemType;
import com.example.demo.models.Cage;
import com.example.demo.models.MathProblem;
import com.example.demo.repositories.CageRepository;
import com.example.demo.repositories.MathProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MathProblemServiceImpl implements MathProblemService {

    private final CageRepository cageRepository;
    private final MathProblemRepository mathProblemRepository;

    @Override
    public MathProblemDTO create(MathProblemDTO dto) {
        Cage cage = cageRepository.findById(dto.getCageId())
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + dto.getCageId()));

        MathProblem newMathProblem = new MathProblem();
        newMathProblem.setCage(cage);
        newMathProblem.setType(dto.getType());
        newMathProblem.setQuestion(dto.getQuestion());
        newMathProblem.setCorrectAnswer(dto.getCorrectAnswer());
        newMathProblem.setProblemMatches(new ArrayList<>());

        MathProblem savedProblem = mathProblemRepository.save(newMathProblem);

        return convertToDTO(savedProblem);
    }

    @Override
    public List<MathProblemDTO> findByCageId(Long cageId) {
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        List<MathProblem> problems = mathProblemRepository.findByCage(cage);

        return problems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MathProblemDTO findById(Long id) {
        MathProblem problem = mathProblemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Math problem not found with id: " + id));

        return convertToDTO(problem);
    }

    @Override
    public MathProblemDTO generateRandomProblem(ProblemType type, Integer difficulty, Long cageId) {
        Cage cage = cageRepository.findById(cageId)
                .orElseThrow(() -> new RuntimeException("Cage not found with id: " + cageId));

        Random random = new Random();
        String question;
        Double answer;

        int maxNumber = switch (difficulty) {
            case 1 -> 10;
            case 2 -> 50;
            case 3 -> 100;
            default -> 20;
        };

        int num1 = random.nextInt(maxNumber) + 1;
        int num2 = random.nextInt(maxNumber) + 1;

        switch (type) {
            case ADDITION:
                question = String.format("Quanto é %d + %d?", num1, num2);
                answer = (double) (num1 + num2);
                break;
            case SUBTRACTION:
                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }
                question = String.format("Quanto é %d - %d?", num1, num2);
                answer = (double) (num1 - num2);
                break;
            case MULTIPLICATION:
                num1 = random.nextInt(Math.min(maxNumber / 2, 12)) + 1;
                num2 = random.nextInt(Math.min(maxNumber / 2, 12)) + 1;
                question = String.format("Quanto é %d × %d?", num1, num2);
                answer = (double) (num1 * num2);
                break;
            case DIVISION:
                num2 = random.nextInt(Math.min(maxNumber / 2, 10)) + 1;
                answer = (double) (random.nextInt(Math.min(maxNumber / 2, 10)) + 1);
                num1 = (int) (num2 * answer);
                question = String.format("Quanto é %d ÷ %d?", num1, num2);
                break;
            default:
                question = String.format("Quanto é %d + %d?", num1, num2);
                answer = (double) (num1 + num2);
        }

        MathProblem newProblem = new MathProblem();
        newProblem.setCage(cage);
        newProblem.setType(type);
        newProblem.setQuestion(question);
        newProblem.setCorrectAnswer(answer);
        newProblem.setProblemMatches(new ArrayList<>());

        MathProblem savedProblem = mathProblemRepository.save(newProblem);

        return convertToDTO(savedProblem);
    }

    private MathProblemDTO convertToDTO(MathProblem mathProblem) {
        MathProblemDTO dto = new MathProblemDTO();
        dto.setId(mathProblem.getId());
        dto.setType(mathProblem.getType());
        dto.setQuestion(mathProblem.getQuestion());
        dto.setCorrectAnswer(mathProblem.getCorrectAnswer());

        if (mathProblem.getCage() != null) {
            dto.setCageId(mathProblem.getCage().getId());
        }

        return dto;
    }
}