package tn.uma.isamm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Card;

public interface CardRepository extends JpaRepository<Card, String>{
    Card findByNumCarte(String numCarte); 


}
