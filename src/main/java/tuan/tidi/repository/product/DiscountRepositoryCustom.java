package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.DiscountDTO;
import tuan.tidi.DTO.admin.DiscountListProductDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Discount;

public interface DiscountRepositoryCustom {
	public float findLastedDiscount(int productId);
	public List<Discount> search(SearchDTO searchDTO);
	public void insertDiscount(DiscountListProductDTO discountDTO);
	public void updateDiscount(DiscountDTO discountDTO);
}
