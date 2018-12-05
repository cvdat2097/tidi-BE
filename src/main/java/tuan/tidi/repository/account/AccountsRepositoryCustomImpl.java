package tuan.tidi.repository.account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.account.AccountsDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Accounts;
import tuan.tidi.service.FormatDate;

@Repository
public class AccountsRepositoryCustomImpl implements AccountsRepositoryCustom{
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	public void insertAccounts(String username, String passwords, String permission, String email){
		Accounts accounts  = new Accounts();
		accounts.setUsername(username);
		accounts.setPassword(passwords);
		accounts.setPermission(permission);
		accounts.setEmail(email);
		accountsRepository.save(accounts);
	}
	
	public void update(String username, AccountsDTO accountsDTO) {
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		if (accountsDTO.getAvatar() != null) accounts.setAvatar(accountsDTO.getAvatar());
		if (accountsDTO.getFullName() != null) accounts.setFullName(accountsDTO.getFullName());
		if (accountsDTO.getDateOfBirth() != null) accounts.setDateOfBirth(FormatDate.parseDate(accountsDTO.getDateOfBirth()));
		if (accountsDTO.getGender() != null) accounts.setGender(accountsDTO.getGender());
		if (accountsDTO.getPhone() != null) accounts.setPhone(accountsDTO.getPhone());
		if (accountsDTO.getEmail() != null) accounts.setEmail(accountsDTO.getEmail());
		if (accountsDTO.getAddress() != null) accounts.setAddress(accountsDTO.getAddress());
		accountsRepository.save(accounts);
	}
	
	public void adminUpdate(int id, tuan.tidi.DTO.admin.AccountsDTO accountsDTO) {
		Accounts accounts = accountsRepository.findById(id);
		if (accountsDTO.getUsername() != null) accounts.setUsername(accountsDTO.getUsername());
		if (accountsDTO.getAvatar() != null) accounts.setAvatar(accountsDTO.getAvatar());
		if (accountsDTO.getFullName() != null) accounts.setFullName(accountsDTO.getFullName());
		if (accountsDTO.getDateOfBirth() != null) accounts.setDateOfBirth(FormatDate.parseDate(accountsDTO.getDateOfBirth()));
		if (accountsDTO.getGender() != null) accounts.setGender(accountsDTO.getGender());
		if (accountsDTO.getPhone() != null) accounts.setPhone(accountsDTO.getPhone());
		if (accountsDTO.getEmail() != null) accounts.setEmail(accountsDTO.getEmail());
		if (accountsDTO.getAddress() != null) accounts.setAddress(accountsDTO.getAddress());
		if (accountsDTO.getActive() != null) accounts.setActive(accountsDTO.getActive());
		if (accountsDTO.getPermission() != null) accounts.setPermission(accountsDTO.getPermission());
		accountsRepository.save(accounts);
	}

	public List<Accounts> search(SearchDTO accountSearchDTO){ 
		String k = accountSearchDTO.getQuery().getKeyword();
		if (k != null) {	
			try {
				String sql = "Select e from Accounts e where e.username like CONCAT('%',?0,'%') or e.fullName like CONCAT('%',?1,'%') or e.dateOfBirth like CONCAT('%',?2,'%') or e.gender like CONCAT('%',?3,'%') or e.phone like CONCAT('%',?4,'%') or e.email like CONCAT('%',?5,'%') or e.address like CONCAT('%',?6,'%') or e.active like CONCAT('%',?7,'%') or e.isVerified like CONCAT('%',?8,'%')";
				return (List<Accounts>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k).setParameter(2, k).setParameter(3, k).setParameter(4, k).setParameter(5, k).setParameter(6, k).setParameter(7, k).setParameter(8, k).setMaxResults(accountSearchDTO.getLimit()).setFirstResult(accountSearchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				String sql = "Select e from Accounts e";
				return (List<Accounts>) entityManager.createQuery(sql).setMaxResults(accountSearchDTO.getLimit()).setFirstResult(accountSearchDTO.getOffset()).getResultList();
			}
			catch (NoResultException e) {
				return null;
			}
		}
	}

}
