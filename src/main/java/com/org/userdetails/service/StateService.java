package com.org.userdetails.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.org.userdetails.dto.StateDto;
import com.org.userdetails.model.Status;

public interface StateService {

	ResponseEntity<Map<String, String>> createState(StateDto stateDto);
	
	ResponseEntity<List<StateDto>> getAllStates();
	
	ResponseEntity<List<StateDto>> getAllStatesByCountryId(Long countryId);
	
	ResponseEntity<StateDto> getByStateName(String stateName);
	
	ResponseEntity<Map<String, String>> updateState(Long stateId, StateDto stateDto);
	
	ResponseEntity<Map<String, String>> updateStateStatus(Long stateId, Status stateStatus);
	
}
