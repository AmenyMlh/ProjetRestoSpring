package tn.uma.isamm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDto {
	private Long id;
    private String name;
    private double price;
    private double quantity;
    private int seuil;
    private String unit;
}
