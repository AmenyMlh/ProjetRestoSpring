package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    Payment findByCardAndMeal(Card card, Meal meal);
    List<Payment> findByCard(Card card);

}