package tuan.tidi.DTO.product;

import java.util.List;

public class ListProductDTO {
	private List<ProductDTO> products;
	private int totalItems;

	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
}
