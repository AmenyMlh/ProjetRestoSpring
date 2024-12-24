package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.MealIngredient;
import tn.uma.isamm.exceptions.InsufficientStockException;
import tn.uma.isamm.repositories.IngredientRepository;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.services.IngredientService;
import tn.uma.isamm.services.MealIngredientService;
import tn.uma.isamm.services.MealService;

@Service
public class MealServiceImpl implements MealService {
    
    @Autowired
    private MealRepository mealRepository;
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private MealIngredientService mealIngredientService;

    @Override
    public Meal save(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Le repas ne peut pas être null");
        }

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            mealIngredient.setMeal(meal);  
        }

        double totalPrice = calculateTotalPrice(meal);
        meal.setPrice(totalPrice);

        return mealRepository.save(meal);
    }


    @Override
    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    @Override
    public Meal update(Long id, Meal meal) {
        if (id == null || meal == null || meal.getId() == null) {
            throw new IllegalArgumentException("Le repas et son ID doivent être non null");
        }

        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException("Repas avec l'ID " + id + " non trouvé");
        }

        meal.setId(id);
        return mealRepository.save(meal);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du repas ne peut pas être null");
        }

        if (!mealRepository.existsById(id)) {
            throw new EntityNotFoundException("Repas avec l'ID " + id + " non trouvé");
        }

        mealRepository.deleteById(id);
    }

    @Override
    public Meal prepareMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal avec l'ID: " + mealId + " n'existe pas"));
        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            Ingredient ingredient = mealIngredient.getIngredient();
            int usedQuantity = mealIngredient.getQuantity();

            if (ingredient.getQuantity() < usedQuantity) {
                throw new InsufficientStockException(ingredient.getName());
            }

            ingredient.setQuantity(ingredient.getQuantity() - usedQuantity);
            ingredientService.saveIngredient(ingredient);
        }

        double totalPrice = calculateTotalPrice(meal);
        return meal;
    }

    @Override
    public double calculateTotalPrice(Meal meal) {
        double totalPrice = 0;

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            double ingredientPrice = mealIngredient.getIngredient().getPrice();
            int quantity = mealIngredient.getQuantity();
            totalPrice += ingredientPrice * quantity;
        }

        return totalPrice;
    }


    @Override
    public Meal findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du repas ne peut pas être null");
        }

        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Repas avec l'ID " + id + " non trouvé"));
    }
    
    @Override
    public double calculateTotalCostForMeals(List<Meal> meals) {
        return meals.stream()
            .mapToDouble(this::calculateTotalPrice)
            .sum();
    }
    


}
