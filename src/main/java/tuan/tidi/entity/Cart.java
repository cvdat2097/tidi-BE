package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	private int id;
	
	@Column(name = "accountsId", nullable = false)
	private int accountsId;
	
	@Column(name = "productId", nullable = false)
	private int productId;
	
	@Column(name = "amount", nullable = false)
	private int amount;
	
	public Cart() {
		
	}
	
	public Cart(int accountsId, int productId, int amount) {
		this.accountsId = accountsId;
		this.productId = productId;
		this.amount = amount;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setAccountsId(int accountsId) {
		this.accountsId = accountsId;
	}
	
	public int getAccountsId() {
		return accountsId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
