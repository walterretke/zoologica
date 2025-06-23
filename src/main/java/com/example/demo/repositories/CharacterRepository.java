package com.example.demo.repositories;

import com.example.demo.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query("SELECT c FROM Character c LEFT JOIN FETCH c.achievements WHERE c.id = :id")
    Optional<Character> findByIdWithAchievements(@Param("id") Long id);

    @Query(value = "SELECT achievement_id FROM character_achievements WHERE character_id = :characterId", nativeQuery = true)
    List<Long> findAchievementIdsByCharacterId(@Param("characterId") Long characterId);


}