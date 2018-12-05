package tuan.tidi.repository.checkout;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Long>{
	Cart findById(int id);
	List<Cart> findByAccountsId(int accountsId);
	List<Cart> findByProductId(int productId);
}
