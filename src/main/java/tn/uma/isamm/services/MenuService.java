package tn.uma.isamm.services;

import java.time.LocalDate;
import java.util.List;

import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;

public interface MenuService {
	Menu save(Menu menu);
    Menu findById(Long id);
    List<Menu> findAll();
    Menu update(Long id,Menu menu);
    void delete(Long id);
    List<Menu> getMenusByDate(LocalDate date);
	double calculateTotalPriceForMenu(Menu menu);
	List<Menu> getMenusByMealType(MealType type);
}
