package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.CouponListProductDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Coupon;

public interface CouponRepositoryCustom {
	public List<Coupon> findByProductId(int productId);
	public void insertCoupon(CouponListProductDTO couponListProductDTO);
	public void updateCoupon(CouponListProductDTO couponListProductDTO);
	public List<Coupon> search(SearchDTO searchDTO);
}
