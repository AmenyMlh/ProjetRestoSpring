package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Meal;
import tn.uma.isamm.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
