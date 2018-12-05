package tuan.tidi.DTO.account;

import tuan.tidi.DTO.StatusDTO;

public class LoginDTO {
	private StatusDTO status;
	private String permission;
	private String token;
	
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
