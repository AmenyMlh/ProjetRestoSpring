package tn.uma.isamm.services;

import tn.uma.isamm.entities.Card;

public interface NotificationService {
	void sendSms(String to, String messageBody);
}
