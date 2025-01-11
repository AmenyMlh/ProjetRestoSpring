package tn.uma.isamm.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.enums.MealType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
	private Long id;
    private String name;
    private Double price;
    private MealType mealType;
    private String description;
    private List<IngredientDto> ingredients;
}
