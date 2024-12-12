package tn.uma.isamm.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uma.isamm.entities.Menu;
import tn.uma.isamm.repositories.MenuRepository;
import tn.uma.isamm.services.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	 @Autowired
	    private MenuRepository menuRepository;

	    @Override
	    public Menu save(Menu menu) {
	        return menuRepository.save(menu);
	    }

	    @Override
	    public Menu findById(Long id) {
	        Optional<Menu> menu = menuRepository.findById(id);
	        return menu.orElse(null);
	    }

	    @Override
	    public List<Menu> findAll() {
	        return menuRepository.findAll();
	    }

	    @Override
	    public Menu update(Long id, Menu menu) {
	        if (id == null) {
	            throw new IllegalArgumentException("Menu ID must not be null");
	        }

	        if (!menuRepository.existsById(id)) {
	            throw new RuntimeException("Menu with ID " + id + " does not exist");
	        }
	        menu.setId(id);
	        return menuRepository.save(menu);
	    }


	    @Override
	    public void delete(Long id) {
	        menuRepository.deleteById(id);
	    }
}
