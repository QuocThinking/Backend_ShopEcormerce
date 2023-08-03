package com.overgo.demo.DTO;

import org.springframework.util.ObjectUtils;

import com.overgo.demo.model.Address;
import com.overgo.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTOResponse {

	private Long id;
	private Long userId;
	private String city;
	private String street;
	private String country;
	private String name;
	public AddressDTOResponse(Address address) {
		this.id = address.getId();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.street = address.getStreet();
		if(!ObjectUtils.isEmpty(address.getUser())){
			User user = address.getUser();
			this.userId = user.getId();
			this.name = user.getFullname();
			
		}
	}
}
