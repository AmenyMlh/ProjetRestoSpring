package tn.uma.isamm.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    private String numCarte;

    @OneToOne
    private User user;

    private Double solde;

    private Boolean active = true;
}
