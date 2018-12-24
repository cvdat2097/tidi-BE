package tuan.tidi.repository.checkout;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Orders;

public interface OrderRepository extends CrudRepository<Orders, Long> {
	@Query("SELECT coalesce(max(e.id), 0) FROM Orders e")
	int getMaxId();
	
	List<Orders> findByAccountsId(int accountsId);
	Orders findById(int id);
	
	Orders findByZptransid(Long zptransid);
	Orders findByApptransid(String apptransid);
}
