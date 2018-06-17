package com.myowndesk.service.inter;

import java.util.List;

import com.myowndesk.domain.Topic;

public interface ITopicService {

	List<Topic> fetchTopicList(long menuId, String type, String status);

	boolean addTopic(Topic topic);

	boolean updateTopic(Topic topic);
	
	void deleteTopic(Topic topic);

	Topic fetchTopicDetail(long id);

}
