package tn.uma.isamm.dto;

import lombok.Data;


@Data
public class CardDto {
	    private String numCarte;
	    private UserDto user;
	    private Double solde;
	    private Boolean active;
}
