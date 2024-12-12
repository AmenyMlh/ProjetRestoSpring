package tn.uma.isamm.services;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;

public interface PaymentService {
	Payment save(Payment payment);
    Payment findByCardAndMeal(Card card, Meal meal);
    void delete(Payment payment);
}
