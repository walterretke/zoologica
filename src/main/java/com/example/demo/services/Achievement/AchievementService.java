package com.example.demo.services.Achievement;

import com.example.demo.models.Achievement;
import com.example.demo.models.Character;
import com.example.demo.repositories.AchievementRepository;
import com.example.demo.repositories.CharacterRepository;
import com.example.demo.repositories.ProblemMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final CharacterRepository characterRepository;
    private final ProblemMatchRepository problemMatchRepository;

    @Transactional
    public void checkAndGrantAchievements(Character character) {
        long correctAnswersCount = problemMatchRepository.countByCharacterAndCorrect(character, true);

        checkAndGrantAchievement(character, "Primeira conta", correctAnswersCount, 1);
        checkAndGrantAchievement(character, "Pegando o jeito", correctAnswersCount, 10);
        checkAndGrantAchievement(character, "Ficando craque", correctAnswersCount, 50);
    }

    private void checkAndGrantAchievement(Character character, String achievementName, long correctAnswersCount, int requiredCount) {
        if (correctAnswersCount >= requiredCount) {
            Achievement achievement = achievementRepository.findByName(achievementName)
                    .orElse(null);

            if (achievement != null) {
                boolean hasAchievement = character.getAchievements() != null &&
                        character.getAchievements().contains(achievement);

                if (!hasAchievement) {
                    if (character.getAchievements() == null) {
                        character.setAchievements(new java.util.HashSet<>());
                    }
                    character.getAchievements().add(achievement);

                    if (achievement.getRewardCoins() != null) {
                        character.setTotalCoins(character.getTotalCoins() + achievement.getRewardCoins());
                    }

                    characterRepository.save(character);

                    System.out.println("🏆 Achievement concedido: " + achievementName +
                            " para " + character.getName() +
                            " (+" + achievement.getRewardCoins() + " moedas)");
                }
            }
        }
    }

    public List<Achievement> getCharacterAchievements(Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + characterId));

        return character.getAchievements() != null ?
                character.getAchievements().stream().toList() :
                List.of();
    }
}
