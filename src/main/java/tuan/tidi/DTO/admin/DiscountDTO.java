package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.entity.Discount;
import tuan.tidi.service.FormatDate;

public class DiscountDTO {
	private int id;
	private int productId;
	private String percent;
	private String startTime;
	private String expiredTime;
	private String active;
	
	public DiscountDTO() {

	}

	public DiscountDTO(Discount discount) {
		this.id = discount.getId();
		this.productId = discount.getProductId();
		this.percent = Float.toString(discount.getPercent());
		this.startTime = FormatDate.formatDateTime(discount.getStartTime());
		this.expiredTime = FormatDate.formatDateTime(discount.getExpiredTime());
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

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}
