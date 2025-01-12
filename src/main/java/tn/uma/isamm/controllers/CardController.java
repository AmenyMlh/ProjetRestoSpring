package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.services.CardService;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

	private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PostMapping("/admin")
	    public ResponseEntity<Card> saveCard(@RequestBody Card card) {
	        Card savedCard = cardService.save(card);
	        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
	    }
        
	    @GetMapping("/{numCarte}")
	    public ResponseEntity<Card> getCard(@PathVariable String numCarte) {
	        Card card = cardService.findByNumCarte(numCarte);
	        return new ResponseEntity<>(card, HttpStatus.OK);
	    }
        
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping("/admin")
	    public ResponseEntity<List<Card>> getAllCards() {
	        List<Card> cards = cardService.findAll();
	        return new ResponseEntity<>(cards, HttpStatus.OK);
	    }

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PutMapping("/admin/{numCarte}")
	    public ResponseEntity<Card> updateCard(@PathVariable String numCarte, @RequestBody Card card) {
	        Card updatedCard = cardService.update(numCarte, card);
	        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
	    }

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @DeleteMapping("/admin/{numCarte}")
	    public ResponseEntity<Void> deleteCard(@PathVariable String numCarte) {
	        cardService.delete(numCarte);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
	    @GetMapping("/solde/{numCarte}")
	    public ResponseEntity<Double> getCardSolde(@PathVariable String numCarte) {
	        Double solde = cardService.getSolde(numCarte);
	        return new ResponseEntity<>(solde, HttpStatus.OK);
	    }    


	    @PreAuthorize("hasRole('ROLE_STUDENT')")
	    @PutMapping("/student/recharge/{numCarte}")
	    public ResponseEntity<Card> rechargeCard(@PathVariable String numCarte, @RequestParam Double montant) {
	        Card rechargedCard = cardService.recharge(numCarte, montant);
	        return new ResponseEntity<>(rechargedCard, HttpStatus.OK);
	    }

	  
	    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	    @PutMapping("/employee/block/{numCarte}")
	    public ResponseEntity<Card> blockCard(@PathVariable String numCarte) {
	        Card blockedCard = cardService.blockCard(numCarte);
	        return new ResponseEntity<>(blockedCard, HttpStatus.OK);
	    }

	 
	    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	    @PutMapping("/employee/unblock/{numCarte}")
	    public ResponseEntity<Card> unblockCard(@PathVariable String numCarte) {
	        Card unblockedCard = cardService.unblockCard(numCarte);
	        return new ResponseEntity<>(unblockedCard, HttpStatus.OK);
	    }
}
