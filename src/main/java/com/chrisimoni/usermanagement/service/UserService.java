package com.chrisimoni.usermanagement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chrisimoni.usermanagement.entity.Role;
import com.chrisimoni.usermanagement.entity.User;
import com.chrisimoni.usermanagement.enums.Roles;
import com.chrisimoni.usermanagement.repository.RoleRepository;
import com.chrisimoni.usermanagement.repository.UserRepository;
import com.chrisimoni.usermanagement.request.UpdateUserPasswordRequest;
import com.chrisimoni.usermanagement.request.UpdateUserRequest;
import com.chrisimoni.usermanagement.request.UserRequest;
import com.chrisimoni.usermanagement.response.UserResponse;
import com.chrisimoni.usermanagement.util.Utils;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	Utils utils;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Map.Entry<Boolean, Object> createUser(UserRequest userRequest) {
		if(userRepository.findByEmail(userRequest.getEmail()) != null) {
			return Maps.immutableEntry(false, "Email already exists");
		}
		
		if(!userRequest.getRole().equalsIgnoreCase("user") && !userRequest.getRole().equalsIgnoreCase("admin")) {
			return Maps.immutableEntry(false, "Role must be either user or admin");
		}
		
		User user = modelMapper.map(userRequest, User.class);
		
		String generatedUserId = utils.generateUserId(30);
		user.setUserId(generatedUserId);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		String roleName = Roles.valueOf(userRequest.getRole().trim().toUpperCase()).toString();
		Role role = roleRepository.findByName(roleName);
		addRoleToUser(user, role);
		
		User savedUser = userRepository.save(user);
		
		UserResponse savedUserResponse = modelMapper.map(savedUser, UserResponse.class);
		
		return Maps.immutableEntry(true, savedUserResponse);
	}
	
	public Entry<Boolean, Object> getAllUsers() {
		List<User> users = userRepository.findAll();
		
		List<UserResponse> listOfUsers = new ArrayList<>();
		
		users.stream().forEach(user -> {
			UserResponse userReponse = modelMapper.map(user, UserResponse.class);
			listOfUsers.add(userReponse);
		});
		
		return Maps.immutableEntry(true, listOfUsers);
	}
	
	public Role createRoles(List<Role> roles) {
		List<Role> savedRoles = roleRepository.saveAll(roles);
		return savedRoles.get(0);
	}
	
	public void addRoleToUser(User user, Role role) {
		user.getRoles().add(role);
	}

	public User createUserFromCommand(User user) {
		return userRepository.save(user);
	}
	
	public Long getNumberOfRoles() {
		return roleRepository.count();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user == null) {
			log.error("user not found in the database");
			throw new UsernameNotFoundException("user not found in the database");
		}
		
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	public Map.Entry<Boolean, Object> getUser(String userId) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			return Maps.immutableEntry(false, "No record found");
		}
		
		UserResponse userResponse = modelMapper.map(user, UserResponse.class);
		
		return Maps.immutableEntry(true, userResponse);
	}

	public Map.Entry<Boolean, Object> updateUser(String userId, UpdateUserRequest userRequest) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			return Maps.immutableEntry(false, "No record found");
		}
		
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		
		User savedUser = userRepository.save(user);
		UserResponse savedUserResponse = modelMapper.map(savedUser, UserResponse.class);
		
		return Maps.immutableEntry(true, savedUserResponse);
	}

	public Map.Entry<Boolean, Object> deleteUser(String userId) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			return Maps.immutableEntry(false, "No record found");
		}
		
		userRepository.delete(user);

		return Maps.immutableEntry(true, "successful");
	}

	public Map.Entry<Boolean, Object> updatePassword(String userId, UpdateUserPasswordRequest userRequest) {
		User user = userRepository.findByUserId(userId);
		if(user == null) {
			return Maps.immutableEntry(false, "No record found");
		}
		
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		return Maps.immutableEntry(true, "successful");
	}

}
