package tuan.tidi.repository.checkout;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.OrdersHistory;

public interface OrdersHistoryRepository extends CrudRepository<OrdersHistory,Long>{
	List<OrdersHistory> findByOrderId(int ordersId);
}
