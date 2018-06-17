package com.myowndesk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myowndesk.domain.CustError;
import com.myowndesk.domain.User;
import com.myowndesk.service.inter.IMessageByLocaleService;
import com.myowndesk.service.inter.IUserService;
import com.myowndesk.util.Utils;

@RestController
@RequestMapping("/")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private IUserService iUserService;

	@Autowired
	IMessageByLocaleService messageLocale;

	@PostMapping("login")
	public ResponseEntity<?> validateLogin(@RequestParam(value = "emailId") String emailId,
			@RequestParam(value = "password") String password) {

		String errorMsg = null;
		Long userId = 0L;
		User user = null;

		if (!Utils.isEmpty(emailId) && !Utils.isEmpty(password)) {
			userId = iUserService.validateUser(emailId, password);
			if (userId == 0) {
				errorMsg = messageLocale.getMessage("user.auth.failed");
				//errorMsg = messageLocale.getMessage("welcome.message", Locale.US);
				logger.info("Authendication failed --> emailId:" + emailId + ", password: " + password);
			}
		} else {
			errorMsg = messageLocale.getMessage("user.auth.empty");
		}
		if (!Utils.isEmpty(errorMsg)) {
			CustError error = new CustError(errorMsg);
			if (error != null) {
				return ResponseEntity.badRequest().body(error);
			}
		}
		user = iUserService.fetchUserDetail(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
