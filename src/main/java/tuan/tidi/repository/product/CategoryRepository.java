package tuan.tidi.repository.product;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
	Category findById(int id);
}
