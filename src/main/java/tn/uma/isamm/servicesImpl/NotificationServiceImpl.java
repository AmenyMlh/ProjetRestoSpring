package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import tn.uma.isamm.services.NotificationService;
import jakarta.annotation.PostConstruct;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void initializeTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void sendSms(String to, String messageBody) {
        try {
            if (!to.startsWith("+")) {
                to = "+" + to; // S'assurer que le numéro commence par "+"
            }
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to), // Numéro du destinataire
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber), // Numéro Twilio
                    messageBody // Message à envoyer
            ).create();
            System.out.println("Message sent: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }
}
