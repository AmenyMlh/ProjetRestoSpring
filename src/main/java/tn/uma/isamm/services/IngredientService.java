package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Ingredient;

public interface IngredientService {
	Ingredient saveIngredient(Ingredient ingredient);

    Ingredient updateIngredient(Long id, Ingredient ingredient);

    void deleteIngredient(Long id);

    Ingredient getIngredientById(Long id);

    List<Ingredient> getAllIngredients();

    List<Ingredient> getLowStockIngredients(int seuil);
}
