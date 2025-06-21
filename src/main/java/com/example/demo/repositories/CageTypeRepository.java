package com.example.demo.repositories;

import com.example.demo.models.CageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CageTypeRepository extends JpaRepository<CageType, Long> {
    Optional<CageType> findByName(String name);
}