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

import com.org.userdetails.dto.StateDto;
import com.org.userdetails.model.Status;
import com.org.userdetails.service.StateService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/createState")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> createState(@RequestBody StateDto stateDto) {
		try {
			ResponseEntity<Map<String, String>> responseEntity = stateService.createState(stateDto);
			return responseEntity;
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().body(Map.of("error", "State already exists"));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(Map.of("error", "Region not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("error", "Failed to create State"));
		}
	}
    @GetMapping("/getAllStates")
    public ResponseEntity<List<StateDto>> getAllStates() {
        return stateService.getAllStates();
    }

    @GetMapping("/getAllStatesByCountryId/{countryId}")
    public ResponseEntity<List<StateDto>> getAllStatesByCountryId(@PathVariable Long countryId) {
        return stateService.getAllStatesByCountryId(countryId);
    }

    @GetMapping("/getByStateName/{stateName}")
    public ResponseEntity<StateDto> getByStateName(@PathVariable String stateName) {
        return stateService.getByStateName(stateName);
    }

    @PutMapping("/updateState/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateState(@PathVariable Long stateId, @RequestBody StateDto stateDto) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = stateService.updateState(stateId, stateDto);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "State already exists"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Country not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update State"));
        }
    }

    @PutMapping("/updateStateStatus/{stateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateStateStatus(@PathVariable Long stateId, @RequestParam Status stateStatus) {
        try {
            ResponseEntity<Map<String, String>> responseEntity = stateService.updateStateStatus(stateId, stateStatus);
            return responseEntity;
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to update state status"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to update state status"));
        }
    }
}
