package com.myowndesk.service.inter;

import java.util.List;

import com.myowndesk.domain.User;

public interface IUserService {

	public User fetchUserDetail(long id);

	public List<User> fetchAllUsers();

	public boolean addUser(User userRegistration);

	public void updateUser(User userRegistration);

	public void deleteUser(long id);

	public void addName(long id, String name);
	
	public Long validateUser(String emailId, String password);

}