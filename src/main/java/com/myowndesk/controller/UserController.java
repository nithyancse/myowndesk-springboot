package com.myowndesk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.myowndesk.constant.IConstant;
import com.myowndesk.domain.CustError;
import com.myowndesk.domain.CustResponse;
import com.myowndesk.domain.User;
import com.myowndesk.service.inter.IMessageByLocaleService;
import com.myowndesk.service.inter.IUserService;
import com.myowndesk.util.Utils;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	IMessageByLocaleService messageLocale;

	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user, UriComponentsBuilder builder, Errors errors) {
		user.setStatus(IConstant.ACTIVE);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		boolean flag = iUserService.addUser(user);
		String responseMsg = null;
		if (flag == false) {
			responseMsg = messageLocale.getMessage("user.added.already");
			CustResponse restAlready = new CustResponse(responseMsg);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(restAlready);
		}
		responseMsg = messageLocale.getMessage("user.added.success");
		CustResponse success = new CustResponse(responseMsg);
		return ResponseEntity.created(null).body(success);
	}

	@PostMapping("/addName")
	public ResponseEntity<?> addName(@RequestParam(value = "id") long id, @RequestParam(value = "name") String name) {
		String errorMsg = null;
		if (!Utils.isEmpty(name)) {
			if(name.length() > 45)
				errorMsg = messageLocale.getMessage("user.name.less45");
		} else {
			errorMsg = messageLocale.getMessage("user.name.empty");
		}
		
		if (!Utils.isEmpty(errorMsg)) {
			CustError error = new CustError(errorMsg);
			if (error != null) {
				return ResponseEntity.badRequest().body(error);
			}
		}

		iUserService.addName(id, name);
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/fetchUserDetail")
	public ResponseEntity<User> fetchUserDetail(@RequestParam(value = "userId") long id) {
		User user = iUserService.fetchUserDetail(id);
		user.setPassword("");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/fetchAllUsers")
	public List<User> fetchAllUsers() {
		List<User> allUserList = iUserService.fetchAllUsers();
		return allUserList;
	}

	@PutMapping("/updateUser")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		iUserService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("/deleteUser")
	public ResponseEntity<Void> deleteUser(@RequestParam(value = "id") long id) {
		User user = iUserService.fetchUserDetail(id);
		user.setStatus(IConstant.DELETE);
		iUserService.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}