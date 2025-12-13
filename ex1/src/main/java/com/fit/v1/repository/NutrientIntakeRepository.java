package com.fit.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fit.v1.model.NutrientIntake;

@Repository
public interface NutrientIntakeRepository extends JpaRepository<NutrientIntake,Long>{
    
}
