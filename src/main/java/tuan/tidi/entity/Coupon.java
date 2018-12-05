package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupon")
public class Coupon {
	
	@Id
	private int id;
	
	@Column(name = "couponCode", length = 10, nullable = false)
	private String couponCode;
	
	@Column(name = "campaignId")
	private int campaignId;
	
	@Column(name = "money")
	private int money;
	
	@Column(name = "percent")
	private float percent;
	
	@Column(name = "threshold")
	private int threshold;
	
	@Column(name = "allProduct")
	private String allProduct;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "active")
	private String active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public String getAllProduct() {
		return allProduct;
	}

	public void setAllProduct(String allProduct) {
		this.allProduct = allProduct;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


}
