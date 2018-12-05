package tuan.tidi.repository.account;

import tuan.tidi.entity.Verification;

public interface VerificationRepositoryCustom {
	public Verification findAccountsIdType(int id, String veriType);
	public Verification findCodeType(String veriCode, String veriType);
}
