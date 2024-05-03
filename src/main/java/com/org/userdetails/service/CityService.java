package com.org.userdetails.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.org.userdetails.dto.CityDto;
import com.org.userdetails.model.Status;

public interface CityService {
	
	ResponseEntity<List<CityDto>> getAllCities();
    
    ResponseEntity<List<CityDto>> getAllCitiesByStateId(Long stateId);
    
    ResponseEntity<CityDto> getByCityName(String cityName);
    
    ResponseEntity<Map<String, String>> createCity(CityDto cityDto);
    
    ResponseEntity<Map<String, String>> updateCity(Long cityId, CityDto cityDto);
    
    ResponseEntity<Map<String, String>> updateCityStatus(Long cityId, Status cityStatus);
    

}
