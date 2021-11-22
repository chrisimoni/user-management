package com.chrisimoni.usermanagement.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
}
