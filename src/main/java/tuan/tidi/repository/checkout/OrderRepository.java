package tuan.tidi.repository.checkout;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Orders;

public interface OrderRepository extends CrudRepository<Orders, Long> {
	@Query("SELECT coalesce(max(e.id), 0) FROM Orders e")
	int getMaxId();
}
