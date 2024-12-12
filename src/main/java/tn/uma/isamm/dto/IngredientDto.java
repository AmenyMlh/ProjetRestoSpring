package tn.uma.isamm.dto;

import lombok.Data;

@Data
public class IngredientDto {
	private Long id;
    private String name;
    private int quantity;
    private int seuil;
}
