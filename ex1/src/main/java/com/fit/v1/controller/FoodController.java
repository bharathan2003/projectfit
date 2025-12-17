package com.fit.v1.controller;

import com.fit.v1.model.Food;
import com.fit.v1.service.FoodService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/foods", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFood(@RequestBody Food food) {
        try {
            food = foodService.calculateNutrients(food);
            Food savedFood = foodService.createFood(food);
            return ResponseEntity.ok(savedFood);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        if (foods.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Food not found")));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFood(@PathVariable Long id, @RequestBody Food food) {
        return foodService.updateFood(id, food)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Food not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        boolean deleted = foodService.deleteFood(id);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Food not found"));
    }
}
