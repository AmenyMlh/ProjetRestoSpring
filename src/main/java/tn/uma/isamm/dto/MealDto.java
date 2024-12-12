package tn.uma.isamm.dto;

import lombok.Data;

@Data
public class MealDto {
	private Long id;
    private String name;
    private String description;
    private Double price;
}
