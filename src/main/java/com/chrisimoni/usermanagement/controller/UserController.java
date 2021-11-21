package com.chrisimoni.usermanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.OK).body("All users");
	}
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body("All users");
	}
	
	@GetMapping("/users/{userid}")
	public ResponseEntity<?> getUsers() {
		return ResponseEntity.status(HttpStatus.OK).body("user");
	}
}
