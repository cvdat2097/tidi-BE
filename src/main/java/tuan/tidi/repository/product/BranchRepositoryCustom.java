package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Branch;

public interface BranchRepositoryCustom {
	public List<Branch> findByIndustryIdActive(int id);
	public List<Branch> search(SearchDTO searchDTO);
	public void updateBranch(Branch branch);
}
