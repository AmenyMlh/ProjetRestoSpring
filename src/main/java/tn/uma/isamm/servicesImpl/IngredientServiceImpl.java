package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.dto.IngredientDto;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.mapper.IngredientMapper;
import tn.uma.isamm.repositories.IngredientRepository;
import tn.uma.isamm.services.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw new EntityConflictException("L'ingrédient existe déjà avec le nom: " + ingredient.getName());
        }
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityConflictException("L'ingrédient avec l'ID " + id + " n'existe pas"));

        Optional<Ingredient> conflictingIngredient = ingredientRepository.findByName(ingredient.getName());
        if (conflictingIngredient.isPresent() && !conflictingIngredient.get().getId().equals(id)) {
            throw new EntityConflictException("Un autre ingrédient existe déjà avec le nom: " + ingredient.getName());
        }

        existingIngredient.setName(ingredient.getName());
        existingIngredient.setQuantity(ingredient.getQuantity());
        existingIngredient.setSeuil(ingredient.getSeuil());
        
        return ingredientRepository.save(existingIngredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new EntityConflictException("L'ingrédient avec l'ID " + id + " n'existe pas");
        }
        
        if (ingredientRepository.isIngredientUsed(id)) {
            throw new EntityConflictException("L'ingrédient avec l'ID " + id + " est utilisé dans un ou plusieurs repas et ne peut pas être supprimé");
        }

        ingredientRepository.deleteById(id);
    }


    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityConflictException("Ingrédient non trouvé avec l'ID " + id));
    }

    @Override
    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll()
                                   .stream()
                                   .map(IngredientMapper.INSTANCE::toDto)
                                   .collect(Collectors.toList());
    }

    @Override
    public List<Ingredient> getLowStockIngredients() {
        return ingredientRepository.findAll().stream()
                .filter(ingredient -> ingredient.getQuantity() < ingredient.getSeuil())
                .collect(Collectors.toList());
    }


    @Override
    public boolean checkStockAvailability(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getQuantity() < ingredient.getSeuil()) {
                return false;  
            }
        }
        return true;  
    }
    @Override
    public List<Ingredient> updateStock(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            Ingredient existingIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new EntityConflictException("L'ingrédient avec l'ID " + ingredient.getId() + " n'existe pas"));
            
            existingIngredient.setQuantity(existingIngredient.getQuantity() + ingredient.getQuantity());
            ingredientRepository.save(existingIngredient);
        }
        return ingredients;
    }

}

