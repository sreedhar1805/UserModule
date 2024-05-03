package com.org.userdetails.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "regions")
public class Region extends AuditorEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "region_name", nullable = false, unique = true)
    private String name;
    
	@Column(name = "region_status")
	@Enumerated(EnumType.STRING)
    private Status regionStatus;
	
	

	public Region() {
		super();
	}

	public Region(Long id, String name, Status regionStatus) {
		super();
		this.id = id;
		this.name = name;
		this.regionStatus = regionStatus;
	}

	public Status getRegionStatus() {
		return regionStatus;
	}

	public void setRegionStatus(Status regionStatus) {
		this.regionStatus = regionStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
