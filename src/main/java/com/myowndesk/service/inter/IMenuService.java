package com.myowndesk.service.inter;

import java.util.List;

import com.myowndesk.domain.Menu;

public interface IMenuService {

	boolean addMenu(Menu menu);

	boolean updateMenu(Menu menu);
	
	void deleteMenu(Menu menu);

	Menu fetchMenuDetail(long id);

	List<Menu> fetchMenuList(long userId, String status);
	
	

}
