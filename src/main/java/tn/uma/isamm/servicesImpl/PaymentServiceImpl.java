package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.repositories.PaymentRepository;
import tn.uma.isamm.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	    @Autowired
	    private PaymentRepository paymentRepository;

	    @Override
	    public Payment save(Payment payment) {
	        return paymentRepository.save(payment);
	    }

	    @Override
	    public Payment findByCardAndMeal(Card card, Meal meal) {
	        return paymentRepository.findByCardAndMeal(card, meal);
	    }

	    @Override
	    public void delete(Payment payment) {
	        paymentRepository.delete(payment);
	    }
}
