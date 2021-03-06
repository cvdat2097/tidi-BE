package tuan.tidi.repository.checkout;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.OrdersDetail;

public interface OrderDetailRepository extends CrudRepository<OrdersDetail, Long>{
	List<OrdersDetail> findByOrdersId(int orderId);

}
