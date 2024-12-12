package tn.uma.isamm.dto;

import lombok.Data;

@Data
public class PaymentDto {
	private CardDto card;
    private MealDto meal;
    private Double amount;
}
