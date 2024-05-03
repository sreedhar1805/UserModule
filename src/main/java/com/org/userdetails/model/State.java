package com.org.userdetails.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="state")
public class State extends AuditorEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "state_name", nullable = false, unique = true)
	private String stateName;
	
	@Column(name = "state_code", nullable = false, unique = true)
	private String stateCode;

	@Column(name = "state_status")
	@Enumerated(EnumType.STRING)
	private Status stateStatus;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	public State(Long id, String stateName, String stateCode, Status stateStatus, Country country) {
		super();
		this.id = id;
		this.stateName = stateName;
		this.stateCode = stateCode;
		this.stateStatus = stateStatus;
		this.country = country;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	
	

}
