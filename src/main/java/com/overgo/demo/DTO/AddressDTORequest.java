package com.overgo.demo.DTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTORequest {

	private Long userId;
	private String city;
	private String country;
	private String street;
	
	public AddressDTORequest(Map<String, Object> newrequest) {
		this.city = newrequest.get("city").toString();
		this.country = newrequest.get("country").toString();
		this.street = newrequest.get("street").toString();
		this.userId = Long.parseLong(newrequest.get("userId").toString());
	}
}
