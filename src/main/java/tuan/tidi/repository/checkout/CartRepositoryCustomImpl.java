package tuan.tidi.repository.checkout;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.entity.Cart;
import tuan.tidi.entity.Product;

@Repository
public class CartRepositoryCustomImpl implements CartRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
	
	public Cart findAccountProduct(int accountId, int productId) {
		try {
			String sql = "Select e from Cart e where e.accountsId = ?0 and e.productId = ?1";
			return (Cart) entityManager.createQuery(sql).setParameter(0, accountId).setParameter(1, productId).getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	
	public List<Product> findItemAccount(int accountId) {
		try {
			String sql = "Select p from (Product p join Cart c on p.id = c.productId) join Accounts a on a.id = c.accountsId where a.id = ?0";
			return (List<Product>) entityManager.createQuery(sql).setParameter(0, accountId).getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
	}
}
