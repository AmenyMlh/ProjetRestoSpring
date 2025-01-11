package tn.uma.isamm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.uma.isamm.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	 List<Ingredient> findByQuantityLessThan(int seuil);

	boolean existsByName(String name);

	Optional<Ingredient> findByName(String name);
	
	@Query("SELECT COUNT(m) > 0 FROM MealIngredient m WHERE m.ingredient.id = :id")
	boolean isIngredientUsed(Long id);

}
