package tuan.tidi.repository.account;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Accounts;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {
	Accounts findById(int i);
	Accounts findByUsernameLike(String id);
	Accounts findByEmailLike(String email);
	List<Accounts> findAll();
}

