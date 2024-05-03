package com.org.userdetails.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.userdetails.model.City;
import com.org.userdetails.model.Role;
import com.org.userdetails.model.User;
import com.org.userdetails.model.UserStatus;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	List<User> findAllByRoles(Role role);

	List<User> findByUserStatus(UserStatus userStatus);

	Optional<User> findByEmail(String email);

	List<User> findAllByCityAndRoles(Optional<City> city, Role role);

	List<User> findAllByCity(Optional<City> city);

}
