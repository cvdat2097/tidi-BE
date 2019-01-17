package tuan.tidi.DTO.account;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.entity.Accounts;
import tuan.tidi.service.FormatDate;

public class AccountsDTO {
	
	private StatusDTO status;
	private String avatar;
	private String fullName;
	private String dateOfBirth;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private String permission;
	private String username;
	private String emailIsVerified;

	public AccountsDTO() {
		
	}
	
	public AccountsDTO(Accounts accounts) {
		this.avatar = accounts.getAvatar();
		this.fullName = accounts.getFullName();
		this.dateOfBirth = FormatDate.formatDate(accounts.getDateOfBirth());
		this.gender = accounts.getGender();
		this.phone = accounts.getPhone();
		this.email = accounts.getEmail();
		this.address = accounts.getAddress();
		this.permission = accounts.getPermission();
		this.username = accounts.getUsername();
		this.emailIsVerified = accounts.getIsVerified();
	}
	
	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmailIsVerified(String emailIsVerified) {
		this.emailIsVerified = emailIsVerified;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public String getEmailIsVerified() {
		return emailIsVerified;
	}
	
}
