package com.myowndesk.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myowndesk.constant.IConstant;
import com.myowndesk.domain.User;
import com.myowndesk.repository.UserRepository;
import com.myowndesk.service.inter.IUserService;

@Service(value = "userService")
public class UserService implements UserDetailsService, IUserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmailId(userName).get(0);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	

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
	
	@Override
	public boolean isEmailIdAvailable(String emailId) {
		List<User> list = userRepository.findByEmailId(emailId); 	
        if (list.size() > 0) {
        	return true;
        } else {
        	return false;
        }
        
	}
	
}