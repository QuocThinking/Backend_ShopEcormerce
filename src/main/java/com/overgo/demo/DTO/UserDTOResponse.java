package com.overgo.demo.DTO;

import org.springframework.util.ObjectUtils;

import com.overgo.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {

	private Long id;
	private String fullname;
	private String username;
	private String phone;
	private String email;
	private String password;
	private Long cartId;
	public UserDTOResponse(User user) {
		this.id = user.getId();
		this.fullname = user.getFullname();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.password = user.getPassword();
		if(!ObjectUtils.isEmpty(user.getCart())) {
			this.cartId = user.getCart().getId();
		}
	}
}
