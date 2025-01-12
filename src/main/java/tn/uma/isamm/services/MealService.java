package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.dto.MealDto;
import tn.uma.isamm.entities.Meal;

public interface MealService {
    Meal findById(Long id);
    List<MealDto> findAll();
    Meal update(Long id,Meal meal);
    void delete(Long id);
	Meal prepareMeal(Long mealId);
	double calculateTotalPrice(Meal meal);
	double calculateTotalCostForMeals(List<Meal> meals);
	Meal purchaseMeal(Long mealId, String numCarte);
	List<MealDto> getAllMealsWithIngredients();
	Meal save(Meal meal);
}
