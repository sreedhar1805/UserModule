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

import com.org.userdetails.dto.CityDto;
import com.org.userdetails.model.Status;
import com.org.userdetails.service.CityService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/createCity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> createCity(@RequestBody CityDto cityDto) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = cityService.createCity(cityDto);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "City already exists"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "State not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create City"));
        }
    }

    @GetMapping("/getAllCities")
    public ResponseEntity<List<CityDto>> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/getAllCitiesByStateId/{stateId}")
    public ResponseEntity<List<CityDto>> getAllCitiesByStateId(@PathVariable Long stateId) {
        return cityService.getAllCitiesByStateId(stateId);
    }

    @GetMapping("/getByCityName/{cityName}")
    public ResponseEntity<CityDto> getByCityName(@PathVariable String cityName) {
        return cityService.getByCityName(cityName);
    }

    @PutMapping("/updateCity/{cityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateCity(@PathVariable Long cityId, @RequestBody CityDto cityDto) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = cityService.updateCity(cityId, cityDto);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "City already exists"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "State not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update City"));
        }
    }

    @PutMapping("/updateCityStatus/{cityId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateCityStatus(@PathVariable Long cityId, @RequestParam Status cityStatus) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = cityService.updateCityStatus(cityId, cityStatus);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to update city status"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update city status"));
        }
    }
}
