package tuan.tidi.DTO.admin;

import java.util.List;

public class CouponListProductDTO {
	private int id;
	private String couponCode;
	private int campaignId;
	private String money;
	private String percent;
	private String threshold;
	private String allProduct;
	private List<Integer> productsId;
	private String amount;
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

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getAllProduct() {
		return allProduct;
	}
	public void setAllProduct(String allProduct) {
		this.allProduct = allProduct;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public List<Integer> getProductsId() {
		return productsId;
	}
	public void setProductsId(List<Integer> productsId) {
		this.productsId = productsId;
	}
	
}
