package tuan.tidi.repository.product;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.CouponListProductDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Campaign;
import tuan.tidi.entity.CouPro;
import tuan.tidi.entity.Coupon;
import tuan.tidi.service.FormatDate;

@Repository
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouProRepository couProRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouProRepositoryCustomImpl couProRepositoryCustomImpl;

	public List<Coupon> findByProductId(int productId) {

		try {
			String sql = "Select e from Coupon e join CouPro f on e.id = f.couponId join Product p on p.id = f.productId where p.id = ?0";
			return (List<Coupon>) entityManager.createQuery(sql).setParameter(0, productId).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void insertCoupon(CouponListProductDTO couponListProductDTO) {
		int couponId = couponRepository.getMaxId() + 1;
		Set<Integer> productsId = new HashSet<Integer>(couponListProductDTO.getProductsId());
		int numberProduct = productsId.size();
		Coupon coupon = new Coupon();
		coupon.setActive("TRUE");
		coupon.setId(couponId);
		coupon.setAllProduct(couponListProductDTO.getAllProduct());
		try {
			coupon.setAmount(Integer.parseInt(couponListProductDTO.getAmount()));
		} catch (Exception e) {
			coupon.setAmount(0);
		}
		coupon.setCampaignId(couponListProductDTO.getCampaignId());
		coupon.setCouponCode(couponListProductDTO.getCouponCode());
		try {
			coupon.setMoney(Integer.parseInt(couponListProductDTO.getMoney()));
		} catch (Exception e) {
			coupon.setMoney(0);
		}
		try {
			coupon.setPercent(Float.parseFloat(couponListProductDTO.getPercent()));
		} catch (Exception e) {
			coupon.setPercent(0);
		}
		try {
			coupon.setThreshold(Integer.parseInt(couponListProductDTO.getThreshold()));
		} catch (Exception e) {
			coupon.setThreshold(0);
		}
		couponRepository.save(coupon);

		for (Integer i : productsId) {
			if (productRepository.findById(i) == null) {
				continue;
			}
			CouPro couPro = new CouPro();
			couPro.setActive("TRUE");
			couPro.setCouponId(couponId);
			couPro.setProductId(i);

			try {
				couProRepository.save(couPro);
			} catch (Exception e) {
				System.out.println(e + "\n");
				System.out.println(couPro.getId() + " === " + couPro.getProductId() + "===== \n");
			}
			try {
				couProRepository.save(couPro);
			} catch (Exception e) {
				System.out.println(e + "\n");
				System.out.println(couPro.getId() + " === " + couPro.getProductId() + "===== \n");
			}
		}
	}

	public void updateCoupon(CouponListProductDTO couponListProductDTO) {
		Coupon coupon = couponRepository.findById(couponListProductDTO.getId());
		if (couponListProductDTO.getActive() != null)
			coupon.setActive(couponListProductDTO.getActive());
		if (couponListProductDTO.getAllProduct() != null)
			coupon.setAllProduct(couponListProductDTO.getAllProduct());
		try {
			if (couponListProductDTO.getAmount() != null)
				coupon.setAmount(Integer.parseInt(couponListProductDTO.getAmount()));
		} catch (Exception e) {
		}
		if (couponListProductDTO.getCampaignId() != 0)
			coupon.setCampaignId(couponListProductDTO.getCampaignId());
		if (couponListProductDTO.getCouponCode() != null)
			coupon.setCouponCode(couponListProductDTO.getCouponCode());
		try {
			if (couponListProductDTO.getMoney() != null)
				coupon.setMoney(Integer.parseInt(couponListProductDTO.getMoney()));
		} catch (Exception e) {
		}
		try {
			if (couponListProductDTO.getPercent() != null)
				coupon.setPercent(Float.parseFloat(couponListProductDTO.getPercent()));
		} catch (Exception e) {
		}
		try {
			if (couponListProductDTO.getThreshold() != null)
				coupon.setThreshold(Integer.parseInt(couponListProductDTO.getThreshold()));
		} catch (Exception e) {
		}
		couponRepository.save(coupon);
		if (couponListProductDTO.getProductsId() != null) {
			int couponId = couponListProductDTO.getId();
			Set<Integer> productsId = new HashSet<Integer>(couponListProductDTO.getProductsId());
			// couProRepositoryCustomImpl.nonActiveCouPro(couponId);
			for (int i : productsId) {
				if (productRepository.findById(i) == null) {
					continue;
				}
				CouPro couPro = new CouPro();
				couPro.setActive("TRUE");
				couPro.setCouponId(couponId);
				couPro.setProductId(i);

				try {
					couProRepository.save(couPro);
				} catch (Exception e) {
					System.out.println(e + "\n");
					System.out.println(couPro.getId() + " === " + couPro.getProductId() + "===== \n");
				}
				try {
					couProRepository.save(couPro);
				} catch (Exception e) {
					System.out.println(e + "\n");
					System.out.println(couPro.getId() + " === " + couPro.getProductId() + "===== \n");
				}
			}
		}
	}

	public List<Coupon> search(SearchDTO searchDTO) {
		if (searchDTO.getQuery().getStartTime() == null && searchDTO.getQuery().getExpiredTime() == null) {
			try {
				String sql = "Select e from Coupon e";
				return (List<Coupon>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit())
						.setFirstResult(searchDTO.getOffset()).getResultList();
			} catch (NoResultException e) {
				return null;
			}
		}
		Date st, et;
		try {
			st = FormatDate.parseDateTime(searchDTO.getQuery().getStartTime());
		} catch (Exception e) {
			st = new Date();
		}
		try {
			et = FormatDate.parseDateTime(searchDTO.getQuery().getExpiredTime());
		} catch (Exception e) {
			et = new Date();
		}
		try {
			String sql = "Select e from Coupon e join Campaign c on e.campaignId = c.id where c.startTime <= ?0 and c.expiredTime >= ?1";
			return (List<Coupon>) entityManager.createQuery(sql).setParameter(0, st).setParameter(1, et)
					.setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
