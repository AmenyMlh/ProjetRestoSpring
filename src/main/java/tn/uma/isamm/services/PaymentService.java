package tn.uma.isamm.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;

public interface PaymentService {
	//Payment save(Payment payment);
    void delete(Payment payment);
    List<Payment> getPaymentsByCard(String cardNumber);
    void validatePayment(PaymentId paymentId);
	String generateReceipt(PaymentId paymentId);
	Payment findByCardAndMenu(Card card, Menu menu);
	void savePayment(Payment payment) throws Exception;
	
}
