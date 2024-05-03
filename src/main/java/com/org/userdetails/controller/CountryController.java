package com.org.userdetails.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

import com.org.userdetails.dto.CountryDto;
import com.org.userdetails.model.Status;
import com.org.userdetails.service.CountryService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class CountryController {
	
	@Autowired
	private CountryService countryService;

	@PostMapping("/createCountry")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> createCountry(@RequestBody CountryDto countryDto) {
		try {
			ResponseEntity<Map<String, String>> responseEntity = countryService.createCountry(countryDto);
			return responseEntity;
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().body(Map.of("error", "Country already exists"));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", "Region not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("error", "Failed to create Country"));
		}
	}
	
	@GetMapping("/getAllCountries")
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/getAllCountriesByRegionId/{regionId}")
    public ResponseEntity<List<CountryDto>> getAllCountriesByRegionId(@PathVariable Long regionId) {
        return countryService.getAllCountriesByRegionId(regionId);
    }

    @GetMapping("/getByCountryName/{countryName}")
    public ResponseEntity<CountryDto> getByCountryName(@PathVariable String countryName) {
        return countryService.getByCountryName(countryName);
    }

    @PutMapping("/updateCountry/{countryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateCountry(@PathVariable Long countryId, @RequestBody CountryDto countryDto) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = countryService.updateCountry(countryId, countryDto);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Country already exists"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Region not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update Country"));
        }
    }

    @PutMapping("/updateCountryStatus/{countryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateCountryStatus(@PathVariable Long countryId, @RequestParam Status countryStatus) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = countryService.updateCountryStatus(countryId, countryStatus);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to update country status"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update country status"));
        }
    }
}
