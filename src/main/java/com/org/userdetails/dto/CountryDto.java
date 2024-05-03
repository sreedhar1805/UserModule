package com.org.userdetails.dto;

import com.org.userdetails.model.Status;

public class CountryDto {

	private Long id;

	private String countryName;

	private String countrycode;

	private String phoneCode;

	private String currency;

	private String currencySymbol;

	private String nativeName;

	private Status countryStatus;

	private Long regionId;

	public CountryDto() {
		super();
	}

	public CountryDto(Long id, String countryName, String countrycode, String phoneCode, String currency,
			String currencySymbol, String nativeName, Status countryStatus, Long regionId) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.countrycode = countrycode;
		this.phoneCode = phoneCode;
		this.currency = currency;
		this.currencySymbol = currencySymbol;
		this.nativeName = nativeName;
		this.countryStatus = countryStatus;
		this.regionId = regionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public Status getCountryStatus() {
		return countryStatus;
	}

	public void setCountryStatus(Status countryStatus) {
		this.countryStatus = countryStatus;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	
	

}
