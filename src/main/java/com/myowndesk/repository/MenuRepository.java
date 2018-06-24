package com.myowndesk.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myowndesk.domain.Menu;

public interface MenuRepository extends CrudRepository<Menu, Long> {
	
	List<Menu> findByNameAndStatusAndUserId(String name, String status, long userId);
	List<Menu> findByUserIdAndStatus(long userId, String status);

}
