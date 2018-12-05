package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.entity.Brand;

public class ListCategoryBranchIndustryDTO {
	private List<CategoryBranchIndustryDTO> categories;
	private int totalItems;
	private StatusDTO status;
	public List<CategoryBranchIndustryDTO> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryBranchIndustryDTO> categories) {
		this.categories = categories;
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
