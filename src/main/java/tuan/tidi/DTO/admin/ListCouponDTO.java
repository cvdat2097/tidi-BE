package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

public class ListCouponDTO {
	private List<CouponDTO> coupons;
	private int totalItems;
	private StatusDTO status;

	public List<CouponDTO> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<CouponDTO> coupons) {
		this.coupons = coupons;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}

}
