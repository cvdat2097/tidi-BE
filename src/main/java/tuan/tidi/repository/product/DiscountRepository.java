package tuan.tidi.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Long>{
	List<Discount> findByProductIdLike(int productId);
	Discount findById(int id);
	@Query("SELECT coalesce(max(e.id), 0) FROM Discount e")
	int getMaxId();
}
