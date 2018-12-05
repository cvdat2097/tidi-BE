package tuan.tidi.repository.checkout;

import java.util.List;

import tuan.tidi.entity.Cart;
import tuan.tidi.entity.Product;

public interface CartRepositoryCustom {
	public Cart findAccountProduct(int accountId, int productId);
	public List<Product> findItemAccount(int accountId);
}
