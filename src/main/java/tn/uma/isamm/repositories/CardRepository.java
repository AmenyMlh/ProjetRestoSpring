package tn.uma.isamm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Card;
import tn.uma.isamm.entities.Student;

public interface CardRepository extends JpaRepository<Card, String>{
    Card findByNumCarte(String numCarte); 

    Optional<Card> findByStudentAndIsBlockedFalse(Student student);
    boolean existsByStudentAndIsBlockedFalse(Student student);
    boolean existsByNumCarte(String numCarte);

	Card findByStudentId(Long studentId);
}
