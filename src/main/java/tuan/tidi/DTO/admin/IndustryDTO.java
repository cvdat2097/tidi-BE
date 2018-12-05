package tuan.tidi.DTO.admin;

public class IndustryDTO {
	private int id;
	private String industryName;
	
	public IndustryDTO() {
		
	}
	
	public IndustryDTO(int id, String industryName) {
		this.id = id;
		this.industryName = industryName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
}
