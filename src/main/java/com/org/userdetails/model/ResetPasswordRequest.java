package com.org.userdetails.model;

public class ResetPasswordRequest {
	private String email;
	private String existingPassword;
	private String newPassword;
	private String confirmPassword;

	public ResetPasswordRequest() {
	}

	public ResetPasswordRequest(String email, String existingPassword, String newPassword, String confirmPassword) {
		this.email = email;
		this.existingPassword = existingPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExistingPassword() {
		return existingPassword;
	}

	public void setExistingPassword(String existingPassword) {
		this.existingPassword = existingPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
