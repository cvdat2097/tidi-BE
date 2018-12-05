package tuan.tidi.repository.account;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Verification;

public interface VerificationRepository extends CrudRepository<Verification, Long>{
	public Verification findById(int id);
}
