package tuan.tidi.repository.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tuan.tidi.entity.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long>{
	Brand findById(int id);
}
