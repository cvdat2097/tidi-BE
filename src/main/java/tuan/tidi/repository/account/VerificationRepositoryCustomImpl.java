package tuan.tidi.repository.account;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.entity.Verification;

@Repository
public class VerificationRepositoryCustomImpl implements VerificationRepositoryCustom {
	
	@Autowired 
	private EntityManager entityManager;
	
	public Verification findAccountsIdType(int id, String veriType) {
		Verification verification = new Verification();
		try {
			String sql = "Select e from Verification e where e.accountsId = ?0 and e.veriType = ?1 and e.active = ?2";
			verification = (Verification) entityManager.createQuery(sql).setParameter(0, id).setParameter(1, veriType).setParameter(2, "TRUE").getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		return verification;
	}
	
	public Verification findCodeType(String veriCode, String veriType) {
		Verification verification = new Verification();
		try {
			String sql = "Select e from Verification e where e.vcode = ?0 and e.veriType = ?1 and e.active = ?2";
			verification = (Verification) entityManager.createQuery(sql).setParameter(0, veriCode).setParameter(1, veriType).setParameter(2, "TRUE").getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		return verification;
	}
}
