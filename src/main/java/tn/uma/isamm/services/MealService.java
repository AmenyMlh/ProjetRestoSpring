package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Meal;

public interface MealService {
	Meal save(Meal meal);
    Meal findById(Long id);
    List<Meal> findAll();
    Meal update(Long id,Meal meal);
    void delete(Long id);
}
