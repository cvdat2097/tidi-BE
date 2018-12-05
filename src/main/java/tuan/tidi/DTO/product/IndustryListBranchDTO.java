package tuan.tidi.DTO.product;

import java.util.List;

public class IndustryListBranchDTO {
	private int id;
	private String industryName;
	private List<BranchListCategoryDTO> branches;
	
	public IndustryListBranchDTO() {
		
	}
	
	public IndustryListBranchDTO(int id, String industryName, List<BranchListCategoryDTO> branches) {
		this.id = id;
		this.industryName = industryName;
		this.branches = branches;
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

	public List<BranchListCategoryDTO> getBranches() {
		return branches;
	}

	public void setBranches(List<BranchListCategoryDTO> branches) {
		this.branches = branches;
	}

}
