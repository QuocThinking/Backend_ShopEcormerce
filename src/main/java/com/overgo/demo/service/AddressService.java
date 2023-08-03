package com.overgo.demo.service;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.overgo.demo.model.Address;
import com.overgo.demo.model.User;

@Service
public interface AddressService {

	List<Address> getAllAddress();
	
	public void removeAddress(Long id);
	
	 Address findAddressById(Long id);
	 
	 Address updateAddress(Map<String, Object> newAddres , Long id);
	
	Address addAddress (Map<String, Object> newAddress, User user);
}
