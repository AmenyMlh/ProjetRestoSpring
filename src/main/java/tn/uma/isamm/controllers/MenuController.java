package tn.uma.isamm.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.services.MenuService;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
    private MenuService menuService;

	//@PreAuthorize("hasRole('ADMIN')")
	 @PostMapping
	    public ResponseEntity<Menu> addMenu(@RequestBody Map<String, Object> data) {
	        MealType mealType = MealType.valueOf((String) data.get("type"));
	        LocalDate date = LocalDate.parse((String) data.get("date"));
	        List<Long> mealIds = (List<Long>) data.get("mealIds");

	        Menu menu = new Menu();
	        menu.setType(mealType);
	        menu.setDate(date);

	        Menu savedMenu = menuService.save(menu, mealIds);

	        return ResponseEntity.ok(savedMenu);
	    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        try {
            Menu menu = menuService.findById(id);
            return ResponseEntity.ok(menu);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.findAll();
        return ResponseEntity.ok(menus);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.update(id, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        try {
            menuService.delete(id);
            return ResponseEntity.ok("Menu supprimé avec succès.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byDate/{date}")
    public ResponseEntity<List<Menu>> getMenusByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Menu> menus = menuService.getMenusByDate(localDate);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/byMealType/{type}")
    public ResponseEntity<List<Menu>> getMenusByMealType(@PathVariable String type) {
        MealType mealType = MealType.valueOf(type.toUpperCase());
        List<Menu> menus = menuService.getMenusByMealType(mealType);
        return ResponseEntity.ok(menus);
    }

   
    @GetMapping("/totalPrice/{id}")
    public ResponseEntity<Double> getTotalPriceForMenu(@PathVariable Long id) {
        try {
            Menu menu = menuService.findById(id);
            double totalPrice = menuService.calculateTotalPriceForMenu(menu);
            return ResponseEntity.ok(totalPrice);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
