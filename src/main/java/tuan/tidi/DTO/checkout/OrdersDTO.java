package tuan.tidi.DTO.checkout;

import tuan.tidi.entity.Orders;

public class OrdersDTO {
	private int orderId;
	private String date;
	private int total;
	private String status;
	
	public OrdersDTO() {
		
	}
	
	public OrdersDTO(Orders orders) {
		this.orderId = orders.getId();
		this.total = orders.getTotal();
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
