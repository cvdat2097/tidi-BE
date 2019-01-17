package tuan.tidi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Indexed
@AnalyzerDef(name = "customanalyzer",
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
filters = {
  @TokenFilterDef(factory = LowerCaseFilterFactory.class),
  @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
    @Parameter(name = "language", value = "English")
  })
})
@Table(name = "product")
public class Product {
	
	@Id
	private int id;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name = "productName", length = 100, nullable = false)
	private String productName;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "amount", nullable = false)
	private int amount;
	
	@Column(name = "images", length = 200, nullable = true)
	private String images;
	
	@Column(name = "categoryId", nullable = true)
	private int categoryId;
	
	@Column(name = "branchId", nullable = true)
	private int branchId;
	
	@Column(name = "industryId", nullable = true)
	private int industryId;
	
	@Column(name = "brandId", nullable = true)
	private int brandId;
	
	@Column(name = "description", length = 1000, nullable = true)
	private String description;
	
	@Column(name = "longDescription", length = 1000, nullable = true)
	private String longDescription;
	
	@Column(name = "active", nullable = true)
	private String active;
	
	public void setProduct(Product product) {
		this.productName = product.getProductName();
		this.price = product.getPrice();
		this.amount = product.getAmount();
		this.images = product.getImages();
		this.categoryId = product.getCategoryId();
		this.branchId = product.getBranchId();
		this.brandId = product.getBrandId();
		this.industryId = product.getIndustryId();
		this.description = product.getDescription();
		this.active = product.getActive();
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public int getIndustryId() {
		return industryId;
	}

	public void setIndustryId(int industryId) {
		this.industryId = industryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
}
