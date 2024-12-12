package tn.uma.isamm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	 List<Ingredient> findByQuantityLessThan(int seuil);
}
