package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
