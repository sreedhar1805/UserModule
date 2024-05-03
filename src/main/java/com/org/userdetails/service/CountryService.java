package com.org.userdetails.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.org.userdetails.dto.CountryDto;
import com.org.userdetails.model.Status;

public interface CountryService {
	
	ResponseEntity<Map<String, String>> createCountry(CountryDto countryDto);
	
	ResponseEntity<List<CountryDto>> getAllCountries();
	
	ResponseEntity<List<CountryDto>> getAllCountriesByRegionId(Long regionId);
	
	ResponseEntity<CountryDto> getByCountryName(String countryName);
	
	ResponseEntity<Map<String, String>> updateCountry(Long countryId, CountryDto countryDto);
	
	ResponseEntity<Map<String, String>> updateCountryStatus(Long countryId, Status countryStatus);

}