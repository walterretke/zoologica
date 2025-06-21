package com.example.demo.repositories;

import com.example.demo.models.Cage;
import com.example.demo.models.MathProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathProblemRepository extends JpaRepository<MathProblem, Long> {
    List<MathProblem> findByCage(Cage cage);
}