package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import tn.uma.isamm.exceptions.InsufficientFundsException;
import tn.uma.isamm.dto.MealDto;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.MealIngredient;
import tn.uma.isamm.exceptions.InsufficientStockException;
import tn.uma.isamm.mapper.MealMapper;
import tn.uma.isamm.repositories.IngredientRepository;
import tn.uma.isamm.repositories.MealIngredientRepository;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.services.CardService;
import tn.uma.isamm.services.IngredientService;
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
    private CardService cardService;

    @Autowired
    private MealIngredientRepository mealIngredientRepository;

    @Override
    public Meal save(Meal meal) {
        if (meal.getMealIngredients() == null || meal.getMealIngredients().isEmpty()) {
            throw new IllegalArgumentException("Le plat doit contenir des ingrédients.");
        }

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(mealIngredient.getIngredient().getId())
                    .orElseThrow(() -> new IllegalArgumentException("L'ingrédient avec l'ID " + mealIngredient.getIngredient().getId() + " n'existe pas."));

            if (ingredient.getQuantity() < mealIngredient.getQuantity()) {
                throw new IllegalArgumentException("Quantité insuffisante pour l'ingrédient " + ingredient.getName());
            }

            ingredient.setQuantity(ingredient.getQuantity() - mealIngredient.getQuantity());
            ingredientRepository.save(ingredient);  

            mealIngredient.setMeal(meal);  
        }

        return mealRepository.save(meal);
    }



    @Override
    public List<MealDto> findAll() {
        return mealRepository.findAll()
                .stream()
                .map(MealMapper.INSTANCE::toDto)
                .toList();
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

    public Meal prepareMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal avec l'ID: " + mealId + " n'existe pas"));

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            Ingredient ingredient = mealIngredient.getIngredient();
            int usedQuantity = mealIngredient.getQuantity(); 

            if (ingredient.getQuantity() < usedQuantity) {
                throw new InsufficientStockException("Stock insuffisant pour l'ingrédient : " + ingredient.getName());
            }
            ingredient.setQuantity(ingredient.getQuantity() - usedQuantity);
            ingredientRepository.save(ingredient); 
        }
        double totalPrice = calculateTotalPrice(meal);

        return meal;
    }

    @Override
    public double calculateTotalPrice(Meal meal) {
        double totalPrice = 0;

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            Ingredient ingredient = mealIngredient.getIngredient();
            int quantity = mealIngredient.getQuantity(); 
            double ingredientPrice = ingredient.getPrice(); 
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
    
    public Meal purchaseMeal(Long mealId, String numCarte) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Repas avec l'ID: " + mealId + " n'existe pas"));

        double totalPrice = calculateTotalPrice(meal);

        Double cardBalance = cardService.getSolde(numCarte);
        if (cardBalance < totalPrice) {
            throw new InsufficientFundsException("Solde insuffisant sur la carte.");
        }

        cardService.deductFromCard(numCarte, totalPrice);

        for (MealIngredient mealIngredient : meal.getMealIngredients()) {
            Ingredient ingredient = mealIngredient.getIngredient(); 
            int usedQuantity = mealIngredient.getQuantity(); 

            if (ingredient.getQuantity() < usedQuantity) {
                throw new InsufficientStockException("Stock insuffisant pour l'ingrédient : " + ingredient.getName());
            }
            ingredient.setQuantity(ingredient.getQuantity() - usedQuantity);
            ingredientService.saveIngredient(ingredient); 
        }

        return meal;
    }


}
