package com.chrisimoni.usermanagement.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chrisimoni.usermanagement.entity.Role;
import com.chrisimoni.usermanagement.entity.User;
import com.chrisimoni.usermanagement.enums.Roles;
import com.chrisimoni.usermanagement.service.UserService;
import com.chrisimoni.usermanagement.util.Utils;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, Utils utils, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userService.getNumberOfRoles() < 1) {
				List<Role> roles = Arrays.asList(new Role(null, Roles.ADMIN.toString()),
						new Role(null, Roles.USER.toString()));
				Role role = userService.createRoles(roles);
				User user = new User(null, utils.generateUserId(30), "John", "Doe", "john.doe@example.com",
						passwordEncoder.encode("password123"), new ArrayList<>());

				userService.addRoleToUser(user, role);
				userService.createUserFromCommand(user);
			}

		};

	}
}
