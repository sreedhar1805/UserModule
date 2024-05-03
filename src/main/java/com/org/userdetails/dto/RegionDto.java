package com.org.userdetails.dto;

import com.org.userdetails.model.Status;

public class RegionDto {
	
	 private Long id;

	    private String name;
	    
	    private Status regionStatus;

		public RegionDto(Long id, String name, Status regionStatus) {
			super();
			this.id = id;
			this.name = name;
			this.regionStatus = regionStatus;
		}

		public RegionDto() {
			super();
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
