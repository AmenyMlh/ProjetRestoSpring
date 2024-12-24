package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Employee;

public interface EmployeeService {
	 Employee save(Employee employee); 
	    Employee update(Employee employee); 
	    void delete(Long id); 
	    Employee findById(Long id);
	    List<Employee> findAll();
}
