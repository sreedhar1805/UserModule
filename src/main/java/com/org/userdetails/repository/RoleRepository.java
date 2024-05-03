package com.org.userdetails.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.userdetails.model.Erole;
import com.org.userdetails.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(Erole name);

	Role findById(long id);

	Optional<Role> findByName(String name);

	boolean existsByName(Erole role);

}
