package tuan.tidi.DTO.admin;

import tuan.tidi.entity.Campaign;
import tuan.tidi.entity.Coupon;

public class CouponDTO {
	private int id;
	private String couponCode;
	private CampaignDTO campagin;
	private int money;
	private float percent;
	private int threshold;
	private String allProduct;
	private int amount;
	private String active;
	
	public CouponDTO() {
		
	}
	
	public CouponDTO(Coupon coupon) {
		this.id = coupon.getId();
		this.couponCode = coupon.getCouponCode();
		this.money = coupon.getMoney();
		this.percent = coupon.getPercent();
		this.threshold = coupon.getThreshold();
		this.allProduct = coupon.getAllProduct();
		this.amount = coupon.getAmount();
		this.active = coupon.getActive();
	}

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

	public CampaignDTO getCampagin() {
		return campagin;
	}

	public void setCampagin(CampaignDTO campagin) {
		this.campagin = campagin;
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
