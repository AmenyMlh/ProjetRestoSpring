package tn.uma.isamm.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Card card; 
    private Menu menu; 

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId that = (PaymentId) o;
        return card.equals(that.card) && menu.equals(that.menu);
    }

    @Override
    public int hashCode() {
        System.out.println("card: " + card);
        System.out.println("menu: " + menu);
        int result = card != null ? card.hashCode() : 0;
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        return result;
    }

}
