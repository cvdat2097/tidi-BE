package tuan.tidi.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long>{
	Coupon findById(int id);
	List<Coupon> findByCampaignId(int campaignId);
	Coupon findByCouponCodeLike(String couponCode);
	@Query("SELECT coalesce(max(e.id), 0) FROM Coupon e")
	int getMaxId();
}
