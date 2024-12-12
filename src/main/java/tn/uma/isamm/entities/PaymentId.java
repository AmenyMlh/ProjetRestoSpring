package tn.uma.isamm.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaymentId implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Card card;
	    private Meal meal;

}
