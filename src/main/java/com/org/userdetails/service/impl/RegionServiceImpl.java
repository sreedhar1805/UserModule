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

import com.org.userdetails.dto.RegionDto;
import com.org.userdetails.model.Region;
import com.org.userdetails.model.Status;
import com.org.userdetails.repository.RegionRepository;
import com.org.userdetails.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<Map<String, String>> createRegion(RegionDto regionDto) {
		Region region = mapToEntity(regionDto);

		Map<String, String> response = new HashMap<>();

		try {
			region = regionRepository.save(region);
			response.put("status", "True");
			response.put("message", "region created successfully!");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			response.put("status", "False");
			response.put("message", "region already exists!");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}
	
	@Override
	public ResponseEntity<List<Region>> getAllByRegions() {
	    List<Region> regions = regionRepository.findAll();
	    return new ResponseEntity<>(regions, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Region> getByRegionName(String regionName) {
	    Region region = regionRepository.findByName(regionName);
	    if (region != null) {
	        return new ResponseEntity<>(region, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	@Override
	public ResponseEntity<Map<String, String>> updateRegion(long regionId, RegionDto updatedRegionDto) {
	    Region existingRegion = regionRepository.findById(regionId).orElse(null);
	    if (existingRegion != null) {
	        // Update existingRegion with data from updatedRegionDto
	        existingRegion.setName(updatedRegionDto.getName());
	        // Update other properties as needed
	        regionRepository.save(existingRegion);
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "True");
	        response.put("message", "Region updated successfully!");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "False");
	        response.put("message", "Region not found!");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}

	@Override
	public ResponseEntity<Map<String, String>> updateRegionStatus(Long regionId, Status newStatus) {
	    Region existingRegion = regionRepository.findById(regionId).orElse(null);
	    if (existingRegion != null) {
	        existingRegion.setRegionStatus(newStatus);
	        regionRepository.save(existingRegion);
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "True");
	        response.put("message", "Region status updated successfully!");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        Map<String, String> response = new HashMap<>();
	        response.put("status", "False");
	        response.put("message", "Region not found!");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	}
	

	private List<RegionDto> mapRegionListToDTOList(List<Region> regionList) {
		return regionList.stream().map(region -> modelMapper.map(region, RegionDto.class))
				.collect(Collectors.toList());
	}

	private Region mapToEntity(RegionDto regionDto) {
		return modelMapper.map(regionDto, Region.class);
	}
	

}
