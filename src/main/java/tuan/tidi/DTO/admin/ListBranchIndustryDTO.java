package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;

public class ListBranchIndustryDTO {
	private List<BranchIndustryDTO> branches;
	private int totalItems;
	private StatusDTO status;

	public List<BranchIndustryDTO> getBranches() {
		return branches;
	}
	public void setBranches(List<BranchIndustryDTO> branches) {
		this.branches = branches;
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
