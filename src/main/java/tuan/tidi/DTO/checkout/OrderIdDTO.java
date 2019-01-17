package tuan.tidi.DTO.checkout;

import tuan.tidi.DTO.StatusDTO;

/**
 * @author tuan
 *
 */
public class OrderIdDTO {
	private int OrderId;
	private StatusDTO status;
	
	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public int getOrderId() {
		return OrderId;
	}

	public void setOrderId(int orderId) {
		OrderId = orderId;
	}
	
}
