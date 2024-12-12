package tn.uma.isamm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card savedCard = cardService.save(card);
        return ResponseEntity.ok(savedCard);
    }

    @GetMapping("/{numCarte}")
    public ResponseEntity<Card> getCardByNumCarte(@PathVariable String numCarte) {
        Card card = cardService.findByNumCarte(numCarte);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{numCarte}")
    public ResponseEntity<Card> updateCard(@PathVariable String numCarte, @RequestBody Card card) {
        Card updatedCard = cardService.update(numCarte, card);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{numCarte}")
    public ResponseEntity<Void> deleteCard(@PathVariable String numCarte) {
        cardService.delete(numCarte);
        return ResponseEntity.noContent().build();
    }
}
