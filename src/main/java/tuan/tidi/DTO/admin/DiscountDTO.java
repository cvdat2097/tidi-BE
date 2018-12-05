package tuan.tidi.DTO.admin;

import java.util.Date;

import tuan.tidi.entity.Discount;

public class DiscountDTO {
	private int id;
	private int productId;
	private float percent;
	private Date startTime;
	private Date expiredTime;
	private String active;
	
	public DiscountDTO() {
		
	}
	
	
	
	public DiscountDTO(Discount discount) {
		this.id = discount.getId();
		this.productId = discount.getProductId();
		this.percent = discount.getPercent();
		this.startTime = discount.getStartTime();
		this.expiredTime = discount.getExpiredTime();
		this.active = discount.getActive();
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
}
