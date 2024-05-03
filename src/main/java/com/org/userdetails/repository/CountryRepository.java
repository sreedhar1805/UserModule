package com.org.userdetails.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.userdetails.model.Country;
import com.org.userdetails.model.Region;

public interface CountryRepository extends JpaRepository<Country, Long>{

	List<Country> findByRegion(Region region);

	Country findByCountryName(String countryName);

}
