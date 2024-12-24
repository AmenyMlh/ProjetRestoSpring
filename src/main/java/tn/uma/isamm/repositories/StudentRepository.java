package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
