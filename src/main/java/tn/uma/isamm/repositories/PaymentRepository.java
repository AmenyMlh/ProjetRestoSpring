package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    Payment findByCardAndMenu(Card card, Menu menu);
    List<Payment> findByCard(Card card);

}
