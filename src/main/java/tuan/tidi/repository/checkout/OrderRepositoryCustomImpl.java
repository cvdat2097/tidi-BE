package tuan.tidi.repository.checkout;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Orders;
import tuan.tidi.service.FormatDate;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	public List<Orders> search(SearchDTO searchDTO, int accountId) {
		String sql;
		if (searchDTO.getQuery() == null) {
			try {
				if (accountId != 0) {
					sql = "select e from Orders e where e.accountsId = ?0 order by e.id desc";
					return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
							.setMaxResults(searchDTO.getLimit()).setParameter(0, accountId).getResultList();
				}
				else {
					sql = "select e from Orders e order by e.id desc";
				
				return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
						.setMaxResults(searchDTO.getLimit()).getResultList();
				}
			} catch (Exception e) {
				return null;
			}
		} else {
			if ((searchDTO.getQuery().getStartTime() != null) && (searchDTO.getQuery().getExpiredTime() != null)){
				try {
					Date st;
					Date et;
					try{
						st = FormatDate.parseDateTime(searchDTO.getQuery().getStartTime());
					}catch(Exception e) {
						st = FormatDate.parseDateTime("2000-01-01 00:00:00");
					}
					try{
						et = FormatDate.parseDateTime(searchDTO.getQuery().getExpiredTime());
					}catch(Exception e) {
						et = new Date();
					}
					if (accountId != 0) {
						sql = "select e from Orders e join OrdersHistory f on e.id = f.orderId where e.accountsId = ?0 and (f.dateTime >= ?1 and f.dateTime <= ?2) order by e.id desc";
						return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
							.setMaxResults(searchDTO.getLimit()).setParameter(0, accountId)
							.setParameter(1,st) 
							.setParameter(2, et)
							.getResultList();
					}else {
						sql = "select e from Orders e join OrdersHistory f on e.id = f.orderId where f.dateTime >= ?0 and f.dateTime <= ?1 order by e.id desc";
						return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
							.setMaxResults(searchDTO.getLimit())
							.setParameter(0,st) 
							.setParameter(1, et)
							.getResultList();
					}

				} catch (Exception e) {
					return null;
				}
			}
			else {
				try {
					if (accountId != 0) {
						sql = "select e from Orders e where e.accountsId = ?0 order by e.id desc";
						return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
							.setMaxResults(searchDTO.getLimit()).setParameter(0, accountId).getResultList();
					}else {
						sql = "select e from Orders e order by e.id desc";
						return (List<Orders>) entityManager.createQuery(sql).setFirstResult(searchDTO.getOffset())
							.setMaxResults(searchDTO.getLimit()).getResultList();
					
					}
				} catch (Exception e) {
					System.out.println("=====================" + e);
					return null;
				}
			}
		}
	}
}
