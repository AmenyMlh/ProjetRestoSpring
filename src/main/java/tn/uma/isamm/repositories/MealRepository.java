package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

}
