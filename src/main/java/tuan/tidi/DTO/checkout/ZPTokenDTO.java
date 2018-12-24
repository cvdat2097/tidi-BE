package tuan.tidi.DTO.checkout;

import tuan.tidi.DTO.StatusDTO;

public class ZPTokenDTO {
	private String zptranstoken;
	private StatusDTO status;
	public StatusDTO getStatus() {
		return status;
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
	
}
