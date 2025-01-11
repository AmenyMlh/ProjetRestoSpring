package tn.uma.isamm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.uma.isamm.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

	boolean existsByEmail(String email);

}
