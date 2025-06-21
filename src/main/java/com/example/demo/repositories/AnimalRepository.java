package com.example.demo.repositories;

import com.example.demo.models.Animal;
import com.example.demo.models.Cage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByCage(Cage cage);
}