package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.entity.Industry;

public class ListIndustryDTO {
	private List<Industry> industries;
	private int totalItems;
	private StatusDTO status;

	public List<Industry> getIndustries() {
		return industries;
	}
	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
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
