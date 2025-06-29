package com.example.demo.repositories;

import com.example.demo.models.AnimalTemplate;
import com.example.demo.models.CageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalTemplateRepository extends JpaRepository<AnimalTemplate, Long> {
    List<AnimalTemplate> findByCageType(CageType cageType);
}