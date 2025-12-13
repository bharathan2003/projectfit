package com.fit.v1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.v1.model.NutrientIntake;
import com.fit.v1.service.NutrientIntakeService;
@RestController
@RequestMapping("/nutrients")
public class NutrientIntakeController {
    @Autowired
    private NutrientIntakeService nutrientIntakeService;

    //get all 
    @GetMapping
    public ResponseEntity<List<NutrientIntake>> getAllNutrientIntakes(){
        return ResponseEntity.ok(nutrientIntakeService.getAllNutrientIntakes());
    }
    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<NutrientIntake> getNutrientIntakeById(@PathVariable Long id){
        return ResponseEntity.ok(nutrientIntakeService.getNutrientIntakeById(id).get());
    }
    //post
    @PostMapping
    public ResponseEntity<NutrientIntake> createNutrientIntake(@RequestBody NutrientIntake NutrientIntake){
        NutrientIntake s = nutrientIntakeService.saveNutrientIntake(NutrientIntake);
        return new ResponseEntity<>(s,HttpStatus.CREATED);
    }
    //update by id
    @PutMapping("/{id}")
    public ResponseEntity<NutrientIntake> getByNutrientIntakeId(@PathVariable Long id,@RequestBody NutrientIntake NutrientIntake){
        NutrientIntake s =  nutrientIntakeService.updateNutrientIntake(id, NutrientIntake).get();
        return ResponseEntity.ok(s);

    }
    //Delete 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrientIntakeById(@PathVariable Long id){
        boolean b = nutrientIntakeService.deleteNutrientIntakeById(id);
        if(b) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
