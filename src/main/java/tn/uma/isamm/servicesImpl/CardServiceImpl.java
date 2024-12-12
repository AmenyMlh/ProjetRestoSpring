package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.repositories.CardRepository;
import tn.uma.isamm.services.CardService;

@Service
public class CardServiceImpl implements CardService{
	  @Autowired
	    private CardRepository cardRepository;

	    @Override
	    public Card save(Card card) {
	        return cardRepository.save(card);
	    }

	    @Override
	    public Card findByNumCarte(String numCarte) {
	        Optional<Card> card = cardRepository.findById(numCarte);
	        return card.orElse(null);
	    }

	    @Override
	    public List<Card> findAll() {
	        return cardRepository.findAll();
	    }

	    @Override
	    public Card update(String numCarte, Card card) {
	        if (card == null || card.getNumCarte() == null) {
	            throw new IllegalArgumentException("Card and its numCarte must not be null");
	        }

	        if (!cardRepository.existsById(numCarte)) {
	            throw new RuntimeException("Card with numCarte " + numCarte + " does not exist");
	        }
	        card.setNumCarte(numCarte); 
	        return cardRepository.save(card);
	    }

	    @Override
	    public void delete(String numCarte) {
	        cardRepository.deleteById(numCarte);
	    }
}
