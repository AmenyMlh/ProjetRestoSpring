package tn.uma.isamm.dto;

import lombok.Data;


@Data
public class CardDto {
	    private String numCarte;
	    private StudentDto student;
	    private Double solde;
	    private Boolean isBlocked;
}
