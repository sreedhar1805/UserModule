package com.org.userdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.userdetails.model.Country;
import com.org.userdetails.model.State;

public interface StateRepository extends JpaRepository<State, Long>{

	List<State> findByCountry(Country country);

	State findByStateName(String stateName);

}
