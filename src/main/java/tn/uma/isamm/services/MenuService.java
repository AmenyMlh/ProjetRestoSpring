package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Menu;

public interface MenuService {
	Menu save(Menu menu);
    Menu findById(Long id);
    List<Menu> findAll();
    Menu update(Long id,Menu menu);
    void delete(Long id);
}
