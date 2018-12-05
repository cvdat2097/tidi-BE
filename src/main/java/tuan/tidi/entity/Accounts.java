package tuan.tidi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "accounts")
public class Accounts {
	
	public enum accessType {
        CUSTOMER, ADMIN
    }
	
	public enum genderType {
        MALE, FEMALE
    }
	
	@Id
	private int id;
	
	@Column(name = "username", length = 20, nullable = false)
	private String username;
	
	@Column(name = "passwords", length = 200, nullable = false)
	private String password;
	
	@Column(name = "permission", nullable = false)
	private String permission;
	
	@Column(name = "fullName", length = 30)
	private String fullName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dateOfBirth", nullable = true)
	private Date dateOfBirth;
	
	@Column(name = "gender", nullable = true)
	private String gender;
	
	@Column(name = "phone", length = 10, nullable = true)
	private String phone;
	
	@Column(name = "email", length = 50, nullable = true)
	private String email;
	
	@Column(name = "address", length = 300, nullable = true)
	private String address;

	@Column(name = "avatar", length = 200, nullable = true)
	private String avatar;

	@Column(name = "active", nullable = true)
	private String active;

	@Column(name = "isVerified", nullable = true)
	private String isVerified;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
}
