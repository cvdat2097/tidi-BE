package tuan.tidi.repository.product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long>{
	Coupon findById(int id);
	List<Coupon> findByCampaignId(int campaignId);
}
