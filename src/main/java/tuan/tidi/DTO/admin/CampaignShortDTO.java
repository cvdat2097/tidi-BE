package tuan.tidi.DTO.admin;

import tuan.tidi.entity.Campaign;
import tuan.tidi.service.FormatDate;

/**
 * @author tuan
 *
 */
public class CampaignShortDTO {
	private int id;
	private String campaignName;
	private String startTime;
	private String expiredTime;
	
	public CampaignShortDTO() {
		
	}
	
	public CampaignShortDTO(Campaign campaign) {
		this.id = campaign.getId();
		this.campaignName = campaign.getCampaignName();
		this.startTime = FormatDate.formatDateTime(campaign.getStartTime());
		this.expiredTime = FormatDate.formatDateTime(campaign.getExpiredTime());
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
	
}
