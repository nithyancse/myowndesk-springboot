package com.myowndesk.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myowndesk.domain.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {
	
	List<Menu> findByNameAndStatus(String name, String status);
	List<Menu> findByUserIdAndStatus(long userId, String status);

}
