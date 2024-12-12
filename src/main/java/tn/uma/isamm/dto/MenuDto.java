package tn.uma.isamm.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MenuDto {
	 private Long id;            
	    private String type;        
	    private LocalDate date;    
	    private List<Long> meals;
}
