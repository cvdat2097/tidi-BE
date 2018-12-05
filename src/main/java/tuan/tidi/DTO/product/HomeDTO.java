package tuan.tidi.DTO.product;

import java.util.List;

public class HomeDTO {
	private List<BrandDTO> brand;
	private List<IndustryListBranchDTO> industry;
	
	public List<BrandDTO> getBrand() {
		return brand;
	}
	
	public void setBrand(List<BrandDTO> brand) {
		this.brand = brand;
	}
	
	public List<IndustryListBranchDTO> getIndustry() {
		return industry;
	}
	
	public void setIndustry(List<IndustryListBranchDTO> industry) {
		this.industry = industry;
	}
	
}
