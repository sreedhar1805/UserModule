package com.org.userdetails.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.org.userdetails.dto.UserDto;
import com.org.userdetails.model.ResetPasswordRequest;
import com.org.userdetails.model.User;
import com.org.userdetails.model.UserStatus;

import jakarta.validation.Valid;

public interface UserService {

	ResponseEntity<List<User>> getAllUserByRole(long roleId);

	ResponseEntity<Map<String, String>> updateUser(Long id, @Valid UserDto signUpRequest);

	ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);

	ResponseEntity<String> updateUserStatusByEmail(String email, UserStatus newStatus);

	ResponseEntity<User> getUserById(long userId);

	ResponseEntity<User> getUserByUserName(String userName);

	ResponseEntity<User> getUserByEmail(String email);

	ResponseEntity<List<User>> getUsersByStatus(UserStatus userStatus);

	void insertDefaultUser();
	
	ResponseEntity<List<User>> getAllUsersByRoleAndCity(long cityId, long roleId);
	
	ResponseEntity<List<User>> getAllUsersByCity(long cityId);

}
