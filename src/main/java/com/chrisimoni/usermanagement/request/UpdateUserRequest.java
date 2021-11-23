package com.chrisimoni.usermanagement.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
	@NotEmpty(message = "first name cannot be null or empty")
	private String firstName;
	@NotEmpty(message = "last name cannot be null")
	private String lastName;
}
