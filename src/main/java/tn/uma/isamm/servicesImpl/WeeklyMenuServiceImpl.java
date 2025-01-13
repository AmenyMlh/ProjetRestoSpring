package tn.uma.isamm.servicesImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
	
    private final MealRepository mealRepository;

    private final MenuRepository menuRepository;
    
    public WeeklyMenuServiceImpl (MealRepository mealRepository, MenuRepository menuRepository ) {
    	this.mealRepository = mealRepository;
    	this.menuRepository = menuRepository;
    }

    public List<Menu> getNextWeekMenus() {
        LocalDate today = LocalDate.now();
        LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate nextFriday = nextMonday.plusDays(4);
        return menuRepository.findByDateBetween(nextMonday, nextFriday);
    }
}
