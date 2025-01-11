package tn.uma.isamm.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.enums.MealType;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Optional<Menu> findById(Long id);
	
	List<Menu> findByDate(LocalDate date);

	List<Menu> findByType(MealType type);
	
	List<Menu> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
