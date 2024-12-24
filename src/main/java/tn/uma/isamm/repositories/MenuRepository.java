package tn.uma.isamm.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findByDate(LocalDate date);

	List<Menu> findByType(MealType type);
}
