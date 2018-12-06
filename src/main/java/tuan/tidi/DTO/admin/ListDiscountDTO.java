package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

/**
 * @author tuan
 *
 */
public class ListDiscountDTO {
	private List<DiscountDTO> discounts;
	private StatusDTO status;
	private int totalItems;

	public List<DiscountDTO> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(List<DiscountDTO> discounts) {
		this.discounts = discounts;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
}
