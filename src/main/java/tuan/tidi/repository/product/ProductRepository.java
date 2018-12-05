package tuan.tidi.repository.product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{
	Product findById(int id);
	List<Product> findByBrandIdLike(int id);
	List<Product> findByBranchIdLike(int id);
	List<Product> findByIndustryIdLike(int id);
	List<Product> findByCategoryIdLike(int id);
	List<Product> findAll();
}
