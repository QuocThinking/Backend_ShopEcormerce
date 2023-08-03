package com.overgo.demo.DTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTORequest {

	private String username;
	private String fullname;
	private String email;
	private String phone;
	private String password;
	
	public UserDTORequest(Map<String , Object> newUser) {
		this.username = newUser.get("username").toString();
		this.fullname = newUser.get("fullname").toString();
		this.email = newUser.get("email").toString();
		this.phone = newUser.get("phone").toString();
		this.password = newUser.get("password").toString();
		
	}
}
