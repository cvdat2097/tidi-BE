package tuan.tidi.DTO.checkout;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.product.ProductDTO;

public class ListProductDTO {
	private StatusDTO status;
	private List<ProductDTO> products;
	public StatusDTO getStatus() {
		return status;
	}
	public void setStatus(StatusDTO status) {
		this.status = status;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	
}
