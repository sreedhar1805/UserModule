package com.org.userdetails.dto;

import com.org.userdetails.model.Status;

public class StateDto {

	private Long id;

	private String stateName;

	private String stateCode;

	private Status stateStatus;

	private Long countryId;

	public StateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StateDto(Long id, String stateName, String stateCode, Status stateStatus, Long countryId) {
		super();
		this.id = id;
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.stateStatus = stateStatus;
		this.countryId = countryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Status getStateStatus() {
		return stateStatus;
	}

	public void setStateStatus(Status stateStatus) {
		this.stateStatus = stateStatus;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	

}
