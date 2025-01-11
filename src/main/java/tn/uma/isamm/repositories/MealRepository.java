package tn.uma.isamm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

}
