package com.org.userdetails.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.org.userdetails.security.config.SignedUserHelper;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditorEntity {

	@CreatedDate
	@Column(name = "CreatedOn")
	private LocalDateTime createdOn;

	@CreatedBy
	@Column(name = "CreatedBy", length = 50)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "UpdatedOn")
	private LocalDateTime updatedOn;

	@LastModifiedBy
	@Column(name = "UpdatedBy", length = 50)
	private String updatedBy;

	@Column(name = "DeletedOn")
	private LocalDateTime deletedOn;

	@Column(name = "DeletedBy", length = 50)
	private String deletedBy;

	@Column(name = "isDeleted", length = 50)
	private Boolean isDeleted = false;

	@Column(name = "status", columnDefinition = "boolean default true")
	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(LocalDateTime deletedOn) {
		this.deletedOn = deletedOn;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@PreUpdate
	@PrePersist
	public void beforeAnyUpdate() {
		if (isDeleted != null && isDeleted) {

			if (deletedBy == null) {
				deletedBy = SignedUserHelper.userId().toString();
			}

			if (getDeletedOn() == null) {
				deletedOn = LocalDateTime.now();
			}
		}
	}

}