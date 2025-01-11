package tn.uma.isamm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.entities.Student;
import tn.uma.isamm.enums.UserRole;

public interface StudentRepository extends JpaRepository<Student, Long>{

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
