package tn.uma.isamm.services;

import java.util.List;
import java.util.Map;

import tn.uma.isamm.entities.Card;

public interface CardService {
	//Card save(Card card);
    Card findByNumCarte(String numCarte);
    List<Card> findAll();
    Card update(String numCarte,Card card);
    void delete(String numCarte);
	Double getSolde(String numCarte);
	Card recharge(String numCarte, Double montant);
	Card blockCard(String numCarte);
	Card unblockCard(String numCarte);
	void checkCardStatus(String numCarte);
	void deductFromCard(String numCarte, double amount);
	Card createNewCardForStudent(String oldCardNum, String newCardNum);
	Card getCardByStudentId(Long studentId);
	Card saveCard(Map<String, Object> data);
}
