package tn.uma.isamm.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.CardRepository;
import tn.uma.isamm.repositories.MenuRepository;
import tn.uma.isamm.repositories.PaymentRepository;
import tn.uma.isamm.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	 private final PaymentRepository paymentRepository;
	    private final CardRepository cardRepository;
	    private final MenuRepository menuRepository;

	    @Autowired
	    public PaymentServiceImpl(PaymentRepository paymentRepository, 
	                              CardRepository cardRepository, 
	                              MenuRepository menuRepository) {
	        this.paymentRepository = paymentRepository;
	        this.cardRepository = cardRepository;
	        this.menuRepository = menuRepository;
	    }

	    @Override
	    public void savePayment(Payment payment) throws Exception {
	        Card card = cardRepository.findByNumCarte(payment.getCard().getNumCarte());
	        if (card == null) {
	            throw new Exception("Carte non trouvée !");
	        }

	        Menu menu = menuRepository.findById(payment.getMenu().getId()).orElse(null);
	        if (menu == null) {
	            throw new Exception("Menu non trouvé !");
	        }

	        payment.setCard(card);
	        payment.setMenu(menu);
	        paymentRepository.save(payment);
	    }

	    @Override
	    public Payment findByCardAndMenu(Card card, Menu menu) {
	        if (card == null || menu == null) {
	            throw new IllegalArgumentException("La carte et le repas ne peuvent pas être nuls.");
	        }

	        if (card.getIsBlocked()) {
	            throw new IllegalStateException("La carte est bloquée, le paiement ne peut pas être récupéré.");
	        }

	        Payment payment = paymentRepository.findByCardAndMenu(card, menu);
	        if (payment == null) {
	            throw new EntityNotFoundException("Aucun paiement trouvé pour la carte " 
	                + card.getNumCarte() + " et le repas " + menu.getId() + ".");
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

	        String receipt = "Reçu pour le paiement de " + payment.getAmount() + " effectué pour le menu "
	                + payment.getMenu().getType() + " avec la carte " + payment.getCard().getNumCarte();

	        return receipt;
	    }

}
