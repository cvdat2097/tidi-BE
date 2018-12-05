package tuan.tidi.DTO.admin;

import java.text.SimpleDateFormat;

import tuan.tidi.entity.Accounts;

public class AccountsDTO {
	private int id;
	private String avatar;
	private String fullName;
	private String dateOfBirth;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private String active;
	private String username;
	private String permission;

	public AccountsDTO() {
		
	}
	
	public AccountsDTO(Accounts accounts) {
		this.avatar = accounts.getAvatar();
		this.fullName = accounts.getFullName();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			this.dateOfBirth = format.format(accounts.getDateOfBirth());
		}catch(Exception e) {
			this.dateOfBirth = null;
		}
		this.gender = accounts.getGender();
		this.phone = accounts.getPhone();
		this.email = accounts.getEmail();
		this.address = accounts.getAddress();
		this.active = accounts.getActive();
		this.id = accounts.getId();
		this.username = accounts.getUsername();
		this.permission = accounts.getPermission();
	}
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return fullName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
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
	
	
}
