package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.User;
import tn.uma.isamm.repositories.UserRepository;
import tn.uma.isamm.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	    @Autowired
	    private UserRepository userRepository;

	    @Override
	    public User save(User user) {
	        return userRepository.save(user);
	    }

	    @Override
	    public User findById(Long id) {
	        Optional<User> user = userRepository.findById(id);
	        return user.orElse(null); 
	    }

	    @Override
	    public List<User> findAll() {
	        return userRepository.findAll();
	    }

	    @Override
	    public User update(Long id, User user) {
	        if (id == null) {
	            throw new IllegalArgumentException("User ID must not be null");
	        }

	        if (!userRepository.existsById(id)) {
	            throw new RuntimeException("User with ID " + id + " does not exist");
	        }
	        user.setId(id);
	        return userRepository.save(user);
	    }

	    @Override
	    public void delete(Long id) {
	        userRepository.deleteById(id);
	    }

}
