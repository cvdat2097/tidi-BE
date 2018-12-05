package tuan.tidi.DTO.admin;

import tuan.tidi.entity.Coupon;

public class CouponActiveDTO {
	private int id;
	private String couponCode;
	private CampaignDTO campagin;
	private int money;
	private float percent;
	private int threshold;
	private String allProduct;
	private int amount;
	private String active;
	
	public CouponActiveDTO() {
		
	}
	
	public CouponActiveDTO(Coupon coupon) {
		this.id = coupon.getId();
		this.couponCode = coupon.getCouponCode();
		this.money = coupon.getMoney();
		this.percent = coupon.getPercent();
		this.threshold = coupon.getThreshold();
		this.allProduct = coupon.getAllProduct();
		this.amount = coupon.getAmount();
		this.active = coupon.getActive();
	}
}
