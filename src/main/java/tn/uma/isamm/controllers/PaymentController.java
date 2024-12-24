package tn.uma.isamm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
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

	 @Autowired
	    private PaymentService paymentService;

	    @PostMapping("/save")
	    public ResponseEntity<Payment> savePayment(@RequestBody Payment payment) {
	        try {
	            Payment savedPayment = paymentService.save(payment);
	            return ResponseEntity.ok(savedPayment);
	        } catch (IllegalArgumentException | IllegalStateException ex) {
	            return ResponseEntity.badRequest().body(null);
	        }
	    }

	    @GetMapping("/card/{cardNumber}/meal/{mealId}")
	    public ResponseEntity<Payment> getPaymentByCardAndMeal(@PathVariable String cardNumber, @PathVariable Long mealId) {
	        try {
	            Card card = new Card();
	            card.setNumCarte(cardNumber);
	            Meal meal = new Meal();
	            meal.setId(mealId);
	            Payment payment = paymentService.findByCardAndMeal(card, meal);
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

	    @PostMapping("/validate/{paymentId}")
	    public ResponseEntity<String> validatePayment(@PathVariable PaymentId paymentId) {
	        try {
	            paymentService.validatePayment(paymentId);
	            return ResponseEntity.ok("Paiement validé avec succès.");
	        } catch (IllegalStateException | EntityNotFoundException ex) {
	            return ResponseEntity.badRequest().body(ex.getMessage());
	        }
	    }

	    @GetMapping("/receipt/{paymentId}")
	    public ResponseEntity<String> generateReceipt(@PathVariable PaymentId paymentId) {
	        try {
	            String receipt = paymentService.generateReceipt(paymentId);
	            return ResponseEntity.ok(receipt);
	        } catch (IllegalStateException | EntityNotFoundException ex) {
	            return ResponseEntity.badRequest().body(ex.getMessage());
	        }
	    }
}
