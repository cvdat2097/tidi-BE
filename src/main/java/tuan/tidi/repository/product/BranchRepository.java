package tuan.tidi.repository.product;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Branch;

public interface BranchRepository extends CrudRepository<Branch, Long>{
	Branch findById(int id);
}
