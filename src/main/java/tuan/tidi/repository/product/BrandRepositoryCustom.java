package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Branch;
import tuan.tidi.entity.Brand;

public interface BrandRepositoryCustom {
	public List<Brand> search(SearchDTO searchDTO);
	public void updateBrand(Brand brand);
}
