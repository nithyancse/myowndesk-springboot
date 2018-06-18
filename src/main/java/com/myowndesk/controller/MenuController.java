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

import com.myowndesk.constant.IConstant;
import com.myowndesk.domain.CustResponse;
import com.myowndesk.domain.Menu;
import com.myowndesk.service.inter.IMenuService;
import com.myowndesk.service.inter.IMessageByLocaleService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private IMenuService iMenuService;
	
	@Autowired
	IMessageByLocaleService messageLocale;
	
	@GetMapping("/fetchMenuList")
	public List<Menu> fetchMenuList(@RequestParam(value = "userId") long userId) {
		List<Menu> menuList = iMenuService.fetchMenuList(userId, IConstant.ACTIVE);
		return menuList;
	}
	
	@PostMapping("/addMenu")
	public ResponseEntity<?> addMenu(@Valid @RequestBody Menu menu, UriComponentsBuilder builder, Errors errors) {
		menu.setStatus(IConstant.ACTIVE);
		boolean flag = iMenuService.addMenu(menu);
		String responseMsg = null;
		if (flag == false) {
			responseMsg = messageLocale.getMessage("menu.added.already");
			CustResponse restAlready = new CustResponse(responseMsg);
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(restAlready);
		}		
		List<Menu> menuList = iMenuService.fetchMenuList(menu.getUserId(), IConstant.ACTIVE);
		return ResponseEntity.status(HttpStatus.CREATED).body(menuList);
	}
	
	@PostMapping("/updateMenu")
	public ResponseEntity<?> updateMenu(@Valid @RequestBody Menu menuReq) {
		Menu menu = iMenuService.fetchMenuDetail(menuReq.getId());
		menu.setName(menuReq.getName());
		boolean flag = iMenuService.updateMenu(menu);
		if (flag == false) {
			CustResponse duplicateMenu = new CustResponse(messageLocale.getMessage("menu.added.already"));
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(duplicateMenu);
		}
		return new ResponseEntity<Menu>(menu, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMenu")
	public ResponseEntity<Void> deleteMenu(@RequestParam(value = "menuId") long menuId) {
		Menu menu = iMenuService.fetchMenuDetail(menuId);
		menu.setStatus(IConstant.DELETE);
		iMenuService.deleteMenu(menu);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
