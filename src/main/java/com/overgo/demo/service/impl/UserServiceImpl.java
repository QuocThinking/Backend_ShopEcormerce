package com.overgo.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Cart;
import com.overgo.demo.model.User;
import com.overgo.demo.repository.UserRepository;
import com.overgo.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByfullname(String fullname) {
		return this.userRepository.findByfullname(fullname);
	}

	@Override
	public User addUser(Map<String, Object> newUser) {
		User user = new User();
		user.setFullname(newUser.get("fullname").toString());
		user.setPassword(newUser.get("password").toString());
		user.setPhone(newUser.get("phone").toString());
		user.setUsername(newUser.get("username").toString());
		user.setEmail(newUser.get("email").toString());
		Cart cart = new Cart();
		cart.setUser(user);
		user.setCart(cart);
		return this.userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}

	@Override
	public User updateUser(Map<String, Object> newUser, Long Id) {
		User user = this.findUserById(Id);
		user.setFullname(newUser.get("fullname").toString());
		user.setEmail(newUser.get("email").toString());
		user.setPhone(newUser.get("phone").toString());
		user.setPassword(newUser.get("password").toString());
		user.setUsername(newUser.get("username").toString());
		return this.userRepository.save(user);
	}

	@Override
	public User findUserById(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}

	@Override
	public void removeUser(Long id) {
		this.userRepository.deleteById(id);
		
	}

	
}
