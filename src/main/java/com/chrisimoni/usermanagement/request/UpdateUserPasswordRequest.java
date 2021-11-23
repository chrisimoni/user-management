package com.chrisimoni.usermanagement.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserPasswordRequest {
	@NotEmpty(message = "password cannot be null or empty")
	private String password;
}
