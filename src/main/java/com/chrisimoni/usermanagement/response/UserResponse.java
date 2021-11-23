package com.chrisimoni.usermanagement.response;

import java.util.Collection;

import com.chrisimoni.usermanagement.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private Collection<Role> roles;
}
