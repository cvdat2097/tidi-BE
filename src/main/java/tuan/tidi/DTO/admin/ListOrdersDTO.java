package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

public class ListOrdersDTO {
	private List<OrdersDTO> orders;
	private StatusDTO status;
	private int totalItems;
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public List<OrdersDTO> getOrders() {
		return orders;
	}
	public void setOrders(List<OrdersDTO> orders) {
		this.orders = orders;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
}
