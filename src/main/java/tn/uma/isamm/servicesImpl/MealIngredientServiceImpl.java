package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.MealIngredient;
import tn.uma.isamm.repositories.MealIngredientRepository;
import tn.uma.isamm.services.MealIngredientService;

@Service
public class MealIngredientServiceImpl implements MealIngredientService {
	 @Autowired
	    private MealIngredientRepository mealIngredientRepository;

	 @Override
	    public MealIngredient addMealIngredient(MealIngredient mealIngredient) {
	        return mealIngredientRepository.save(mealIngredient);
	    }
	    
}