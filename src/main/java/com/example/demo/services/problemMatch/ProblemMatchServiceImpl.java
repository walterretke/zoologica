package com.example.demo.services.problemMatch;

import com.example.demo.dto.PlayerStatsDTO;
import com.example.demo.dto.ProblemMatchDTO;
import com.example.demo.dto.SolveProblemRequest;
import com.example.demo.models.Cage;
import com.example.demo.models.Character;
import com.example.demo.models.MathProblem;
import com.example.demo.models.ProblemMatch;
import com.example.demo.repositories.CharacterRepository;
import com.example.demo.repositories.MathProblemRepository;
import com.example.demo.repositories.ProblemMatchRepository;
import com.example.demo.services.Achievement.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemMatchServiceImpl implements ProblemMatchService {

    private final ProblemMatchRepository problemMatchRepository;
    private final CharacterRepository characterRepository;
    private final MathProblemRepository mathProblemRepository;
    private final AchievementService achievementService;

    @Override
    @Transactional
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

            // Salva o match primeiro para que a contagem esteja atualizada
            ProblemMatch savedMatch = problemMatchRepository.save(match);

            // Verifica e concede achievements após resposta correta
            achievementService.checkAndGrantAchievements(character);

            return convertToDTO(savedMatch);
        } else {
            match.setCoinsEarned(0);
            ProblemMatch savedMatch = problemMatchRepository.save(match);
            return convertToDTO(savedMatch);
        }
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

    public PlayerStatsDTO getDetailedPlayerStats(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        long totalAttempts = problemMatchRepository.countByCharacter(character);
        long correctAnswers = problemMatchRepository.countByCharacterAndCorrect(character, true);
        long incorrectAnswers = totalAttempts - correctAnswers;

        double accuracy = totalAttempts > 0 ? (double) correctAnswers / totalAttempts * 100 : 0;

        return PlayerStatsDTO.builder()
                .characterId(characterId)
                .totalAttempts(totalAttempts)
                .correctAnswers(correctAnswers)
                .incorrectAnswers(incorrectAnswers)
                .accuracy(Math.round(accuracy * 100.0) / 100.0) // Arredonda para 2 casas decimais
                .achievements(achievementService.getCharacterAchievements(characterId))
                .build();
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

        Double animalMultiplier = cage.getAnimalMultiplier();

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