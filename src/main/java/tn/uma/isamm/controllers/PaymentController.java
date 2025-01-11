package tn.uma.isamm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.entities.PaymentId;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.CardRepository;
import tn.uma.isamm.repositories.MealRepository;
import tn.uma.isamm.services.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	 private final PaymentService paymentService;

	    @Autowired
	    public PaymentController(PaymentService paymentService) {
	        this.paymentService = paymentService;
	    }

	    @PostMapping
	    public ResponseEntity<String> savePayment(@RequestBody Payment payment) {
	        if (payment.getCard() == null || payment.getCard().getNumCarte() == null) {
	            return ResponseEntity.badRequest().body("Erreur : La carte est invalide ou non fournie.");
	        }

	        if (payment.getMenu() == null || payment.getMenu().getId() == null) {
	            return ResponseEntity.badRequest().body("Erreur : Le menu est invalide ou non fourni.");
	        }

	        try {
	            paymentService.savePayment(payment);
	            return ResponseEntity.ok("Paiement enregistré avec succès !");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
	        }
	    }


	    @GetMapping("/card/{cardNumber}/menu/{menuId}")
	    public ResponseEntity<Payment> getPaymentByCardAndMeal(@PathVariable String cardNumber, @PathVariable Long menuId) {
	        try {
	            Card card = new Card();
	            card.setNumCarte(cardNumber);
	            Menu menu = new Menu();
	            menu.setId(menuId);
	            Payment payment = paymentService.findByCardAndMenu(card, menu);
	            return ResponseEntity.ok(payment);
	        } catch (IllegalArgumentException | EntityNotFoundException | EntityConflictException ex) {
	            return ResponseEntity.badRequest().body(null);
	        }
	    }

	    @DeleteMapping("/delete")
	    public ResponseEntity<String> deletePayment(@RequestBody Payment payment) {
	        try {
	            paymentService.delete(payment);
	            return ResponseEntity.ok("Paiement supprimé avec succès.");
	        } catch (IllegalArgumentException | IllegalStateException ex) {
	            return ResponseEntity.badRequest().body(ex.getMessage());
	        }
	    }

	    @GetMapping("/card/{cardNumber}")
	    public ResponseEntity<List<Payment>> getPaymentsByCard(@PathVariable String cardNumber) {
	        try {
	            List<Payment> payments = paymentService.getPaymentsByCard(cardNumber);
	            return ResponseEntity.ok(payments);
	        } catch (IllegalStateException | EntityNotFoundException ex) {
	            return ResponseEntity.badRequest().body(null);
	        }
	    }

	    //@PreAuthorize("hasRole('EMPLOYEE')")
	    @PutMapping("/validate/{cardNum}/{menuId}")
	    public ResponseEntity<String> validatePayment(@PathVariable Card cardNum, @PathVariable Menu menuId) {
	        PaymentId paymentId = new PaymentId(cardNum, menuId);  
	        try {
	            paymentService.validatePayment(paymentId);
	            return ResponseEntity.ok("Paiement validé avec succès.");
	        } catch (IllegalStateException | EntityNotFoundException ex) {
	            return ResponseEntity.badRequest().body(ex.getMessage());
	        }
	    }

	    @GetMapping("/receipt/{cardNum}/{menuId}")
	    public ResponseEntity<String> generateReceipt(@PathVariable Card cardNum, @PathVariable Menu menuId) {
	    	 PaymentId paymentId = new PaymentId(cardNum, menuId);
	    	try {
	            String receipt = paymentService.generateReceipt(paymentId);
	            return ResponseEntity.ok(receipt);
	        } catch (IllegalStateException | EntityNotFoundException ex) {
	            return ResponseEntity.badRequest().body(ex.getMessage());
	        }
	    }
}
