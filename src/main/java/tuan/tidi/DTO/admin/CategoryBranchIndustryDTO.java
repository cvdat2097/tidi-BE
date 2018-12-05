package tuan.tidi.DTO.admin;

public class CategoryBranchIndustryDTO {
	private int id;
	private String categoryName;
	private BranchIndustryDTO branch;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BranchIndustryDTO getBranch() {
		return branch;
	}
	public void setBranch(BranchIndustryDTO branch) {
		this.branch = branch;
	}
	
	
}
