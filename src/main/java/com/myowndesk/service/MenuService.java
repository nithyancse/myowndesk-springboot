package com.myowndesk.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myowndesk.domain.Menu;
import com.myowndesk.repository.MenuRepository;
import com.myowndesk.service.inter.IMenuService;

@Service
public class MenuService implements IMenuService {
	
	@Autowired
	MenuRepository menuRepository;

	@Override
	public boolean addMenu(Menu menu) {
		List<Menu> list = menuRepository.findByNameAndStatusAndUserId(menu.getName(), menu.getStatus(), menu.getUserId()); 	
        if (list.size() > 0) {
        	return false;
        } else {
        	menuRepository.save(menu);
        }
        return true;
	}

	@Override
	public boolean updateMenu(Menu menu) {
		List<Menu> list = menuRepository.findByNameAndStatusAndUserId(menu.getName(), menu.getStatus(), menu.getUserId()); 	
        if (list.size() > 1) {
        	return false;
        } else {
        	menuRepository.save(menu);
        }
        return true;
	}

	@Override
	public Menu fetchMenuDetail(long id) {
		return menuRepository.findById(id).get();
	}

	@Override
	public List<Menu> fetchMenuList(long userId, String status) {
		return menuRepository.findByUserIdAndStatus(userId, status);
	}

	@Override
	public void deleteMenu(Menu menu) {
		menuRepository.save(menu);
		
	}

}
