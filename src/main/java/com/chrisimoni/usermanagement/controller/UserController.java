package com.chrisimoni.usermanagement.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chrisimoni.usermanagement.request.UpdateUserPasswordRequest;
import com.chrisimoni.usermanagement.request.UpdateUserRequest;
import com.chrisimoni.usermanagement.request.UserRequest;
import com.chrisimoni.usermanagement.response.RestResponse;
import com.chrisimoni.usermanagement.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	RestResponse restResponse;

	@PostMapping("/users/create_user")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
		Map.Entry<Boolean, Object> result = userService.createUser(userRequest);
		return restResponse.reponse(result);
	}

	@GetMapping("/users/get_all_users")
	public ResponseEntity<?> getAllUsers() {
		Map.Entry<Boolean, Object> result = userService.getAllUsers();
		return restResponse.reponse(result);
	}

	@GetMapping("/users/get_user/{userid}")
	public ResponseEntity<?> getUsers(@PathVariable("userid") String userId) {
		Map.Entry<Boolean, Object> result = userService.getUser(userId);
		return restResponse.reponse(result);
	}

	@PutMapping("/users/update_user/{userid}")
	public ResponseEntity<?> updateUser(@PathVariable("userid") String userId,
			@Valid @RequestBody UpdateUserRequest userRequest) {
		Map.Entry<Boolean, Object> result = userService.updateUser(userId, userRequest);
		return restResponse.reponse(result);
	}

	@DeleteMapping("/users/delete_user/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable("userid") String userId) {
		Map.Entry<Boolean, Object> result = userService.deleteUser(userId);
		return restResponse.reponse(result);
	}
	
	@PutMapping("/users/update_password/{userid}")
	public ResponseEntity<?> updatePassword(@PathVariable("userid") String userId,
			@Valid @RequestBody UpdateUserPasswordRequest userRequest) {
		Map.Entry<Boolean, Object> result = userService.updatePassword(userId, userRequest);
		return restResponse.reponse(result);
	}

}
