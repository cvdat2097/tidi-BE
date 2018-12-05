package tuan.tidi.DTO.product;

import java.util.List;

public class BranchListCategoryDTO {
	private int id;
	private String branchName;
	private List<CategoryDTO> categories; 
	
	public BranchListCategoryDTO() {
		
	}
	
	public BranchListCategoryDTO(int id, String branchName, List<CategoryDTO> category) {
		this.id = id;
		this.branchName = branchName;
		this.categories = category;
	}

	
	
	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
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
