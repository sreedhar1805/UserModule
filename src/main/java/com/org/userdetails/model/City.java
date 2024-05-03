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
@Table(name="city")
public class City extends AuditorEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "city_name", nullable = false, unique = true)
	private String cityName;
	
	@Column(name = "city_code", nullable = false, unique = true)
	private String cityCode;

	@Column(name = "city_status")
	@Enumerated(EnumType.STRING)
	private Status cityStatus;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "state_id", nullable = false)
	private State state;

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	public City(Long id, String cityName, String cityCode, Status cityStatus, State state) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.cityCode = cityCode;
		this.cityStatus = cityStatus;
		this.state = state;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	

}
