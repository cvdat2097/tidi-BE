package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.entity.Campaign;

public class ListCampaignDTO {
	private List<Campaign> campaign;
	private int totalItems;
	private StatusDTO status;
	public List<Campaign> getCampaign() {
		return campaign;
	}
	public void setCampaign(List<Campaign> campaign) {
		this.campaign = campaign;
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
