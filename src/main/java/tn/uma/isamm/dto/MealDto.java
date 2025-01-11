package tn.uma.isamm.dto;

import java.util.List;

import lombok.Data;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.enums.MealType;

@Data
public class MealDto {
	private Long id;
    private String name;
    private Double price;
    private MealType mealType;
    private String description;
    private List<Ingredient> ingredients;
}
