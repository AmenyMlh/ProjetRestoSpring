package tn.uma.isamm.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tn.uma.isamm.dto.MenuDto;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;
import tn.uma.isamm.mapper.MenuMapper;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.repositories.MenuRepository;
import tn.uma.isamm.services.MealService;
import tn.uma.isamm.services.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
    private MenuRepository menuRepository;
	
	@Autowired
	private MealRepository mealRepository;

    @Autowired
    private MealService mealService;

   @Override
   public Menu save(Menu menu, List<Long> mealIds) {
       if (mealIds == null || mealIds.isEmpty()) {
           throw new IllegalArgumentException("Le menu doit contenir des plats.");
       }

       List<Meal> meals = mealRepository.findAllById(mealIds);
       if (meals.size() != mealIds.size()) {
           throw new IllegalArgumentException("Certains plats spécifiés n'existent pas.");
       }
       menu.setMeals(meals);
       return menuRepository.save(menu);
   }



    @Override
    public Menu findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du menu ne peut pas être null");
        }

        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu avec l'ID " + id + " non trouvé"));
    }

    @Override
    public List<MenuDto> findAll() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                    .map(MenuMapper.INSTANCE::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Menu update(Long id, Menu menu) {
        if (id == null || menu == null) {
            throw new IllegalArgumentException("L'ID ou le menu ne peut pas être null");
        }

        if (!menuRepository.existsById(id)) {
            throw new EntityNotFoundException("Menu avec l'ID " + id + " non trouvé");
        }

        menu.setId(id);
        return menuRepository.save(menu);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du menu ne peut pas être null");
        }

        if (!menuRepository.existsById(id)) {
            throw new EntityNotFoundException("Menu avec l'ID " + id + " non trouvé");
        }

        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> getMenusByDate(LocalDate date) {
        List<Menu> menus = menuRepository.findByDate(date);

        if (menus.isEmpty()) {
            throw new EntityNotFoundException("Aucun menu trouvé pour la date : " + date);
        }

        return menus;
    }

    @Override
    public List<Menu> getMenusByMealType(MealType type) {
        List<Menu> menus = menuRepository.findByType(type);

        if (menus.isEmpty()) {
            throw new EntityNotFoundException("Aucun menu trouvé pour le type : " + type);
        }

        return menus;
    }

    @Override
    public double calculateTotalPriceForMenu(Menu menu) {
        return menu.getMeals().stream()
                   .mapToDouble(Meal::getPrice) 
                   .sum();  
    }

}
