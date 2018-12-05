package tuan.tidi.repository.account;

import java.util.List;

import tuan.tidi.DTO.account.AccountsDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Accounts;

public interface AccountsRepositoryCustom {
	public void insertAccounts(String username, String passwords, String permission, String email);
	public void update(String username, AccountsDTO accountsDTO);
	public List<Accounts> search(SearchDTO accountSearchDTO);
}
