package tn.uma.isamm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.services.MealService;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.save(meal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long id) {
        Meal meal = mealService.findById(id);
        return ResponseEntity.ok(meal);
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        return ResponseEntity.ok(mealService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.update(id, meal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/prepareMeal/{mealId}")
    public ResponseEntity<Meal> prepareMeal(@PathVariable Long mealId) {
        try {
            Meal preparedMeal = mealService.prepareMeal(mealId);
            return new ResponseEntity<>(preparedMeal, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/calculateTotalPrice/{mealId}")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long mealId) {
        try {
            Meal meal = mealService.findById(mealId);
            
            double totalPrice = mealService.calculateTotalPrice(meal);
            
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
