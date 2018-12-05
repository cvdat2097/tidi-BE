package tuan.tidi.repository.product;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DiscountRepositoryCustomImpl implements DiscountRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
	
	public float findLastedDiscount(int productId) {
		float percent = 0;
		try {
			String sql = "SELECT e.percent from Discount e where e.active = 'TRUE' and e.startTime = (select MAX(f.startTime) from Discount f where f.productId = ?0)";
			percent = (float)entityManager.createQuery(sql).setParameter(0, productId).setMaxResults(1).getSingleResult();
		}
		catch (NoResultException e) {
			return 0;
		}
		return percent;
	}

}
