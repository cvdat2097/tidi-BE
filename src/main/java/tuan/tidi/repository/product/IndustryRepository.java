package tuan.tidi.repository.product;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Industry;

public interface IndustryRepository extends CrudRepository<Industry, Long>{
	Industry findById(int i);
}
