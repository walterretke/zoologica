package com.example.demo.repositories;

import com.example.demo.models.Character;
import com.example.demo.models.ProblemMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemMatchRepository extends JpaRepository<ProblemMatch, Long> {
    List<ProblemMatch> findByCharacterOrderByDateTimeDesc(Character character);
}