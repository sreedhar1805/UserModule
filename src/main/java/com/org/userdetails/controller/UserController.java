package com.org.userdetails.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.org.userdetails.dto.UserDto;
import com.org.userdetails.model.ResetPasswordRequest;
import com.org.userdetails.model.User;
import com.org.userdetails.model.UserStatus;
import com.org.userdetails.repository.UserRepository;
import com.org.userdetails.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserRepository  userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/getAllUserByRole/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUserByRole(@PathVariable(value = "roleId") long roleId) {
		 logger.info("\u001B[32mFetching all users by role with roleId: {}\u001B[0m", roleId);
	    ResponseEntity<List<User>> responseEntity = userService.getAllUserByRole(roleId);

	    return responseEntity;
	}
	
	@GetMapping("/getUserById/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable(value = "userId") long userId) {
		 logger.info("\u001B[32mFetching user by ID: {}\u001B[0m", userId);
	    ResponseEntity<User> responseEntity = userService.getUserById(userId);

	    return responseEntity;
	}
	
	@GetMapping("/getUserByUserName/{userName}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserByUserName(@PathVariable(value = "userName") String userName) {
		logger.info("\u001B[32mFetching user by username: {}\u001B[0m", userName);
	    ResponseEntity<User> responseEntity = userService.getUserByUserName(userName);

	    return responseEntity;
	}
	
	@GetMapping("/getUserByEmail/{email}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserByEmail(@PathVariable(value = "email") String email) {
		logger.info("\u001B[32mFetching user by email: {}\u001B[0m", email);
		ResponseEntity<User> responseEntity = userService.getUserByEmail(email);

	    return responseEntity;
	}
	
	@GetMapping("/getAllUserByUserStatus/{userStatus}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUserByUserStatus(@PathVariable(value = "userStatus") UserStatus userStatus) {
		logger.info("\u001B[32mFetching all users by status: {}\u001B[0m", userStatus);
		ResponseEntity<List<User>> responseEntity = userService.getUsersByStatus(userStatus);

	    return responseEntity;
	}
	
	@PutMapping("/updateUser/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Map<String, String>> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto signupRequestDto) {
		logger.info("\u001B[32mUpdating user with ID: {}\u001B[0m", id);
		ResponseEntity<Map<String, String>> response = userService.updateUser(id, signupRequestDto);

	    return response;
	}

	@PostMapping("/reset-password")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
		logger.info("\u001B[32mResetting password for user with email: {}\u001B[0m", resetPasswordRequest.getEmail());
		return userService.resetPassword(resetPasswordRequest);
	}
	
	@PutMapping("/users/updateUserStatusByEmail")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateUserStatusByEmail(@RequestParam("email") String email, @RequestParam("newStatus") UserStatus newStatus) {
		 logger.info("\u001B[32mUserStatus changed to {} with email: {}\u001B[0m", newStatus, email);
	    return userService.updateUserStatusByEmail(email, newStatus);
	}
	
	@GetMapping("/getAllUsersByRoleAndCity/{cityId}/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsersByRoleAndCity(@PathVariable(value="cityId") long cityId, @PathVariable(value="roleId") long roleId) {
	    ResponseEntity<List<User>> responseEntity = userService.getAllUsersByRoleAndCity(cityId, roleId);

	    return responseEntity;
	}
	
	@GetMapping("/getAllUsersByCity/{cityId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsersByCity(@PathVariable(value="cityId") long cityId) {
	    ResponseEntity<List<User>> responseEntity = userService.getAllUsersByCity(cityId);

	    return responseEntity;
	}
	
	@GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(usersPage);
    }

}
