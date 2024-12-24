package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Employee;
import tn.uma.isamm.exceptions.EntityConflictException;
import tn.uma.isamm.repositories.EmployeeRepository;
import tn.uma.isamm.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            return employeeRepository.save(employee);
        }
        throw new EntityConflictException("L'employé existe déjà avec le nom: " + employee.getUsername());
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
