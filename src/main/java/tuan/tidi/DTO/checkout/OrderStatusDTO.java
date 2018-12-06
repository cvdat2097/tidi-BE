package tuan.tidi.DTO.checkout;

import tuan.tidi.DTO.StatusDTO;

public class OrderStatusDTO {
	private OrderDTO order;
	private StatusDTO status;
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	
}
