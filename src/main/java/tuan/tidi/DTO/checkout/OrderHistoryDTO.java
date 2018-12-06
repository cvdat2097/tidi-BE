package tuan.tidi.DTO.checkout;

import tuan.tidi.entity.OrdersHistory;
import tuan.tidi.service.FormatDate;

public class OrderHistoryDTO {
	private String status;
	private String date;
	
	public OrderHistoryDTO() {
		
	}
	public OrderHistoryDTO(OrdersHistory ordersHistory) {
		this.status = ordersHistory.getStatus();
		this.date = FormatDate.formatDateTime((ordersHistory.getDateTime()));
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
