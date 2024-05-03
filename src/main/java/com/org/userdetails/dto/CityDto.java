package com.org.userdetails.dto;

import com.org.userdetails.model.Status;

public class CityDto {

	private Long id;

	private String cityName;

	private String cityCode;

	private Status cityStatus;

	private Long stateId;

	public CityDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CityDto(Long id, String cityName, String cityCode, Status cityStatus, Long stateId) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.cityStatus = cityStatus;
		this.stateId = stateId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Status getCityStatus() {
		return cityStatus;
	}

	public void setCityStatus(Status cityStatus) {
		this.cityStatus = cityStatus;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	

}
