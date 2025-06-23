package com.example.demo.http.controllers;

import com.example.demo.dto.PlayerStatsDTO;
import com.example.demo.models.Achievement;
import com.example.demo.services.Achievement.AchievementService;
import com.example.demo.services.problemMatch.ProblemMatchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;
    private final ProblemMatchServiceImpl problemMatchService;

    @GetMapping("/character/{characterId}")
    public ResponseEntity<List<Achievement>> getCharacterAchievements(@PathVariable Long characterId) {
        List<Achievement> achievements = achievementService.getCharacterAchievements(characterId);
        return ResponseEntity.ok(achievements);
    }

    @GetMapping("/stats/{characterId}")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(@PathVariable Long characterId) {
        PlayerStatsDTO stats = problemMatchService.getDetailedPlayerStats(characterId);
        return ResponseEntity.ok(stats);
    }
}