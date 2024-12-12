package tn.uma.isamm.dto;

import lombok.Data;

@Data
public class PaymentIdDto {
	private CardDto carte; 
    private MealDto meal; 
}
