package com.chrisimoni.usermanagement.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> createUser() {
		return ResponseEntity.status(HttpStatus.OK).body("create user");
//		Map.Entry<Boolean, Object> result = userService.createUser(userRequest);
//		return restResponse.reponse(result);
	}
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body("All users");
	}
	
	@GetMapping("/users/{userid}")
	public ResponseEntity<?> getUsers(@PathVariable("userid") String userId) {
		return ResponseEntity.status(HttpStatus.OK).body("user");
	}
	
	@PutMapping("/users/{userid}")
	public ResponseEntity<?> updateUser(@PathVariable("userid") String userId, @Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.OK).body("update user");
	}
	
	@DeleteMapping("/users/{userid}")
	public ResponseEntity<?> deleteUser(@PathVariable("userid") String userId) {
		return ResponseEntity.status(HttpStatus.OK).body("delete user");
	}
	
	@PostMapping("/users/save")
	public String hello() {
		return "hello";
	}
}
