package tn.uma.isamm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.entities.Employee;
import tn.uma.isamm.enums.UserRole;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Employee> findByEmail(String email);  
    Optional<Employee> findByUsername(String username);
}
