package com.myowndesk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//import com.myowndesk.GlobalProperties;
import com.myowndesk.constant.IConstant;
import com.myowndesk.domain.CustResponse;
import com.myowndesk.domain.Topic;
import com.myowndesk.service.inter.IMessageByLocaleService;
import com.myowndesk.service.inter.ITopicService;

@RestController
@RequestMapping("/")
public class TopicController {

	// @Autowired
	// private GlobalProperties global;

	@Autowired
	IMessageByLocaleService messageLocale;

	@Autowired
	ITopicService iTopicService;

	@GetMapping("fetchTopicList")
	public List<Topic> fetchTopicList(@RequestParam(value = "menuId") long menuId,
			@RequestParam(value = "type", required = false) String type) {
		List<Topic> topicList = iTopicService.fetchTopicList(menuId, type, IConstant.ACTIVE);
		return topicList;
	}

	@PostMapping("addTopic")
	public ResponseEntity<?> addTopic(@Valid @RequestBody Topic topic, UriComponentsBuilder builder, Errors errors) {
		topic.setStatus(IConstant.ACTIVE);
		boolean flag = iTopicService.addTopic(topic);
		String responseMsg = null;
		if (flag == false) {
			responseMsg = messageLocale.getMessage("topic.posted.already");
			CustResponse restAlready = new CustResponse(responseMsg);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(restAlready);
		}
		responseMsg = messageLocale.getMessage("topic.posted.success");
		CustResponse success = new CustResponse(responseMsg);
		return ResponseEntity.created(null).body(success);
	}

	@PutMapping("updateTopic")
	public ResponseEntity<?> updateTopic(@Valid @RequestBody Topic topicReq) {
		Topic topic = iTopicService.fetchTopicDetail(topicReq.getId());
		topic.setTitle(topicReq.getTitle());
		topic.setType(topicReq.getType());
		topic.setDescription(topicReq.getDescription());
		boolean flag = iTopicService.updateTopic(topic);
		if (flag == false) {
			CustResponse duplicateTopic = new CustResponse(messageLocale.getMessage("topic.posted.already"));
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(duplicateTopic);
		}
		return new ResponseEntity<Topic>(topic, HttpStatus.OK);
	}

	@DeleteMapping("deleteTopic")
	public ResponseEntity<Void> deleteTopic(@RequestParam(value = "id") long id) {
		Topic topic = iTopicService.fetchTopicDetail(id);
		topic.setStatus(IConstant.DELETE);
		iTopicService.deleteTopic(topic);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}