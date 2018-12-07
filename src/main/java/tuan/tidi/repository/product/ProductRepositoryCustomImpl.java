package tuan.tidi.repository.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.admin.ProductUpdateDTO;
import tuan.tidi.DTO.product.ProductSearchDTO;
import tuan.tidi.entity.Product;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private IndustryRepository industryRepository;

	@Autowired
	private DiscountRepositoryCustomImpl discountRepositoryCustomImpl;

	public List<Product> findByBrandIdActive(int id) {
		List<Product> product = new ArrayList<Product>();
		try {
			String sql = "Select e from Product e where e.active = ?0 and e.brandId like ?1";
			product = (List<Product>) entityManager.createQuery(sql).setParameter(0, "TRUE").setParameter(1, id)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return product;
	}

	public List<Product> findByCategoryIdActive(int id) {
		List<Product> product = new ArrayList<Product>();
		try {
			String sql = "Select e from Product e where e.active = ?0 and e.categoryId like ?1";
			product = (List<Product>) entityManager.createQuery(sql).setParameter(0, "TRUE").setParameter(1, id)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
		return product;
	}

	public StatusDTO insertProduct(Product product) {
		StatusDTO statusDTO = new StatusDTO();

		// check productname
		if (product.getProductName() == null || product.getProductName().isEmpty()) {
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("ProductName is null!!!");
			return statusDTO;
		}
		System.out.println(product.getId());
		// check CategoryID
		if (categoryRepository.findById(product.getCategoryId()) == null) {
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("CategoryID has not been existed!!!");
			return statusDTO;
		}

		// check BrandID
		if (brandRepository.findById(product.getBrandId()) == null) {
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Brand has not been existed!!!");
			return statusDTO;
		}

		// check BranchId
		if (branchRepository.findById(product.getBranchId()) == null) {
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Branch has not been existed!!!");
			return statusDTO;
		}

		// check industryID
		if (industryRepository.findById(product.getIndustryId()) == null) {
			statusDTO.setStatus("FALSE");
			statusDTO.setMessage("Industry has not been existed!!!");
			return statusDTO;
		}
		statusDTO.setMessage("Successful!");

		// check price
		if (product.getPrice() == 0) {
			statusDTO.setMessage("Warning: Price is zero!!");
		}

		// check amount
		if (product.getAmount() == 0) {
			statusDTO.setMessage("Warning: Amount is zero!!!");
		}

		try {
			productRepository.save(product);
		} catch (Exception e) {
			System.out.println(e);
		}
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	public List<Product> search(ProductSearchDTO productSearchDTO) {
		List<Product> product = new ArrayList<Product>();

		String sql = "Select e from Product e ";
		int kt = 0;
		int d = 0;
		if (productSearchDTO.getQuery() != null) {
			if (productSearchDTO.getQuery().getBrandId() != 0) {
				if (kt == 1) {
					sql += "and ";
				} else {
					sql += "where ";
				}
				sql += "e.brandId = ?" + d + " ";
				d++;
				kt = 1;
			}
			if (productSearchDTO.getQuery().getBranchId() != 0) {
				if (kt == 1) {
					sql += "and ";
				} else {
					sql += "where ";
				}
				kt = 1;
				sql += "e.branchId = ?" + d + " ";
				d++;
			}
			if (productSearchDTO.getQuery().getIndustryId() != 0) {
				if (kt == 1) {
					sql += "and ";
				} else {
					sql += "where ";
				}
				kt = 1;
				sql += "e.industryId = ?" + d + " ";
				d++;
			}
			if (productSearchDTO.getQuery().getCategoryId() != 0) {
				if (kt == 1) {
					sql += "and ";
				} else {
					sql += "where ";
				}
				kt = 1;
				sql += "e.categoryId = ?" + d + " ";
				d++;
			}
			if (productSearchDTO.getQuery().getKeyword() != null
					&& !productSearchDTO.getQuery().getKeyword().isEmpty()) {
				if (kt == 1) {
					sql += "and ";
				} else {
					sql += "where ";
				}
				kt = 1;
				sql += "e.productName like CONCAT('%',?" + d + ",'%') ";
			}

			Query query = entityManager.createQuery(sql);
			d = 0;
			if (productSearchDTO.getQuery().getBrandId() != 0) {
				query.setParameter(d, productSearchDTO.getQuery().getBrandId());
				d++;
			}
			if (productSearchDTO.getQuery().getBranchId() != 0) {
				query.setParameter(d, productSearchDTO.getQuery().getBranchId());
				d++;
			}
			if (productSearchDTO.getQuery().getIndustryId() != 0) {
				query.setParameter(d, productSearchDTO.getQuery().getIndustryId());
				d++;
			}
			if (productSearchDTO.getQuery().getCategoryId() != 0) {
				query.setParameter(d, productSearchDTO.getQuery().getCategoryId());
				d++;
			}
			if (productSearchDTO.getQuery().getKeyword() != null
					&& !productSearchDTO.getQuery().getKeyword().isEmpty()) {
				query.setParameter(d, productSearchDTO.getQuery().getKeyword());
				d++;
			}

			List<Product> lproduct = (List<Product>) query.getResultList();
			int limit = productSearchDTO.getLimit();
			int offset = productSearchDTO.getOffset() + 1;
			if (productSearchDTO.getQuery().getMinPrice() != 0 || productSearchDTO.getQuery().getMaxPrice() != 0) {
				int maxPrice;
				int minPrice = productSearchDTO.getQuery().getMinPrice();
				if (productSearchDTO.getQuery().getMaxPrice() == 0)
					maxPrice = 999999999;
				else
					maxPrice = productSearchDTO.getQuery().getMaxPrice();
				
				d = 0;
				for (Product pro : lproduct) {
					float percent = discountRepositoryCustomImpl.findLastedDiscount(pro.getId());
					if (pro.getPrice() * (1 - percent) < maxPrice && pro.getPrice() * (1 - percent) > minPrice) {
						d++;
						if (d - offset >= limit && limit != 0)
							break;
						if (d >= offset)
							product.add(pro);
					}
				}
			} else {
				d = 0;
				for (Product pro : lproduct) {
					d++;
					if (d - offset >= limit && limit != 0) break;
					if (d>=offset) product.add(pro);
				}
			}
			try {
				return product;
			} catch (NoResultException e) {
				return null;
			}
		}
		else {
			try {
				return (List<Product>) entityManager.createQuery(sql).setFirstResult(productSearchDTO.getOffset()).setMaxResults((productSearchDTO.getLimit())).getResultList();
			}catch(Exception e){
				return null;
			}
		}
	}

	public void updateProduct(ProductUpdateDTO productUpdateDTO) {
		Product pro = productRepository.findById(productUpdateDTO.getId());
		if (productUpdateDTO.getActive() != null)
			pro.setActive(productUpdateDTO.getActive());
		if (productUpdateDTO.getAmount() != null)
			pro.setAmount(Integer.parseInt(productUpdateDTO.getAmount()));
		if (productUpdateDTO.getBranchId() != 0)
			pro.setBranchId(productUpdateDTO.getBranchId());
		if (productUpdateDTO.getBrandId() != 0)
			pro.setBrandId(productUpdateDTO.getBrandId());
		if (productUpdateDTO.getCategoryId() != 0)
			pro.setCategoryId(productUpdateDTO.getCategoryId());
		if (productUpdateDTO.getDescription() != null)
			pro.setDescription(productUpdateDTO.getDescription());
		if (productUpdateDTO.getImages() != null)
			pro.setImages(productUpdateDTO.getImages());
		if (productUpdateDTO.getIndustryId() != 0)
			pro.setIndustryId(productUpdateDTO.getIndustryId());
		if (productUpdateDTO.getPrice() != 0)
			pro.setPrice(productUpdateDTO.getPrice());
		if (productUpdateDTO.getProductName() != null)
			pro.setProductName(productUpdateDTO.getProductName());
		if (productUpdateDTO.getLongDescription() != null)
			pro.setLongDescription(productUpdateDTO.getLongDescription());
		productRepository.save(pro);
	}
}
