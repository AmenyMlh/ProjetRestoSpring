package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Ingredient;
import tn.uma.isamm.entities.Student;
import tn.uma.isamm.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendLowBalanceNotification(Card card) {
        String message = "Alerte : Votre solde est faible. Il reste " + card.getSolde() + " DT sur votre carte.";
        sendNotificationToUser(card.getStudent(), message);
    }

    @Override
    public void sendBlockedCardNotification(Card card) {
        String message = "Alerte : Votre carte est actuellement bloquée. Veuillez contacter l'administration.";
        sendNotificationToUser(card.getStudent(), message);
    }

    public void sendLowStockAlert(Ingredient ingredient) {
        String message = "Alerte : Le stock de l'ingrédient " + ingredient.getName() + " est faible. Il reste " + ingredient.getQuantity() + " unités.";
        sendNotificationToAdmin(message);
    }

    private void sendNotificationToUser(Student student, String message) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage());
            helper.setFrom("admin@uma.isamm.tn");
            helper.setTo(student.getEmail()); 
            helper.setSubject("Notification de Solde");
            helper.setText(message);

            mailSender.send(helper.getMimeMessage());
            System.out.println("Email envoyé à l'utilisateur : " + student.getUsername() + " - " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotificationToAdmin(String message) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage());
            helper.setFrom("admin@uma.isamm.tn");
            helper.setTo("admin@uma.isamm.tn"); 
            helper.setSubject("Alerte de Stock Faible");
            helper.setText(message);

            mailSender.send(helper.getMimeMessage());
            System.out.println("Email envoyé à l'administrateur : " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
