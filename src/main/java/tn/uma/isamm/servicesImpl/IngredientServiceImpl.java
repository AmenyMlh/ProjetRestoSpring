package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.repositories.IngredientRepository;
import tn.uma.isamm.services.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {
	    @Autowired
	    private IngredientRepository ingredientRepository;

	    @Override
	    public Ingredient saveIngredient(Ingredient ingredient) {
	        return ingredientRepository.save(ingredient);
	    }

	    @Override
	    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
	        Optional<Ingredient> existingIngredient = ingredientRepository.findById(id);
	        if (existingIngredient.isPresent()) {
	            Ingredient updatedIngredient = existingIngredient.get();
	            updatedIngredient.setName(ingredient.getName());
	            updatedIngredient.setQuantity(ingredient.getQuantity());
	            updatedIngredient.setSeuil(ingredient.getSeuil());
	            return ingredientRepository.save(updatedIngredient);
	        } else {
	            throw new RuntimeException("Ingredient not found with id " + id);
	        }
	    }

	    @Override
	    public void deleteIngredient(Long id) {
	        ingredientRepository.deleteById(id);
	    }

	    @Override
	    public Ingredient getIngredientById(Long id) {
	        return ingredientRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Ingredient not found with id " + id));
	    }

	    @Override
	    public List<Ingredient> getAllIngredients() {
	        return ingredientRepository.findAll();
	    }

	    @Override
	    public List<Ingredient> getLowStockIngredients(int seuil) {
	        return ingredientRepository.findByQuantityLessThan(seuil);
	    }

}
