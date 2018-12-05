package tuan.tidi.DTO.account;

import tuan.tidi.DTO.StatusDTO;

public class VerifyTokenDTO {
	private StatusDTO status;
	private String permission;

	public String getPermission() {
		return permission;
	}
	
	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
