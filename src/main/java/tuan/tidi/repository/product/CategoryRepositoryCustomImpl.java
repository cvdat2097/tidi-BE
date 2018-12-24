package tuan.tidi.repository.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Brand;
import tuan.tidi.entity.Category;

@Repository
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findByBranchIdActive(int id){
		List<Category> category = new ArrayList<Category>();
		try {
			String sql = "Select e from Category e where e.active = ?0 and e.branchId like ?1";
			category = (List<Category>)entityManager.createQuery(sql).setParameter(0, "TRUE").setParameter(1, id).getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
		return category;
	}

	public List<Category> search(SearchDTO searchDTO){
		String k = null;
		if (searchDTO.getQuery() != null) k = searchDTO.getQuery().getKeyword();
		
		if (k != null && !k.isEmpty()) {
			try {
				String sql = "Select e from Category e where e.categoryName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%')";
				return (List<Category>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				String sql = "Select e from Category e";
				return (List<Category>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
	}
	
	public void updateCategory(Category category) {
		Category cat = categoryRepository.findById(category.getId());
		if (category.getActive() != null) cat.setActive(category.getActive());
		if (category.getCategoryName() != null) cat.setCategoryName(category.getCategoryName());
		if (category.getBranchId() != 0) cat.setBranchId(category.getBranchId());
		categoryRepository.save(cat);
	}

}
