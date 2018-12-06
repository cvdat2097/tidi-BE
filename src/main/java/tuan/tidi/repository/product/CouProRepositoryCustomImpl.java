package tuan.tidi.repository.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CouProRepositoryCustomImpl implements CouProRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public void nonActiveCouPro(int couponId) {
		try {
			String sql = "Update CouPro e set e.active = 'FALSE' where e.couponId = ?0";
			Query query = entityManager.createQuery(sql).setParameter(0, couponId);
			query.executeUpdate();
		}
		catch (NoResultException e) {
		}
		
	}

}
