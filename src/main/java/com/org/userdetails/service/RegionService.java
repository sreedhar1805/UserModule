package com.org.userdetails.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.org.userdetails.dto.RegionDto;
import com.org.userdetails.model.Region;
import com.org.userdetails.model.Status;

public interface RegionService {
	
	ResponseEntity<Map<String, String>> createRegion(RegionDto regionDto);
	
	ResponseEntity<List<Region>> getAllByRegions();
	
	ResponseEntity<Region> getByRegionName(String regionName);
	
	ResponseEntity<Map<String, String>> updateRegion(long regionId, RegionDto updatedRegionDto);
	
	ResponseEntity<Map<String, String>> updateRegionStatus(Long regionId, Status newStatus);

}
