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
    private Student student;

    private Double solde;

    private Boolean isBlocked = false;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return numCarte != null && numCarte.equals(card.numCarte);
    }

    @Override
    public int hashCode() {
        return numCarte != null ? numCarte.hashCode() : 0;
    }
}
