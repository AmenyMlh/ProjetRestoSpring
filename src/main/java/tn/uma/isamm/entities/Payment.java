package tn.uma.isamm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "card_num")
    private Card card; 

    @Id
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Double amount;

    private Boolean validated = false;
}
