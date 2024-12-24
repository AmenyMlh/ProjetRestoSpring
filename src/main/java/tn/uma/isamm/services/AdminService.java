package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Admin;

public interface AdminService {

	 Admin save(Admin admin); 
	    Admin update(Admin admin); 
	    void delete(Long id); 
	    Admin findById(Long id); 
	    List<Admin> findAll(); 
}
