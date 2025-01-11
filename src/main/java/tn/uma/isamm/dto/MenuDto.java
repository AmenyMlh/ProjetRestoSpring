package tn.uma.isamm.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
	 private Long id;            
	    private String type;        
	    private LocalDate date;    
	    private List<MealDto> meals;
}
