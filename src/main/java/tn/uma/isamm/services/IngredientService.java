package tn.uma.isamm.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import tn.uma.isamm.dto.IngredientDto;
import tn.uma.isamm.entities.Ingredient;

public interface IngredientService {
	Ingredient saveIngredient(Ingredient ingredient);

    Ingredient updateIngredient(Long id, Ingredient ingredient);

    void deleteIngredient(Long id);

    Ingredient getIngredientById(Long id);

    List<IngredientDto> getAllIngredients();

	boolean checkStockAvailability(List<Ingredient> ingredients);

	List<Ingredient> getLowStockIngredients();

	List<Ingredient> updateStock(List<Ingredient> ingredients);
	

}
