package com.fit.v1.repository;

import com.fit.v1.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByFoodNameIn(List<String> foodNames);
}
