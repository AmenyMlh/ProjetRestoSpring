package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Meal;

public interface MealService {
	Meal save(Meal meal);
    Meal findById(Long id);
    List<Meal> findAll();
    Meal update(Long id,Meal meal);
    void delete(Long id);
	Meal prepareMeal(Long mealId);
	double calculateTotalPrice(Meal meal);
	double calculateTotalCostForMeals(List<Meal> meals);
}
