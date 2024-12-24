package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Card;

public interface CardRepository extends JpaRepository<Card, String>{
	

}
