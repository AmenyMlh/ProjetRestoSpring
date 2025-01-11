package tn.uma.isamm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.exceptions.InsufficientFundsException;
import tn.uma.isamm.exceptions.InsufficientStockException;
import tn.uma.isamm.services.CardService;
import tn.uma.isamm.services.IngredientService;
import tn.uma.isamm.services.MealService;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        Meal savedMeal = mealService.save(meal);
        return ResponseEntity.ok(savedMeal);
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

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long id, @RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.update(id, meal));
    }

    //@PreAuthorize("hasRole('ADMIN')")
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

    //@PreAuthorize("hasRole('ADMIN')")
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
    
    @PostMapping("/purchase/{mealId}/{numCarte}")
    public ResponseEntity<String> purchaseMeal(@PathVariable Long mealId, @PathVariable String numCarte) {
        try {
            Meal meal = mealService.purchaseMeal(mealId, numCarte);

            double totalPrice = mealService.calculateTotalPrice(meal);
            return ResponseEntity.ok("Repas acheté avec succès. Prix total : " + totalPrice);
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'achat du repas: " + e.getMessage());
        } catch (InsufficientStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'achat du repas: Stock insuffisant pour l'ingrédient " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'achat du repas: " + e.getMessage());
        }
    }
    
   
}
