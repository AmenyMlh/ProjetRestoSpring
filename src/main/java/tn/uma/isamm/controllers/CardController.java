package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.services.CardService;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

	 @Autowired
	    private CardService cardService;

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

	    @GetMapping
	    public ResponseEntity<List<Card>> getAllCards() {
	        List<Card> cards = cardService.findAll();
	        return new ResponseEntity<>(cards, HttpStatus.OK);
	    }

	    @PutMapping("/{numCarte}")
	    public ResponseEntity<Card> updateCard(@PathVariable String numCarte, @RequestBody Card card) {
	        Card updatedCard = cardService.update(numCarte, card);
	        return new ResponseEntity<>(updatedCard, HttpStatus.OK);
	    }

	    @DeleteMapping("/{numCarte}")
	    public ResponseEntity<Void> deleteCard(@PathVariable String numCarte) {
	        cardService.delete(numCarte);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    @GetMapping("/solde/{numCarte}")
	    public ResponseEntity<Double> getCardSolde(@PathVariable String numCarte) {
	        Double solde = cardService.getSolde(numCarte);
	        return new ResponseEntity<>(solde, HttpStatus.OK);
	    }

	    @PostMapping("/recharge/{numCarte}")
	    public ResponseEntity<Card> rechargeCard(@PathVariable String numCarte, @RequestParam Double montant) {
	        Card rechargedCard = cardService.recharge(numCarte, montant);
	        return new ResponseEntity<>(rechargedCard, HttpStatus.OK);
	    }

	  
	    @PostMapping("/block/{numCarte}")
	    public ResponseEntity<Card> blockCard(@PathVariable String numCarte) {
	        Card blockedCard = cardService.blockCard(numCarte);
	        return new ResponseEntity<>(blockedCard, HttpStatus.OK);
	    }

	 
	    @PostMapping("/unblock/{numCarte}")
	    public ResponseEntity<Card> unblockCard(@PathVariable String numCarte) {
	        Card unblockedCard = cardService.unblockCard(numCarte);
	        return new ResponseEntity<>(unblockedCard, HttpStatus.OK);
	    }
}
