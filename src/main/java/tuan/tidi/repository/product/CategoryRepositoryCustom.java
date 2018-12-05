package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Category;

public interface CategoryRepositoryCustom {
	public List<Category> findByBranchIdActive(int id);
	public List<Category> search(SearchDTO searchDTO);
	public void updateCategory(Category category);
}
