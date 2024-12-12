package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.User;

public interface UserService {
	    User save(User user);
	    User findById(Long id);
	    List<User> findAll();
	    User update(Long id, User user);
	    void delete(Long id);
}
