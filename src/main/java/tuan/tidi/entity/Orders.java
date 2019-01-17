package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	
	@Id
	private int id;
	
	@Column(name = "accountsId", nullable = false)
	private int accountsId;
	
	@Column(name = "fullname", nullable = false)
	private String fullName;
	
	@Column(name = "total", nullable = false)
	private int total;
	
	@Column(name = "couponId", nullable = false)
	private int couponId;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "statuss", nullable = false)
	private String status;
	
	@Column(name = "note", nullable = true)
	private String note;

	@Column(name = "active", nullable = false)
	private String active;

	@Column(name = "zalopayToken")
	private String zalopayToken;
	
	@Column(name = "zptransid")
	private Long zptransid;
	
	@Column(name = "apptransid")
	private String apptransid;

	@Column(name = "jsonzptoken")
	private String jsonzptoken;
	
	public String getJsonzptoken() {
		return jsonzptoken;
	}

	public void setJsonzptoken(String jsonzptoken) {
		this.jsonzptoken = jsonzptoken;
	}

	public String getApptransid() {
		return apptransid;
	}

	public void setApptransid(String apptransid) {
		this.apptransid = apptransid;
	}

	public Long getZptransid() {
		return zptransid;
	}

	public void setZptransid(Long zptransid) {
		this.zptransid = zptransid;
	}

	public String getZalopayToken() {
		return zalopayToken;
	}

	public void setZalopayToken(String zalopayToken) {
		this.zalopayToken = zalopayToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountsId() {
		return accountsId;
	}

	public void setAccountsId(int accountsId) {
		this.accountsId = accountsId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
