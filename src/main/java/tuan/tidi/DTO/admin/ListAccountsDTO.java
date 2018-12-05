package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

public class ListAccountsDTO {
	private List<AccountsDTO> accounts;
	private int totalItems;
	private StatusDTO status;
	public List<AccountsDTO> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<AccountsDTO> accounts) {
		this.accounts = accounts;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	
}
