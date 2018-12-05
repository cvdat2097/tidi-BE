package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordersdetail")
public class OrdersDetail {
	
	@Id
	private int id;
	
	@Column(name = "ordersId", nullable = false)
	private int ordersId;
	
	@Column(name = "productId", nullable = false)
	private int productId;
	
	@Column(name = "amount", nullable = false)
	private int amount;
	
	@Column(name = "originalPrice", nullable = false)
	private int originalPrice;
	
	@Column(name = "finalPrice", nullable = false)
	private int finalPrice;
	
	@Column(name = "active", nullable = false)
	private String active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
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

	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	public int getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	

}
