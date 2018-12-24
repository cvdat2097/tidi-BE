package tuan.tidi.repository.checkout;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Orders;

public interface OrderRepositoryCustom {
	public List<Orders> search(SearchDTO searchDTO, int accountId);
}
