package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.entity.Discount;
import tuan.tidi.entity.Product;

public class ProductDTO {

	private int id;
	private String productName;
	private int price;
	private int amount;
	private String images;
	private String description;
	private CategoryDTO category;
	private BrandDTO brand;
	private BranchDTO branch;
	private IndustryDTO industry;
	private String active;
	private List<Discount> discount;
	private List<CouponDTO> coupon;
	public ProductDTO() {
		
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.amount = product.getAmount();
		this.images = product.getImages();
		this.description = product.getDescription();
		this.active = product.getActive();
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<Discount> getDiscount() {
		return discount;
	}

	public void setDiscount(List<Discount> discount) {
		this.discount = discount;
	}

	public List<CouponDTO> getCoupon() {
		return coupon;
	}

	public void setCoupon(List<CouponDTO> coupon) {
		this.coupon = coupon;
	}
	
	
}
