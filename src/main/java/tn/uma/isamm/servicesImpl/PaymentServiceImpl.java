package tn.uma.isamm.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.CardRepository;
import tn.uma.isamm.repositories.PaymentRepository;
import tn.uma.isamm.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	  @Autowired
	    private PaymentRepository paymentRepository;

	    @Autowired
	    private CardRepository cardRepository;

	    @Override
	    public Payment save(Payment payment) {
	        if (payment == null) {
	            throw new IllegalArgumentException("Le paiement ne peut pas être nul.");
	        }
	        
	        Card card = cardRepository.findById(payment.getCard().getNumCarte())
	                .orElseThrow(() -> new EntityNotFoundException("Carte avec le numéro " + payment.getCard().getNumCarte() + " non trouvée."));
	        
	        if (card.getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, le paiement ne peut pas être effectué.");
	        }

	        Meal meal = payment.getMeal();
	        if (meal == null) {
	            throw new EntityNotFoundException("Repas non trouvé.");
	        }

	        return paymentRepository.save(payment);
	    }

	    @Override
	    public Payment findByCardAndMeal(Card card, Meal meal) {
	        if (card == null || meal == null) {
	            throw new IllegalArgumentException("La carte et le repas ne peuvent pas être nuls.");
	        }

	        if (card.getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, le paiement ne peut pas être récupéré.");
	        }

	        Payment payment = paymentRepository.findByCardAndMeal(card, meal);
	        if (payment == null) {
	            throw new EntityNotFoundException("Aucun paiement trouvé pour la carte " 
	                + card.getNumCarte() + " et le repas " + meal.getId() + ".");
	        }
	        return payment;
	    }

	    @Override
	    public void delete(Payment payment) {
	        if (payment == null) {
	            throw new IllegalArgumentException("Le paiement ne peut pas être nul.");
	        }
	        
	        if (payment.getCard().getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, le paiement ne peut pas être supprimé.");
	        }
	        
	        paymentRepository.delete(payment);
	    }

	    @Override
	    public List<Payment> getPaymentsByCard(String cardNumber) {
	        Card card = cardRepository.findById(cardNumber)
	                .orElseThrow(() -> new EntityNotFoundException("Carte avec le numéro " + cardNumber + " non trouvée"));

	        if (card.getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, impossible de récupérer les paiements.");
	        }

	        return paymentRepository.findByCard(card);
	    }

	    @Override
	    public void validatePayment(PaymentId paymentId) {
	        Payment payment = paymentRepository.findById(paymentId)
	                .orElseThrow(() -> new EntityNotFoundException("Paiement avec la clé " + paymentId + " non trouvé"));

	        if (payment.getCard().getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, le paiement ne peut pas être validé.");
	        }

	        if (payment.getValidated()) {
	            throw new IllegalStateException("Ce paiement a déjà été validé.");
	        }

	        payment.setValidated(true);
	        paymentRepository.save(payment);
	    }

	    @Override
	    public String generateReceipt(PaymentId paymentId) {
	        Payment payment = paymentRepository.findById(paymentId)
	                .orElseThrow(() -> new EntityNotFoundException("Paiement avec la clé " + paymentId + " non trouvé"));

	        if (payment.getCard().getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, impossible de générer le reçu.");
	        }

	        String receipt = "Reçu pour le paiement de " + payment.getAmount() + " effectué pour le repas "
	                + payment.getMeal().getName() + " avec la carte " + payment.getCard().getNumCarte();

	        return receipt;
	    }
}
