package tn.uma.isamm.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.repositories.MenuRepository;
import tn.uma.isamm.services.MealService;
import tn.uma.isamm.services.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService{
	
	private final MenuRepository menuRepository;
	private final MealService mealService;

    public StatisticsServiceImpl(MenuRepository menuRepository,MealService mealService) {
        this.menuRepository = menuRepository;
        this.mealService = mealService;
    }

	    @Override
	    public int getMealsServedByDate(LocalDate date) {
	        List<Menu> menus = menuRepository.findByDate(date);
	        return menus.stream()
	                .mapToInt(menu -> menu.getMeals().size())
	                .sum();
	    }

	    @Override
	    public int getMealsServedByWeek(LocalDate startDate, LocalDate endDate) {
	        List<Menu> menus = menuRepository.findByDateBetween(startDate, endDate);
	        return menus.stream()
	                .mapToInt(menu -> menu.getMeals().size())
	                .sum();
	    }

	    @Override
	    public int getMealsServedByMonth(int year, int month) {
	        LocalDate startOfMonth = LocalDate.of(year, month, 1);
	        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
	        List<Menu> menus = menuRepository.findByDateBetween(startOfMonth, endOfMonth);
	        return menus.stream()
	                .mapToInt(menu -> menu.getMeals().size())
	                .sum();
	    }

	    @Override
	    public double calculateRestaurantRevenueByDate(LocalDate date) {
	        List<Menu> menus = menuRepository.findByDate(date);
	        return menus.stream()
	                .flatMap(menu -> menu.getMeals().stream()) 
	                .mapToDouble(mealService::calculateTotalPrice) 
	                .sum();
	    }


	    @Override
	    public double calculateRestaurantRevenueByWeek(LocalDate startDate, LocalDate endDate) {
	        List<Menu> menus = menuRepository.findByDateBetween(startDate, endDate);
	        return menus.stream()
	        		.flatMap(menu -> menu.getMeals().stream()) 
	                .mapToDouble(mealService::calculateTotalPrice) 
	                .sum();
	    }

	    @Override
	    public double calculateRestaurantRevenueByMonth(int year, int month) {
	        LocalDate startOfMonth = LocalDate.of(year, month, 1);
	        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
	        List<Menu> menus = menuRepository.findByDateBetween(startOfMonth, endOfMonth);
	        return menus.stream()
	        		.flatMap(menu -> menu.getMeals().stream()) 
	                .mapToDouble(mealService::calculateTotalPrice) 
	                .sum();
	    }

	    @Override
	    public Map<Meal, Long> getMostPopularMeals() {
	        List<Menu> menus = menuRepository.findAll();
	        return menus.stream()
	                .flatMap(menu -> menu.getMeals().stream())
	                .collect(Collectors.groupingBy(meal -> meal, Collectors.counting()))
	                .entrySet().stream()
	                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
	                .limit(5) // 5 plats top
	                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	    }
}
