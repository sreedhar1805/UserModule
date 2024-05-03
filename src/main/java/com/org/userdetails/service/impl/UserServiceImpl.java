package com.org.userdetails.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.userdetails.dto.UserDto;
import com.org.userdetails.model.City;
import com.org.userdetails.model.Erole;
import com.org.userdetails.model.ResetPasswordRequest;
import com.org.userdetails.model.Role;
import com.org.userdetails.model.User;
import com.org.userdetails.model.UserStatus;
import com.org.userdetails.repository.CityRepository;
import com.org.userdetails.repository.RoleRepository;
import com.org.userdetails.repository.UserRepository;
import com.org.userdetails.response.MessageResponse;
import com.org.userdetails.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CityRepository cityRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public void insertDefaultUser() {


		if (userRepository.existsByUsername("Venkat")) {
			logger.info("\u001B[33mDefault user already exists.\u001B[0m");
			System.out.println("Default user already exists.");
			return;
		}
		
		
		Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN).orElseGet(() -> {
			Role newAdminRole = new Role();
			newAdminRole.setName(Erole.ROLE_ADMIN);
			return roleRepository.save(newAdminRole);
		});

		User defaultUser = new User();
		defaultUser.setUsername("Venkat");
		defaultUser.setEmail("venkatid9009@gmail.com");
		defaultUser.setPassword(passwordEncoder.encode("Venkat@123"));
		defaultUser.setRoles(Set.of(adminRole));
		defaultUser.setUserStatus(UserStatus.ACTIVE);
		defaultUser.setPhoneNumber("8908907890");

		userRepository.save(defaultUser);
		logger.info("\u001B[32mDefault user registered successfully!\u001B[0m");
		System.out.println("Default user inserted successfully.");
	}

	@Override
	public ResponseEntity<List<User>> getAllUserByRole(long roleId) {
		List<User> userList = new ArrayList<User>();
		Role role = roleRepository.findById(roleId);
		userList = userRepository.findAllByRoles(role);
		if (userList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, String>> updateUser(Long id, @Valid UserDto signUpRequest) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {

			if (signUpRequest.getPhoneNumber() != null)
				user.setPhoneNumber(signUpRequest.getPhoneNumber());
			
			if (signUpRequest.getCityId() != 0) {
	            Long cityId = signUpRequest.getCityId();
	            City city = cityRepository.findById(cityId).orElse(null);
	            if (city != null) {
	                user.setCity(city); 
	            } else {
	                // Handle case where city with given ID doesn't exist
	                Map<String, String> response = new HashMap<>();
	                response.put("status", "false");
	                response.put("message", "City with ID " + cityId + " not found!");
	                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	            }
	        }
		    
			userRepository.save(user);

			Map<String, String> response = new HashMap<String, String>();
			response.put("status", "true");
			response.put("message", "Update Succc!");
			return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		}

		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "false");
		response.put("message", "Try again!");
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
	}





	@Override
	public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
		// Validate if newPassword matches confirmPassword
		if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Passwords do not match!"));
		}

		Optional<User> optionalUser = userRepository.findByEmail(resetPasswordRequest.getEmail());
		if (!optionalUser.isPresent()) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email address not found!"));
		}
		User user = optionalUser.get();

		if (!passwordEncoder.matches(resetPasswordRequest.getExistingPassword(), user.getPassword())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Existing password is incorrect!"));
		}

		user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Password reset successfully!"));
	}

	@Override
	public ResponseEntity<String> updateUserStatusByEmail(String email, UserStatus newStatus) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setUserStatus(newStatus);
			userRepository.save(user);
			return ResponseEntity.ok("User status updated successfully!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with the provided email");
		}
	}

	@Transactional
	@Override
	public ResponseEntity<User> getUserById(long userId) {
	    Optional<User> userOptional = userRepository.findById(userId);
	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        user.getCity().getCityStatus();
	        user.getCity().getCityName();
	        user.getCity().getCityCode();
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	@Override
	public ResponseEntity<User> getUserByUserName(String userName) {
		Optional<User> userOptional = userRepository.findByUsername(userName);
		if (!userOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User user = userOptional.get();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<User> getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (!userOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User user = userOptional.get();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> getUsersByStatus(UserStatus userStatus) {
		List<User> userList = userRepository.findByUserStatus(userStatus);
		if (userList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<User>> getAllUsersByRoleAndCity(long cityId, long roleId) {
		List<User> userList = new ArrayList<User>();
		Role role= roleRepository.findById(roleId);
      	Optional<City> city= cityRepository.findById(cityId);

		userList= userRepository.findAllByCityAndRoles(city,role);
		 if (userList.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<User>> getAllUsersByCity(long cityId) {
		List<User> userList = new ArrayList<User>();
      	Optional<City> city= cityRepository.findById(cityId);

		userList= userRepository.findAllByCity(city);
		 if (userList.isEmpty()) {
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		 return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
}
