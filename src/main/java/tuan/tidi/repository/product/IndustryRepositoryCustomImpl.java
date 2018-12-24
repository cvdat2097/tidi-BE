package tuan.tidi.repository.product;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Brand;
import tuan.tidi.entity.Industry;

@Repository
public class IndustryRepositoryCustomImpl implements IndustryRepositoryCustom{
	@Autowired 
	private EntityManager entityManager;
	
	@Autowired 
	private IndustryRepository industryRepository;
	public List<Industry> search(SearchDTO searchDTO){
		String k = null;
		if (searchDTO.getQuery() != null) k = searchDTO.getQuery().getKeyword();
		if (k != null && !k.isEmpty()) {
			try {
				String sql = "Select e from Industry e where e.industryName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%')";
				return (List<Industry>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				String sql = "Select e from Industry e";
				return (List<Industry>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
	}

	
	public void updateIndustry(Industry industry) {
		Industry ind = industryRepository.findById(industry.getId());
		if (industry.getActive() != null) ind.setActive(industry.getActive());
		if (industry.getIndustryName() != null) ind.setIndustryName(industry.getIndustryName());
		industryRepository.save(ind);
	}

}
