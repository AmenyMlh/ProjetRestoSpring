package tn.uma.isamm.services;

import tn.uma.isamm.entities.Card;

public interface NotificationService {

	void sendLowBalanceNotification(Card card);
	
	void sendBlockedCardNotification(Card card);
	
}
