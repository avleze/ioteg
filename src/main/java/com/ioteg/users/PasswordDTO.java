package com.ioteg.users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PasswordDTO {

	@NotNull
	@NotEmpty
	private String oldPassword;
	@NotNull
	@NotEmpty
	private String newPassword;

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
