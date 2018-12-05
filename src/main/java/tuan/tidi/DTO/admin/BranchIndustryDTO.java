package tuan.tidi.DTO.admin;

public class BranchIndustryDTO {
	private int id;
	private String branchName;
	private IndustryDTO industry;
	
	public IndustryDTO getIndustry() {
		return industry;
	}

	public void setIndustry(IndustryDTO industry) {
		this.industry = industry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
}
