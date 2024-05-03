package com.org.userdetails.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.userdetails.dto.CountryDto;
import com.org.userdetails.model.Country;
import com.org.userdetails.model.Region;
import com.org.userdetails.model.Status;
import com.org.userdetails.repository.CountryRepository;
import com.org.userdetails.repository.RegionRepository;
import com.org.userdetails.service.CountryService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CountryServiceImpl implements CountryService{
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<Map<String, String>> createCountry(CountryDto countryDto) {
		Map<String, String> response = new HashMap<>();

		try {
			Region region = regionRepository.findById(countryDto.getRegionId()).orElseThrow(
					() -> new EntityNotFoundException("Region not found with id: " + countryDto.getRegionId()));

			Country country = mapToEntity(countryDto, region);

			country = countryRepository.save(country);

			response.put("status", "True");
			response.put("message", "Country created successfully!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			response.put("status", "False");
			response.put("message", "Country already exists!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<List<CountryDto>> getAllCountries() {
	    List<Country> countries = countryRepository.findAll();
	    List<CountryDto> countryDtos = countries.stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	    return new ResponseEntity<>(countryDtos, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<CountryDto>> getAllCountriesByRegionId(Long regionId) {
	    Region region = regionRepository.findById(regionId)
	            .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + regionId));
	    List<Country> countries = countryRepository.findByRegion(region);
	    List<CountryDto> countryDtos = countries.stream()
	            .map(this::mapToDto)
	            .collect(Collectors.toList());
	    return new ResponseEntity<>(countryDtos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<CountryDto> getByCountryName(String countryName) {
	    Country country = countryRepository.findByCountryName(countryName);
	    if (country == null) {
	        throw new EntityNotFoundException("Country not found with name: " + countryName);
	    }
	    CountryDto countryDto = mapToDto(country);
	    return new ResponseEntity<>(countryDto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Map<String, String>> updateCountry(Long countryId, CountryDto countryDto) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        Country country = countryRepository.findById(countryId)
	                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));

	        Region region = regionRepository.findById(countryDto.getRegionId())
	                .orElseThrow(() -> new EntityNotFoundException("Region not found with id: " + countryDto.getRegionId()));

	        country.setCountryName(countryDto.getCountryName());
	        country.setCountrycode(countryDto.getCountrycode());
	        country.setPhoneCode(countryDto.getPhoneCode());
	        country.setCurrency(countryDto.getCurrency());
	        country.setCurrencySymbol(countryDto.getCurrencySymbol());
	        country.setNativeName(countryDto.getNativeName());
	        country.setRegion(region);

	        country = countryRepository.save(country);

	        response.put("status", "True");
	        response.put("message", "Country updated successfully!");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (DataIntegrityViolationException e) {
	        response.put("status", "False");
	        response.put("message", "Country already exists!");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}

	@Override
	public ResponseEntity<Map<String, String>> updateCountryStatus(Long countryId, Status countryStatus) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        Country country = countryRepository.findById(countryId)
	                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));

	        country.setCountryStatus(countryStatus);

	        countryRepository.save(country);

	        response.put("status", "True");
	        response.put("message", "Country status updated successfully!");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (DataIntegrityViolationException e) {
	        response.put("status", "False");
	        response.put("message", "Failed to update country status!");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}


	
	private Country mapToEntity(CountryDto countryDto, Region region) {
	    Country country = modelMapper.map(countryDto, Country.class);
	    country.setRegion(region);
	    return country;
	}

	private CountryDto mapToDto(Country country) {
	    CountryDto countryDto = modelMapper.map(country, CountryDto.class);
	    countryDto.setRegionId(country.getRegion().getId());
	    return countryDto;
	}

}
