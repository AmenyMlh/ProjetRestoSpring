package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import tn.uma.isamm.entities.Employee;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.repositories.EmployeeRepository;
import tn.uma.isamm.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    	this.employeeRepository = employeeRepository;
     }

    @Override
    public Employee save(Employee employee) {
    	if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("L'email existe déjà : " + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
        	Employee existingEmployee = employeeRepository.findById(employee.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employé avec ID " + employee.getId() + " n'existe pas"));

        	existingEmployee.setUsername(employee.getUsername());
        	existingEmployee.setEmail(employee.getEmail());
        	existingEmployee.setPassword(employee.getPassword());
        	existingEmployee.setFirstName(employee.getFirstName());
        	existingEmployee.setLastName(employee.getLastName());
        	existingEmployee.setPhone(employee.getPhone());
        	existingEmployee.setPosition(employee.getPosition());

            return employeeRepository.save(existingEmployee);
        }
        throw new EntityConflictException("L'employé avec ID " + employee.getId() + " n'existe pas");
    }


    @Override
    public void delete(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new EntityConflictException("L'employé avec ID " + id + " existe déjà.");
        }
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new EntityConflictException("L'employé avec ID " + id + " n'existe pas."));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
