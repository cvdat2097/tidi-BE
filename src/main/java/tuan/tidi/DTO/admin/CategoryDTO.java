package tuan.tidi.DTO.admin;

public class CategoryDTO {
	private int id;
	private String categoryName;
	
	public CategoryDTO() {
		
	}
	
	public CategoryDTO(int id, String categoryName) {
		this.id = id;
		this.categoryName = categoryName;
	}

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
}
