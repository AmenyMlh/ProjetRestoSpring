package tn.uma.isamm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.uma.isamm.entities.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {
	 @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.mealIngredients mi LEFT JOIN FETCH mi.ingredient")
	    List<Meal> findAllWithIngredients();
}
