package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.services.MealService;

@Service
public class MealServiceImpl implements MealService {
	
	@Autowired
    private MealRepository mealRepository;

    @Override
    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public Meal findById(Long id) {
        Optional<Meal> meal = mealRepository.findById(id);
        return meal.orElse(null);
    }

    @Override
    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    @Override
    public Meal update(Long id, Meal meal) {
        if (meal == null || meal.getId() == null) {
            throw new IllegalArgumentException("Meal and its ID must not be null");
        }

        if (!mealRepository.existsById(id)) {
            throw new RuntimeException("Meal with ID " + id + " does not exist");
        }

        meal.setId(id);  

        return mealRepository.save(meal);
    }



    @Override
    public void delete(Long id) {
        mealRepository.deleteById(id);
    }

}
