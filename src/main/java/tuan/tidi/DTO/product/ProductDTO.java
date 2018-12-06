package tuan.tidi.DTO.product;

import tuan.tidi.entity.Product;

public class ProductDTO {

	private int id;
	private String productName;
	private int price;
	private int amount;
	private String images;
	private String description;
	private float discPercent;
	private CategoryDTO category;
	private BrandDTO brand;
	private BranchDTO branch;
	private IndustryDTO industry;

	public ProductDTO() {
		
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.amount = product.getAmount();
		this.images = product.getImages();
		this.description = product.getDescription();
		
	}
	
	public float getDiscPercent() {
		return discPercent;
	}

	public void setDiscPercent(float discPercent) {
		this.discPercent = discPercent;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getImages() {
		return images;
	}
	
	public void setImages(String images) {
		this.images = images;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public BrandDTO getBrand() {
		return brand;
	}

	public void setBrand(BrandDTO brand) {
		this.brand = brand;
	}

	public BranchDTO getBranch() {
		return branch;
	}

	public void setBranch(BranchDTO branch) {
		this.branch = branch;
	}

	public IndustryDTO getIndustry() {
		return industry;
	}

	public void setIndustry(IndustryDTO industry) {
		this.industry = industry;
	}
	
}
