package tn.uma.isamm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PaymentId.class)
public class Payment {
	
	@Id
	@ManyToOne
    private Card card;

    @Id
    @ManyToOne
    private Meal meal;

    private Double amount;

}
