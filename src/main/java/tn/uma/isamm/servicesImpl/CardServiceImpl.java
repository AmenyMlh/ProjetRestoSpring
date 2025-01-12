package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Student;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.CardRepository;
import tn.uma.isamm.services.CardService;
import tn.uma.isamm.services.NotificationService;

@Service
public class CardServiceImpl implements CardService {

	private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    
    private static final double SOLDE_MINIMAL = 10.0;

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }
    
    @Override
    public Card createNewCardForStudent(String oldCardNum, String newCardNum) {
        Card oldCard = cardRepository.findById(oldCardNum)
                .orElseThrow(() -> new RuntimeException("Ancienne carte introuvable avec le numéro : " + oldCardNum));

        Student student = oldCard.getStudent();
        if (student == null) {
            throw new RuntimeException("Aucun étudiant associé à l'ancienne carte.");
        }

        if (!oldCard.getIsBlocked()) {
            oldCard.setIsBlocked(true);
            cardRepository.save(oldCard);
        }

        Optional<Card> existingActiveCard = cardRepository.findByStudentAndIsBlockedFalse(student);
        if (existingActiveCard.isPresent()) {
            throw new IllegalStateException("Cet étudiant a déjà une carte active.");
        }

        Card newCard = new Card();
        newCard.setNumCarte(newCardNum);
        newCard.setStudent(student);
        newCard.setSolde(oldCard.getSolde());
        oldCard.setSolde(0.0);
        newCard.setIsBlocked(false); 

        return cardRepository.save(newCard);
    }


    @Override
    public Card findByNumCarte(String numCarte) {
        Optional<Card> card = cardRepository.findById(numCarte);
        return card.orElseThrow(() -> new EntityNotFoundException("Carte avec le numéro " + numCarte + " non trouvée."));
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Card update(String numCarte, Card card) {
        if (card == null || card.getNumCarte() == null) {
            throw new IllegalArgumentException("La carte et son numéro ne doivent pas être nuls.");
        }

        if (!cardRepository.existsById(numCarte)) {
            throw new EntityNotFoundException("Carte avec le numéro " + numCarte + " non trouvée.");
        }
        card.setNumCarte(numCarte); 
        return cardRepository.save(card);
    }

    @Override
    public void delete(String numCarte) {
        if (!cardRepository.existsById(numCarte)) {
            throw new EntityNotFoundException("Carte avec le numéro " + numCarte + " non trouvée.");
        }
        cardRepository.deleteById(numCarte);
    }
    
    @Override
    public Double getSolde(String numCarte) {
        Card card = findByNumCarte(numCarte);
        if (card.getIsBlocked()) {
            throw new IllegalStateException("La carte est bloquée, impossible de vérifier le solde.");
        }
        return card.getSolde();
    }

    @Override
    public Card recharge(String numCarte, Double montant) {
        if (montant == null || montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0.");
        }

        Card card = findByNumCarte(numCarte);
        if (card.getIsBlocked()) {
            throw new IllegalStateException("La carte est bloquée, impossible de recharger.");
        }

        card.setSolde(card.getSolde() + montant);
        return cardRepository.save(card);
    }


    @Override
    public Card blockCard(String numCarte) {
        Card card = findByNumCarte(numCarte);
        if (card.getIsBlocked()) {
            throw new IllegalArgumentException("La carte est déjà bloquée.");
        }

        card.setIsBlocked(true);  
        return cardRepository.save(card);  
    }
    
    @Override
    public Card unblockCard(String numCarte) {
        Card card = findByNumCarte(numCarte);
        if (!card.getIsBlocked()) {
            throw new IllegalArgumentException("La carte n'est pas bloquée.");
        }

        card.setIsBlocked(false);  
        return cardRepository.save(card);  
    }
    
    @Override
    public void checkCardStatus(String numCarte) {
       
    }
    @Override
    public void deductFromCard(String numCarte, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à 0.");
        }

        Card card = findByNumCarte(numCarte);

        if (card.getIsBlocked()) {
            throw new IllegalStateException("La carte est bloquée, impossible de déduire des fonds.");
        }

        if (card.getSolde() < amount) {
            throw new IllegalStateException("Solde insuffisant pour effectuer cette transaction.");
        }

        card.setSolde(card.getSolde() - amount);

        cardRepository.save(card);
    }

}
