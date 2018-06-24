package com.myowndesk.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.myowndesk.domain.Topic;

public interface TopicRepository extends CrudRepository<Topic, Long> {
	
	List<Topic> findByMenuIdAndTitleAndStatus(long menuId, String title, String status);
	List<Topic> findByMenuIdAndStatus(long menuId, String status);
	List<Topic> findByMenuIdAndTypeAndStatus(long menuId, String type, String status);
	

}
