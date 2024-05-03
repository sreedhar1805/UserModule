package com.org.userdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.userdetails.model.City;
import com.org.userdetails.model.State;

public interface CityRepository extends JpaRepository<City, Long>{

	List<City> findByState(State state);

	City findByCityName(String cityName);

}
