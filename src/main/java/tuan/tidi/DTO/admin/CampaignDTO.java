package tuan.tidi.DTO.admin;

import tuan.tidi.entity.Campaign;
import tuan.tidi.service.FormatDate;

public class CampaignDTO {
	private int id;
	private String campaignName;
	private String startTime;
	private String expiredTime;
	private String active;
	private String description;
	public CampaignDTO() {
		
	}
	
	public CampaignDTO(Campaign campaign) {
		this.id =  campaign.getId();
		this.campaignName = campaign.getCampaignName();
		this.startTime = FormatDate.formatDateTime(campaign.getStartTime());
		this.expiredTime = FormatDate.formatDateTime(campaign.getExpiredTime());
		this.active = campaign.getActive();
		this.description = campaign.getDescription();
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
