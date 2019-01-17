package tuan.tidi.DTO.checkout;

import tuan.tidi.DTO.StatusDTO;

public class ZPTokenDTO {
	private String zptranstoken;
	private StatusDTO status;
	private String orderId;
	public StatusDTO getStatus() {
		return status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public String getZptranstoken() {
		return zptranstoken;
	}

	public void setZptranstoken(String zptranstoken) {
		this.zptranstoken = zptranstoken;
	}
	
	@Override
	public String toString() {
		
		return "{\"zptranstoken\":\""+zptranstoken+"\",\"orderId\":\""+orderId+"\",\"status\":"+(status==null? "null" : status.toString())+"}";
	}
	
}
