package tuan.tidi.repository.product;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.DiscountDTO;
import tuan.tidi.DTO.admin.DiscountListProductDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Coupon;
import tuan.tidi.entity.Discount;
import tuan.tidi.service.FormatDate;

@Repository
public class DiscountRepositoryCustomImpl implements DiscountRepositoryCustom {
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private DiscountRepository discountRepository;
	
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

	public List<Discount> search(SearchDTO searchDTO){
		if (searchDTO.getQuery().getStartTime() == null && searchDTO.getQuery().getExpiredTime() == null) {
			try {
				String sql = "Select e from Discount e";
				return (List<Discount>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit())
						.setFirstResult(searchDTO.getOffset()).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		}
		Date st, et;
		try {
			st = FormatDate.parseDateTime(searchDTO.getQuery().getStartTime());
		} catch (Exception e) {
			st = new Date();
		}
		try {
			et = FormatDate.parseDateTime(searchDTO.getQuery().getExpiredTime());
		} catch (Exception e) {
			et = new Date();
		}
		try {
			String sql = "Select e from Discount e where e.startTime <= ?0 and e.expiredTime >= ?1";
			return (List<Discount>) entityManager.createQuery(sql).setParameter(0, st).setParameter(1, et)
					.setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void insertDiscount(DiscountListProductDTO discountDTO) {
		Discount discount = new Discount();
		discount.setActive("TRUE");
		discount.setExpiredTime(FormatDate.parseDateTime(discountDTO.getExpiredTime()));
		discount.setPercent(Float.parseFloat(discountDTO.getPercent()));
		discount.setStartTime(FormatDate.parseDateTime(discountDTO.getStartTime()));
		Set<Integer> productsId = new HashSet<Integer>(discountDTO.getProductsId());
		for (int i : productsId) {
			discount.setProductId(i);
			try {
				discountRepository.save(discount);
			} catch(Exception e) {
				
			}
			try {
				discountRepository.save(discount);
			} catch(Exception e) {
				
			}
		}
	}
	
	public void updateDiscount(DiscountDTO discountDTO) {
		Discount discount = discountRepository.findById(discountDTO.getId());
		if (discountDTO.getActive() != null) discount.setActive(discountDTO.getActive());
		if (discountDTO.getExpiredTime() != null) discount.setExpiredTime(FormatDate.parseDateTime(discountDTO.getExpiredTime()));
		if (discountDTO.getPercent() != null) discount.setPercent(Float.parseFloat(discountDTO.getPercent()));
		if (discountDTO.getStartTime() != null) discount.setStartTime(FormatDate.parseDateTime(discountDTO.getStartTime()));
		if (discountDTO.getProductId() != 0) discount.setProductId(discountDTO.getProductId());
		discountRepository.save(discount);
	}

}
