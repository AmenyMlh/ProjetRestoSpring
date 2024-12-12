package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.Payment;
import tn.uma.isamm.services.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.save(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/card/{cardId}/meal/{mealId}")
    public ResponseEntity<Payment> getPaymentByCardAndMeal(@PathVariable Long cardId, @PathVariable Long mealId) {
        Card card = new Card();  
        card.setNumCarte(cardId.toString());  
        
        Meal meal = new Meal();
        meal.setId(mealId);  

        Payment payment = paymentService.findByCardAndMeal(card, meal);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping
    public ResponseEntity<Void> deletePayment(@RequestBody Payment payment) {
        paymentService.delete(payment);
        return ResponseEntity.noContent().build();
    }
}
