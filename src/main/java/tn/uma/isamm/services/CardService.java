package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Card;

public interface CardService {
	Card save(Card card);
    Card findByNumCarte(String numCarte);
    List<Card> findAll();
    Card update(String numCarte,Card card);
    void delete(String numCarte);
}
