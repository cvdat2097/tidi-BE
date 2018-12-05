package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "verification")
public class Verification {

	@Id
	private int id;
	
	@Column(name = "accountsId", nullable = false)
	private int accountsId;
	
	@Column(name = "vcode", length = 20, nullable = false)
	private String vcode;
	
	@Column(name = "veriType", nullable = false)
	private String veriType;
	
	@Column(name = "active", nullable = false)
	private String active;

	public Verification() {
		
	}
	
	public Verification(int accountsId, String vcode, String veriType, String active) {
		this.accountsId = accountsId;
		this.vcode = vcode;
		this.veriType = veriType;
		this.active = active;
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

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getVeriType() {
		return veriType;
	}

	public void setVeriType(String veriType) {
		this.veriType = veriType;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
