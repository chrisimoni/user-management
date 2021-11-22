package com.chrisimoni.usermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	private Long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	
}
