package com.example.demo.services.problemMatch;

import com.example.demo.dto.ProblemMatchDTO;
import com.example.demo.dto.SolveProblemRequest;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.models.MathProblem;
import com.example.demo.models.ProblemMatch;
import com.example.demo.repositories.CharacterRepository;
import com.example.demo.repositories.MathProblemRepository;
import com.example.demo.repositories.ProblemMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemMatchServiceImpl implements ProblemMatchService {

    private final ProblemMatchRepository problemMatchRepository;
    private final CharacterRepository characterRepository;
    private final MathProblemRepository mathProblemRepository;

    @Override
    public ProblemMatchDTO solveProblem(SolveProblemRequest request) {
        Character character = characterRepository.findById(request.getCharacterId())
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + request.getCharacterId()));

        MathProblem problem = mathProblemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Math problem not found with id: " + request.getProblemId()));

        boolean isCorrect = request.getGivenAnswer().equals(problem.getCorrectAnswer());

        ProblemMatch match = new ProblemMatch();
        match.setCharacter(character);
        match.setMathProblem(problem);
        match.setGivenAnswer(request.getGivenAnswer());
        match.setCorrect(isCorrect);
        match.setSolutionTime(request.getSolutionTime());
        match.setDateTime(LocalDateTime.now());

        if (isCorrect) {
            int coinsEarned = calculateCoins(request.getSolutionTime(), problem.getCage());
            match.setCoinsEarned(coinsEarned);

            character.setTotalCoins(character.getTotalCoins() + coinsEarned);
            characterRepository.save(character);
        } else {
            match.setCoinsEarned(0);
        }

        ProblemMatch savedMatch = problemMatchRepository.save(match);

        return convertToDTO(savedMatch);
    }

    @Override
    public List<ProblemMatchDTO> getPlayerStats(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        List<ProblemMatch> matches = problemMatchRepository.findByCharacterOrderByDateTimeDesc(character);

        return matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private int calculateCoins(Long solutionTime, Cage cage) {
        int baseCoins = cage.getCageType().getDifficultyLevel() * 15;

        int speedBonus = 0;
        if (solutionTime <= 5000) {
            speedBonus = 20;
        } else if (solutionTime <= 10000) {
            speedBonus = 15;
        } else if (solutionTime <= 20000) {
            speedBonus = 10;
        }

        // Multiplicador por número de animais na jaula
        Double animalMultiplier = cage.getAnimalMultiplier();

        // Cálculo final: (base + bonus) * multiplicador de animais
        return (int) ((baseCoins + speedBonus) * animalMultiplier);
    }

    private ProblemMatchDTO convertToDTO(ProblemMatch match) {
        ProblemMatchDTO dto = new ProblemMatchDTO();
        dto.setId(match.getId());
        dto.setCharacterId(match.getCharacter().getId());
        dto.setProblemId(match.getMathProblem().getId());
        dto.setGivenAnswer(match.getGivenAnswer());
        dto.setCorrect(match.getCorrect());
        dto.setCoinsEarned(match.getCoinsEarned());
        dto.setSolutionTime(match.getSolutionTime());
        dto.setDateTime(match.getDateTime());

        if (match.getCorrect()) {
            Cage cage = match.getMathProblem().getCage();
            String animalInfo = cage.getAnimals().size() > 1 ?
                    " (Multiplicador de " + cage.getAnimalMultiplier() + "x por ter " + cage.getAnimals().size() + " animais!)" : "";

            dto.setMessage("🎉 Parabéns! Resposta correta! Você ganhou " + match.getCoinsEarned() +
                    " moedas!" + animalInfo);
        } else {
            dto.setMessage("❌ Resposta incorreta! A resposta correta era: " +
                    match.getMathProblem().getCorrectAnswer() +
                    ". Você respondeu: " + match.getGivenAnswer());
        }

        return dto;
    }
}