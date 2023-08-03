package com.overgo.demo.rest;

import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.overgo.demo.DTO.AddressDTORequest;
import com.overgo.demo.DTO.AddressDTOResponse;
import com.overgo.demo.contains.ResponseCode;
import com.overgo.demo.model.Address;
import com.overgo.demo.model.User;
import com.overgo.demo.service.AddressService;
import com.overgo.demo.service.UserService;

@RestController
@RequestMapping(path = "/address")
public class AddressController extends BaseRestController {
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserService userService;

	@PostMapping("")
	public ResponseEntity<?> addAddress(@RequestBody(required = true) Map<String, Object> newAddress, User user ){
		try {
			if(ObjectUtils.isEmpty(newAddress)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			AddressDTORequest request = new AddressDTORequest(newAddress);
			if(ObjectUtils.isEmpty(request.getCity()) || ObjectUtils.isEmpty(request.getCountry())
					|| ObjectUtils.isEmpty(request.getUserId()) || ObjectUtils.isEmpty(request.getStreet())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			
			Long userId = Long.parseLong(newAddress.get("userId").toString());
			User foundUser = this.userService.findUserById(userId);
			if(ObjectUtils.isEmpty(foundUser)) {
				 return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			
			Address insertAddress = this.addressService.addAddress(newAddress, foundUser);
			return super.success(new AddressDTOResponse(insertAddress));
		} catch (Exception e) {
			e.printStackTrace();
		}
		  return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@DeleteMapping("/{id}")
		public ResponseEntity<?> removeUser(@PathVariable Long id){
			try {
				Address foundAddress = this.addressService.findAddressById(id);
				if(ObjectUtils.isEmpty(foundAddress)) {
					return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_PARAM.getMessage());
				}
				this.addressService.removeAddress(id);
				return super.success(new AddressDTOResponse(foundAddress));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
		}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody(required = true) Map<String,Object> newAddress){
		try {
			if(ObjectUtils.isEmpty(newAddress)) {
				return super.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
			}
			Address address = this.addressService.findAddressById(id);
			if(ObjectUtils.isEmpty(address)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			
			Address upAddress = this.addressService.updateAddress(newAddress, id);
			return super.success(new AddressDTOResponse(upAddress));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllAddress(){
		try {
			List<Address> addresses = this.addressService.getAllAddress();
			List<AddressDTOResponse> response = addresses.stream().map(address -> new AddressDTOResponse(address)).collect(Collectors.toList());
			return super.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		  return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
}
