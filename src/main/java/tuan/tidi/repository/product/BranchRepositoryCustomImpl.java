package tuan.tidi.repository.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Branch;
import tuan.tidi.entity.Brand;

@Repository
public class BranchRepositoryCustomImpl implements BranchRepositoryCustom{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private BranchRepository branchRepository;
	
	public List<Branch> findByIndustryIdActive(int id){
		List<Branch> branch = new ArrayList<Branch>();
		try {
			String sql = "Select e from Branch e where e.active = ?0 and e.industryId like ?1";
			branch = (List<Branch>)entityManager.createQuery(sql).setParameter(0, "TRUE").setParameter(1, id).getResultList();
		}
		catch (NoResultException e) {
			return null;
		}
		return branch;
	}
	
	public List<Branch> search(SearchDTO searchDTO){
		String k = null;
		if (searchDTO.getQuery() != null) k = searchDTO.getQuery().getKeyword();
		if (k != null && !k.isEmpty()) {
			try {
				String sql = "Select e from Branch e where e.branchName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%')";
				return (List<Branch>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				String sql = "Select e from Branch e";
				return (List<Branch>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
	}

	public void updateBranch(Branch branch) {
		Branch bra = branchRepository.findById(branch.getId());
		if (branch.getActive() != null) bra.setActive(branch.getActive());
		if (branch.getBranchName() != null) bra.setBranchName(branch.getBranchName());
		if (branch.getIndustryId() != 0) bra.setIndustryId(branch.getIndustryId());
		branchRepository.save(bra);
	}

}
