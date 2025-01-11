package tn.uma.isamm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.MealIngredient;

public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {
    List<MealIngredient> findByMealId(Long mealId);

}