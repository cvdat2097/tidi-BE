package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.product.ProductSearchDTO;
import tuan.tidi.entity.Product;

public interface ProductRepositoryCustom {
	public List<Product> findByBrandIdActive(int id);
	public List<Product> findByCategoryIdActive(int id);
	public StatusDTO insertProduct(Product product);
	public List<Product> search(ProductSearchDTO productSearchDTO);
	public void updateProduct(Product product);

}
