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
@Table(name="country")
public class Country extends AuditorEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "country_name", nullable = false, unique = true)
	private String countryName;

	@Column(name = "country_code", nullable = false, unique = true)
    private String countrycode;
    
	@Column(name = "phone_code", nullable = false, unique = true)
    private String phoneCode;

	@Column(name = "currency")
    private String currency;

	@Column(name = "currency_symbol")
    private String currencySymbol;

	@Column(name = "native_name")
    private String nativeName;
    
	@Column(name = "country_status")
	@Enumerated(EnumType.STRING)
    private Status countryStatus;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="region_id")
	private Region region;

	public Country() {
		super();
	}

	public Country(Long id, String countryName, String countrycode, String phoneCode, String currency,
			String currencySymbol, String nativeName, Status countryStatus, Region region) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.countrycode = countrycode;
		this.phoneCode = phoneCode;
		this.currency = currency;
		this.currencySymbol = currencySymbol;
		this.nativeName = nativeName;
		this.countryStatus = countryStatus;
		this.region = region;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	
	

}
