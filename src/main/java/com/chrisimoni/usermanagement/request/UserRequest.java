package com.chrisimoni.usermanagement.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
	@NotEmpty(message = "first name cannot be null or empty")
	private String firstName;
	@NotEmpty(message = "last name cannot be null")
	private String lastName;
	@NotEmpty(message = "email cannot be null")
	private String email;
	@NotEmpty(message = "password cannot be null")
	private String password;
	@NotEmpty
	private String role;
}
