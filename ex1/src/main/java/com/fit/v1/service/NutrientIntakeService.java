package com.fit.v1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fit.v1.model.NutrientIntake;
import com.fit.v1.repository.NutrientIntakeRepository;

@Service
public class NutrientIntakeService {
    
    @Autowired
    private NutrientIntakeRepository nutrientIntakeRepository;

    //save nutrient
    public NutrientIntake saveNutrientIntake(NutrientIntake nutrientIntake){
        return nutrientIntakeRepository.save(nutrientIntake);
    }
    //get all nutrient
    public List<NutrientIntake> getAllNutrientIntakes(){
        return nutrientIntakeRepository.findAll();
    }
    //get nutrient in take by id
    public Optional<NutrientIntake> getNutrientIntakeById(Long id){
        return nutrientIntakeRepository.findById(id);
    }
    //update nutrient by id
    public Optional<NutrientIntake> updateNutrientIntake(Long id, NutrientIntake nutrientIntake){
        return nutrientIntakeRepository.findById(id)
                                       .map(up -> {
                                        up.setMealId(nutrientIntake.getMealId());
                                        up.setCaloriesConsumed(nutrientIntake.getCaloriesConsumed());
                                        up.setProtein(nutrientIntake.getProtein());
                                        up.setCarbs(nutrientIntake.getCarbs());
                                        up.setFat(nutrientIntake.getFat());
                                        up.setWaterIntake(nutrientIntake.getWaterIntake());

                                        return nutrientIntakeRepository.save(up);
                                       });
    }
    //delete by nutrient id
    public boolean deleteNutrientIntakeById(Long id){
        if(nutrientIntakeRepository.existsById(id)){
            nutrientIntakeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
