package com.myowndesk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myowndesk.constant.IConstant;
import com.myowndesk.domain.User;
import com.myowndesk.repository.UserRepository;
import com.myowndesk.service.inter.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	UserRepository userRepository;

	public User fetchUserDetail(long id){
		User user = userRepository.findById(id).get();
		return user;
		
	}

	@Override
	public List<User> fetchAllUsers() {
		List<User> userList = new ArrayList<>();
		userRepository.findAll().forEach(e -> userList.add(e));
		return userList;
	}

	@Override
	public boolean addUser(User user) {
		List<User> list = userRepository.findByEmailId(user.getEmailId()); 	
        if (list.size() > 0) {
        	return false;
        } else {
        	userRepository.save(user);
        }
        return true;
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void addName(long id, String name) {
		User user = userRepository.findById(id).get();
		user.setName(name);
		userRepository.save(user);
	}

	@Override
	public Long validateUser(String emailId, String password) {
		List<User> list = userRepository.findByEmailIdAndPasswordAndStatus(emailId, password, IConstant.ACTIVE); 	
        if (list.size() > 0) {
        	return list.get(0).getId();
        } 
        return 0L;
	}
	
}