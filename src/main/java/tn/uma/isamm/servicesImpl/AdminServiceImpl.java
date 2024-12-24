package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.isamm.entities.Admin;
import tn.uma.isamm.exceptions.EntityNotFoundException;
import tn.uma.isamm.repositories.AdminRepository;
import tn.uma.isamm.services.AdminService;
import tn.uma.isamm.services.IngredientService;
import tn.uma.isamm.services.MealService;
import tn.uma.isamm.services.MenuService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        if (!adminRepository.existsById(admin.getId())) {
            throw new EntityNotFoundException("L'admin avec l'ID " + admin.getId() + " n'existe pas.");
        }
        return adminRepository.save(admin);
    }

    @Override
    public void delete(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityNotFoundException("L'admin avec l'ID " + id + " n'existe pas.");
        }
        adminRepository.deleteById(id);
    }

    @Override
    public Admin findById(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return admin.orElseThrow(() -> new EntityNotFoundException("L'admin avec l'ID " + id + " n'a pas été trouvé."));
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }
}
