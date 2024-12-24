package tn.uma.isamm.servicesImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.repositories.MenuRepository;

@Service
public class WeeklyMenuServiceImpl {
	@Autowired
    private MealRepository mealRepository;

    @Autowired
    private MenuRepository menuRepository;

    public Map<String, Menu> generateWeeklyMenu() {
        Map<String, Menu> weeklyMenu = new HashMap<>();

        weeklyMenu.put("Lundi", createMenuForDay("Lundi"));
        weeklyMenu.put("Mardi", createMenuForDay("Mardi"));
        weeklyMenu.put("Mercredi", createMenuForDay("Mercredi"));
        weeklyMenu.put("Jeudi", createMenuForDay("Jeudi"));
        weeklyMenu.put("Vendredi", createMenuForDay("Vendredi"));
        weeklyMenu.put("Samedi", createMenuForDay("Samedi"));

        weeklyMenu.values().forEach(menuRepository::save);

        return weeklyMenu;
    }

    private Menu createMenuForDay(String day) {
        List<Meal> meals = mealRepository.findMealsForDay(day);
        
        if (meals == null || meals.isEmpty()) {
            throw new EntityNotFoundException("Aucun repas trouvé pour le jour " + day);
        }

        Menu menu = new Menu();
        menu.setDate(LocalDate.now());
        menu.setMeals(meals);
        menu.setType(MealType.LUNCH); 

        return menu;
    }
}
