package com.overgo.demo.rest;

import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.overgo.demo.DTO.UserDTORequest;
import com.overgo.demo.DTO.UserDTOResponse;
import com.overgo.demo.contains.ResponseCode;
import com.overgo.demo.model.User;
import com.overgo.demo.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseRestController {

	@Autowired
	private UserService userService;

	@PostMapping("")
	public ResponseEntity<?> addUser(@RequestBody(required = true) Map<String, Object> newUser) {
		try {
			if (ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
			}
			UserDTORequest request = new UserDTORequest(newUser);
			if (ObjectUtils.isEmpty(request.getEmail()) || ObjectUtils.isEmpty(request.getFullname())
					|| ObjectUtils.isEmpty(request.getPassword()) || ObjectUtils.isEmpty(request.getUsername())
					|| ObjectUtils.isEmpty(request.getPhone())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}

			User foundUser = this.userService.findByfullname(newUser.get("fullname").toString()).orElse(null);
			if (!ObjectUtils.isEmpty(foundUser)) {
				return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
						ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}

			User insertUser = userService.addUser(newUser);
			if (!ObjectUtils.isEmpty(insertUser)) {
				return super.success(new UserDTOResponse(insertUser));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id,
			@RequestBody(required = true) Map<String, Object> newUser) {
		try {
			if (ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
			}
			User user = this.userService.findUserById(id);
			if (ObjectUtils.isEmpty(user)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			User updateUser = this.userService.updateUser(newUser, id);
			return super.success(new UserDTOResponse(updateUser));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeUser(@PathVariable Long id){
		try {
			User foundUser = this.userService.findUserById(id);
			if(ObjectUtils.isEmpty(foundUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			this.userService.removeUser(id);
			return super.success(new UserDTOResponse(foundUser));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() {
		try {
			List<User> users = this.userService.getAllUser();
			List<UserDTOResponse> response = users.stream().map(user -> new UserDTOResponse(user))
					.collect(Collectors.toList());
			return super.success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

}
