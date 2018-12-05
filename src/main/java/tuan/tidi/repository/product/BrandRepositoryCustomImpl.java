package tuan.tidi.repository.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Accounts;
import tuan.tidi.entity.Brand;

@Repository
public class BrandRepositoryCustomImpl implements BrandRepositoryCustom{
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	public List<Brand> search(SearchDTO searchDTO){
		
		String k = searchDTO.getQuery().getKeyword();
		if (k != null && !k.isEmpty()) {
			try {
				String sql = "Select e from Brand e where e.brandName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%')";
				return (List<Brand>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				String sql = "Select e from Brand e";
				return (List<Brand>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
	}
	public void updateBrand(Brand brand) {
		Brand bra = brandRepository.findById(brand.getId());
		if(brand.getActive() != null) bra.setActive(brand.getActive());
		if(brand.getBrandName() != null) bra.setBrandName(brand.getBrandName());
		brandRepository.save(bra);
	}

}
