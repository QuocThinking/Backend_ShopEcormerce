package com.overgo.demo.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.overgo.demo.model.Address;
import com.overgo.demo.model.User;
import com.overgo.demo.repository.AddressRepository;
import com.overgo.demo.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressRepository addressRepository;



	@Override
	public Address addAddress(Map<String, Object> newAddress, User user) {
		Address address = new Address();
		address.setCity(newAddress.get("city").toString());
		address.setCountry(newAddress.get("country").toString());
		address.setStreet(newAddress.get("street").toString());
		address.setUser(user);
		return this.addressRepository.save(address);
	}



	@Override
	public List<Address> getAllAddress() {
		return this.addressRepository.findAll();
	}



	@Override
	public void removeAddress(Long id) {
		 this.addressRepository.deleteById(id);
		
	}



	@Override
	public Address findAddressById(Long id) {
		return this.addressRepository.findById(id).orElse(null);
	}



	@Override
	public Address updateAddress(Map<String, Object> newAddres, Long id) {
		Address foundAddress = this.findAddressById(id);
		foundAddress.setCity(newAddres.get("city").toString());
		foundAddress.setCountry(newAddres.get("country").toString());
		foundAddress.setStreet(newAddres.get("street").toString());
		return this.addressRepository.save(foundAddress);
		
	}

	
}
