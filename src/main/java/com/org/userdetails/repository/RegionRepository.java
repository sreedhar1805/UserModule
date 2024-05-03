package com.org.userdetails.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.userdetails.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{

	Region findByName(String regionName);

}
