package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;

public interface PaymentService {
	Payment save(Payment payment);
    Payment findByCardAndMeal(Card card, Meal meal);
    void delete(Payment payment);
    List<Payment> getPaymentsByCard(String cardNumber);
    void validatePayment(PaymentId paymentId);
	String generateReceipt(PaymentId paymentId);
}
