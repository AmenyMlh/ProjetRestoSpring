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

	 @Autowired
	    private CardService cardService;

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PostMapping
	    public ResponseEntity<Card> saveCard(@RequestBody Card card) {
	        Card savedCard = cardService.save(card);
	        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
	    }
        
	    @GetMapping("/{numCarte}")
	    public ResponseEntity<Card> getCard(@PathVariable String numCarte) {
	        Card card = cardService.findByNumCarte(numCarte);
	        return new ResponseEntity<>(card, HttpStatus.OK);
	    }
        
	    @Secured("hasRole('ROLE_ADMIN')")
	    @GetMapping
	    public ResponseEntity<List<Card>> getAllCards() {
	        List<Card> cards = cardService.findAll();
	        return new ResponseEntity<>(cards, HttpStatus.OK);
	    }

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PutMapping("/{numCarte}")
	    public ResponseEntity<Card> updateCard(@PathVariable String numCarte, @RequestBody Card card) {
	        Card updatedCard = cardService.update(numCarte, card);
	        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
	    }

	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @DeleteMapping("/{numCarte}")
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
	    @PutMapping("/recharge/{numCarte}")
	    public ResponseEntity<Card> rechargeCard(@PathVariable String numCarte, @RequestParam Double montant) {
	        Card rechargedCard = cardService.recharge(numCarte, montant);
	        return new ResponseEntity<>(rechargedCard, HttpStatus.OK);
	    }

	  
	    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	    @PutMapping("/block/{numCarte}")
	    public ResponseEntity<Card> blockCard(@PathVariable String numCarte) {
	        Card blockedCard = cardService.blockCard(numCarte);
	        return new ResponseEntity<>(blockedCard, HttpStatus.OK);
	    }

	 
	    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
	    @PutMapping("/unblock/{numCarte}")
	    public ResponseEntity<Card> unblockCard(@PathVariable String numCarte) {
	        Card unblockedCard = cardService.unblockCard(numCarte);
	        return new ResponseEntity<>(unblockedCard, HttpStatus.OK);
	    }
}
