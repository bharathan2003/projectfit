package com.fit.v1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fit.v1.model.Food;
import com.fit.v1.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    // USDA FoodData Central API
    private static final String API_URL = "https://api.nal.usda.gov/fdc/v1/foods/search?query=";
    private static final String API_KEY = "te7vu1Rs0ecxgBUi8XZlmv7hHequLdT2chU4sbUd";

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }

    public Optional<Food> updateFood(Long id, Food food) {
        return foodRepository.findById(id).map(existing -> {
            if (food.getFoodName() != null) existing.setFoodName(food.getFoodName());
            if (food.getQuantity() != null) existing.setQuantity(food.getQuantity());
            existing = calculateNutrients(existing);
            return foodRepository.save(existing);
        });
    }

    public boolean deleteFood(Long id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Fetch nutrient values from USDA FoodData Central
    public Food calculateNutrients(Food food) {
        if (food.getFoodName() == null) {
            throw new IllegalArgumentException("foodName is required");
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL + food.getFoodName() + "&api_key=" + API_KEY;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("USDA API Raw Response: " + response.getBody());

        Map<String, Object> bodyMap;
        try {
            bodyMap = mapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse USDA API response: " + response.getBody(), e);
        }

        List<Map<String, Object>> foods = (List<Map<String, Object>>) bodyMap.get("foods");
        if (foods == null || foods.isEmpty()) {
            throw new RuntimeException("No nutrition data found for: " + food.getFoodName());
        }

        Map<String, Object> firstFood = foods.get(0);
        List<Map<String, Object>> nutrients = (List<Map<String, Object>>) firstFood.get("foodNutrients");

        double calories = 0.0;
        double protein = 0.0;

        for (Map<String, Object> nutrient : nutrients) {
            String name = (String) nutrient.get("nutrientName");
            Object value = nutrient.get("value");

            if (name != null && value != null) {
                try {
                    double val = Double.parseDouble(value.toString());
                    if (name.equalsIgnoreCase("Energy")) {
                        calories = val;
                    } else if (name.equalsIgnoreCase("Protein")) {
                        protein = val;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }

        food.setCaloriesConsumed(calories);
        food.setProtein(protein);

        return food;
    }
}
