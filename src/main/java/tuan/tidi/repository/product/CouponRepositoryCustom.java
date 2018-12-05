package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.entity.Coupon;

public interface CouponRepositoryCustom {
	public List<Coupon> findByProductId(int productId);
}
