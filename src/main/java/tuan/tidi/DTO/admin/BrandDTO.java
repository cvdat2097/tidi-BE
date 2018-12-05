package tuan.tidi.DTO.admin;

public class BrandDTO {
	private int id;
	private String brandName;
	
	public BrandDTO() {
		
	}
	
	public BrandDTO(int id, String brandName) {
		this.id = id;
		this.brandName = brandName;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
