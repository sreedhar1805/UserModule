package com.org.userdetails.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.org.userdetails.dto.RegionDto;
import com.org.userdetails.model.Region;
import com.org.userdetails.model.Status;
import com.org.userdetails.service.RegionService;

@RestController
@RequestMapping("/api/v1")
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	@PostMapping("/createRegion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> createLocation(@RequestBody RegionDto regionDto) {
	    try {
	        ResponseEntity<Map<String, String>> responseEntity = regionService.createRegion(regionDto);
	        return responseEntity;
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(Map.of("error", "Failed to create region"));
	    }
	}

	
	@GetMapping("/getAllRegions")
    public ResponseEntity<List<Region>> getAllRegions() {
        try {
            return regionService.getAllByRegions();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/getRegionByName/{regionName}")
    public ResponseEntity<Region> getRegionByName(@PathVariable String regionName) {
        try {
            return regionService.getByRegionName(regionName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/updateRegion/{regionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateRegion(@PathVariable Long regionId, @RequestBody RegionDto updatedRegionDto) {
        try {
            return regionService.updateRegion(regionId, updatedRegionDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update region"));
        }
    }

    @PutMapping("/updateRegionStatus/{regionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> updateRegionStatus(@PathVariable Long regionId, @RequestParam Status newStatus) {
        try {
            return regionService.updateRegionStatus(regionId, newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update region status"));
        }
    }
}
