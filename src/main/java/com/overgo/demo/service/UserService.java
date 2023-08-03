package com.overgo.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.overgo.demo.model.User;

@Service
public interface UserService {
	Optional<User> findByfullname(String fullname);
	
	User addUser(Map<String, Object> newUser);
	
	User updateUser(Map<String, Object> newUser, Long Id);
	
	User findUserById(Long id);
	
	List<User> getAllUser();
	
	public void removeUser(Long id);
}
