package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.User;
import tn.uma.isamm.repositories.UserRepository;
import tn.uma.isamm.services.UserService;


@Service
public class UserServiceImplement  implements UserService {
    
    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserByUserName(String userName) {
        // TODO Auto-generated method stub
        return userRepository.findByUsername(userName).orElse(null);
    }

    
}
