package com.myowndesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myowndesk.domain.Topic;
import com.myowndesk.repository.TopicRepository;
import com.myowndesk.service.inter.ITopicService;
import com.myowndesk.util.Utils;

@Service
public class TopicService implements ITopicService {
	
	@Autowired
	TopicRepository topicRepository;

	@Override
	public List<Topic> fetchTopicList(long menuId, String type, String status) {
		if(!Utils.isEmpty(type)) {
			return topicRepository.findByMenuIdAndTypeAndStatus(menuId, type, status);
		}
		return topicRepository.findByMenuIdAndStatus(menuId, status);
	}

	@Override
	public boolean addTopic(Topic topic) {
		List<Topic> list = topicRepository.findByMenuIdAndTitleAndStatus(topic.getMenuId(), topic.getTitle(), topic.getStatus()); 	
        if (list.size() > 0) {
        	return false;
        } else {
        	topicRepository.save(topic);
        }
        return true;
	}

	@Override
	public boolean updateTopic(Topic topic) {
		List<Topic> list = topicRepository.findByMenuIdAndTitleAndStatus(topic.getMenuId(), topic.getTitle(), topic.getStatus()); 	
        //current topic name not changed but other values can change
		if (list.size() > 0) {
        	return false;
        } else {
        	topicRepository.save(topic);
        }
        return true;
		
	}
	
	@Override
	public void deleteTopic(Topic topic) {
        topicRepository.save(topic);
	}

	@Override
	public Topic fetchTopicDetail(long id) {
		return topicRepository.findById(id).get();
	}

}
