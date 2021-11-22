package com.chrisimoni.usermanagement.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chrisimoni.usermanagement.entity.Role;
import com.chrisimoni.usermanagement.entity.User;
import com.chrisimoni.usermanagement.enums.Roles;
import com.chrisimoni.usermanagement.repository.RoleRepository;
import com.chrisimoni.usermanagement.repository.UserRepository;
import com.chrisimoni.usermanagement.request.UserRequest;
import com.chrisimoni.usermanagement.response.UserResponse;
import com.chrisimoni.usermanagement.util.Utils;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	Utils utils;
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Map.Entry<Boolean, Object> createUser(UserRequest userRequest) {
		if(userRepository.findByEmail(userRequest.getEmail()) != null) {
			return Maps.immutableEntry(false, "Record already exists");
		}
		
		if(!userRequest.getRole().equalsIgnoreCase("user") && !userRequest.getRole().equalsIgnoreCase("admin")) {
			return Maps.immutableEntry(false, "Role must be either user or admin");
		}
		
		User user = modelMapper.map(userRequest, User.class);
		
		String generatedUserId = utils.generateUserId(30);
		user.setUserId(generatedUserId);
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));		
		User savedUser = userRepository.save(user);
		
		if(roleRepository.count() < 1) {
			createRoles();
		}
		
		String roleName = Roles.valueOf(userRequest.getRole().trim().toUpperCase()).toString();
		Role role = roleRepository.findByName(roleName);
		addRoleToUser(savedUser, role);
		
		UserResponse savedUserResponse = modelMapper.map(savedUser, UserResponse.class);
		
		return Maps.immutableEntry(true, savedUserResponse);
	}
	
	public void createRoles() {
		List<Role> roles = Arrays.asList(new Role(null, Roles.ADMIN.toString()), new Role(null, Roles.USER.toString()));
		roleRepository.saveAll(roles);
	}
	
	public void addRoleToUser(User user, Role role) {
		user.getRoles().add(role);
	}

}
