package tuan.tidi.repository.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.entity.Coupon;

@Repository
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom{
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Coupon> findByProductId(int productId){
		
		try {
			String sql = "Select e from Coupon e join CouPro f on e.id = f.couponId join Product p on p.id = f.productId where p.id = ?0";
			return (List<Coupon>)entityManager.createQuery(sql).setParameter(0, productId).getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
	}

}
