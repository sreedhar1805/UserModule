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

import com.org.userdetails.dto.CityDto;
import com.org.userdetails.model.City;
import com.org.userdetails.model.State;
import com.org.userdetails.model.Status;
import com.org.userdetails.repository.CityRepository;
import com.org.userdetails.repository.StateRepository;
import com.org.userdetails.service.CityService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CityServiceImpl implements CityService{
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    
    @Override
    public ResponseEntity<Map<String, String>> createCity(CityDto cityDto) {
        Map<String, String> response = new HashMap<>();

        try {
            State state = stateRepository.findById(cityDto.getStateId()).orElseThrow(
                    () -> new EntityNotFoundException("State not found with id: " + cityDto.getStateId()));

            City city = mapToEntity(cityDto, state);

            city = cityRepository.save(city);

            response.put("status", "True");
            response.put("message", "City created successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("status", "False");
            response.put("message", "City already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDto> cityDtos = cities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cityDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CityDto>> getAllCitiesByStateId(Long stateId) {
        State state = stateRepository.findById(stateId).orElseThrow(
                () -> new EntityNotFoundException("State not found with id: " + stateId));
        List<City> cities = cityRepository.findByState(state);
        List<CityDto> cityDtos = cities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(cityDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CityDto> getByCityName(String cityName) {
        City city = cityRepository.findByCityName(cityName);
        if (city == null) {
            throw new EntityNotFoundException("City not found with name: " + cityName);
        }
        CityDto cityDto = mapToDto(city);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, String>> updateCity(Long cityId, CityDto cityDto) {
        Map<String, String> response = new HashMap<>();
        try {
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));

            State state = stateRepository.findById(cityDto.getStateId())
                    .orElseThrow(() -> new EntityNotFoundException("State not found with id: " + cityDto.getStateId()));

            city.setCityName(cityDto.getCityName());
            city.setCityCode(cityDto.getCityCode());
            city.setState(state);

            city = cityRepository.save(city);

            response.put("status", "True");
            response.put("message", "City updated successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("status", "False");
            response.put("message", "City already exists!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> updateCityStatus(Long cityId, Status cityStatus) {
        Map<String, String> response = new HashMap<>();
        try {
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + cityId));

            city.setCityStatus(cityStatus);

            cityRepository.save(city);

            response.put("status", "True");
            response.put("message", "City status updated successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response.put("status", "False");
            response.put("message", "Failed to update city status!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public City mapToEntity(CityDto cityDto, State state) {
        City city = modelMapper.map(cityDto, City.class);
        city.setState(state);
        return city;
    }

    public CityDto mapToDto(City city) {
        CityDto cityDto = modelMapper.map(city, CityDto.class);
        cityDto.setStateId(city.getState().getId());
        return cityDto;
    }

}
