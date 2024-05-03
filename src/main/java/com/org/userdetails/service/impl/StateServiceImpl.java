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

import com.org.userdetails.dto.StateDto;
import com.org.userdetails.model.Country;
import com.org.userdetails.model.State;
import com.org.userdetails.model.Status;
import com.org.userdetails.repository.CountryRepository;
import com.org.userdetails.repository.StateRepository;
import com.org.userdetails.service.StateService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StateServiceImpl implements StateService{
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<Map<String, String>> createState(StateDto stateDto) {
	    Map<String, String> response = new HashMap<>();

	    try {
	        Long countryId = stateDto.getCountryId();
	        if (countryId == null) {
	            response.put("status", "False");
	            response.put("message", "Country ID cannot be null!");
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	        }

	        Country country = countryRepository.findById(countryId).orElseThrow(
	                () -> new EntityNotFoundException("Country not found with id: " + countryId));

	        State state = mapToEntity(stateDto, country);

	        state = stateRepository.save(state);

	        response.put("status", "True");
	        response.put("message", "State created successfully!");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (DataIntegrityViolationException e) {
	        response.put("status", "False");
	        response.put("message", "State already exists!");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	}
	
	@Override
	public ResponseEntity<List<StateDto>> getAllStates() {
        List<State> states = stateRepository.findAll();
        List<StateDto> stateDtos = states.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stateDtos, HttpStatus.OK);
    }

	@Override
    public ResponseEntity<List<StateDto>> getAllStatesByCountryId(Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(
                () -> new EntityNotFoundException("Country not found with id: " + countryId));
        List<State> states = stateRepository.findByCountry(country);
        List<StateDto> stateDtos = states.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stateDtos, HttpStatus.OK);
    }

	@Override
    public ResponseEntity<StateDto> getByStateName(String stateName) {
        State state = stateRepository.findByStateName(stateName);
        if (state == null) {
            throw new EntityNotFoundException("State not found with name: " + stateName);
        }
        StateDto stateDto = mapToDto(state);
        return new ResponseEntity<>(stateDto, HttpStatus.OK);
    }

	@Override
    public ResponseEntity<Map<String, String>> updateState(Long stateId, StateDto stateDto) {
        Map<String, String> response = new HashMap<>();
        try {
            State state = stateRepository.findById(stateId)
                    .orElseThrow(() -> new EntityNotFoundException("State not found with id: " + stateId));

            Country country = countryRepository.findById(stateDto.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + stateDto.getCountryId()));

            state.setStateName(stateDto.getStateName());
            state.setStateCode(stateDto.getStateCode());
            state.setCountry(country);

            state = stateRepository.save(state);

            response.put("status", "True");
            response.put("message", "State updated successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("status", "False");
            response.put("message", "State already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

	@Override
    public ResponseEntity<Map<String, String>> updateStateStatus(Long stateId, Status stateStatus) {
        Map<String, String> response = new HashMap<>();
        try {
            State state = stateRepository.findById(stateId)
                    .orElseThrow(() -> new EntityNotFoundException("State not found with id: " + stateId));

            state.setStateStatus(stateStatus);

            stateRepository.save(state);

            response.put("status", "True");
            response.put("message", "State status updated successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("status", "False");
            response.put("message", "Failed to update state status!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
	
	private State mapToEntity(StateDto stateDto, Country country) {
	    State state = modelMapper.map(stateDto, State.class);
	    state.setCountry(country);
	    return state;
	}

	private StateDto mapToDto(State state) {
	    StateDto stateDto = modelMapper.map(state, StateDto.class);
	    stateDto.setCountryId(state.getCountry().getId());
	    return stateDto;
	}

}
