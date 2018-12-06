package tuan.tidi.DTO.checkout;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

public class ListOrdersDTO {
	private List<OrdersDTO> orders;
	private StatusDTO status;
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
