package tuan.tidi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tuan.tidi.DTO.product.BranchDTO;
import tuan.tidi.DTO.product.BranchListCategoryDTO;
import tuan.tidi.DTO.product.BrandDTO;
import tuan.tidi.DTO.product.CategoryDTO;
import tuan.tidi.DTO.product.IndustryDTO;
import tuan.tidi.DTO.product.IndustryListBranchDTO;
import tuan.tidi.DTO.product.ListProductDTO;
import tuan.tidi.DTO.product.ProductDTO;
import tuan.tidi.DTO.product.ProductSearchDTO;
import tuan.tidi.entity.Branch;
import tuan.tidi.entity.Brand;
import tuan.tidi.entity.Category;
import tuan.tidi.entity.Industry;
import tuan.tidi.entity.Product;
import tuan.tidi.repository.product.BranchRepository;
import tuan.tidi.repository.product.BranchRepositoryCustomImpl;
import tuan.tidi.repository.product.BrandRepository;
import tuan.tidi.repository.product.CategoryRepository;
import tuan.tidi.repository.product.CategoryRepositoryCustomImpl;
import tuan.tidi.repository.product.DiscountRepositoryCustomImpl;
import tuan.tidi.repository.product.IndustryRepository;
import tuan.tidi.repository.product.ProductRepository;
import tuan.tidi.repository.product.ProductRepositoryCustomImpl;
import tuan.tidi.api.API;

@Controller
public class ProductController {

	@Autowired
	private ProductRepositoryCustomImpl productRepositoryCustomImpl;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private IndustryRepository industryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BranchRepositoryCustomImpl branchRepositoryCustomImpl;

	@Autowired
	private CategoryRepositoryCustomImpl categoryRepositoryCustomImpl;

	@Autowired
	private DiscountRepositoryCustomImpl discountRepositoryCustomImpl;



	// Load all brand
	@CrossOrigin(origins = "*")
	@GetMapping(API.BRANDS)
	@ResponseBody
	public List<BrandDTO> loadBrand() {
		List<BrandDTO> lBrandDTO = new ArrayList<BrandDTO>();
		Iterable<Brand> iBrand = brandRepository.findAll();
		for (Brand bra : iBrand) {
			if (bra.getActive().equals("FALSE"))
				continue;
			lBrandDTO.add(new BrandDTO(bra.getId(), bra.getBrandName()));
		}
		return lBrandDTO;
	}

	// Load All industry
	@CrossOrigin(origins = "*")
	@GetMapping(API.INDUSTRIES)
	@ResponseBody
	public List<IndustryListBranchDTO> loadIndustry() {
		List<IndustryListBranchDTO> lIndustryDTO = new ArrayList<IndustryListBranchDTO>();
		Iterable<Industry> iIndustry = industryRepository.findAll();
		for (Industry ind : iIndustry) {
			if (ind.getActive().equals("FALSE"))
				continue;
			List<BranchListCategoryDTO> lBranchDTO = new ArrayList<BranchListCategoryDTO>();
			List<Branch> lBranch = branchRepositoryCustomImpl.findByIndustryIdActive(ind.getId());
			for (Branch bra : lBranch) {
				List<CategoryDTO> lCategoryDTO = new ArrayList<CategoryDTO>();
				List<Category> lCategory = categoryRepositoryCustomImpl.findByBranchIdActive(bra.getId());
				for (Category cat : lCategory) {
					lCategoryDTO.add(new CategoryDTO(cat.getId(), cat.getCategoryName()));

				}
				lBranchDTO.add(new BranchListCategoryDTO(bra.getId(), bra.getBranchName(), lCategoryDTO));
			}
			lIndustryDTO.add(new IndustryListBranchDTO(ind.getId(), ind.getIndustryName(), lBranchDTO));
		}
		return lIndustryDTO;
	}

	// tranfer product to productDTO
	public ProductDTO tranferDTO(Product product) {

		ProductDTO productDTO = new ProductDTO(product);
		productDTO.setBrand(
				new BrandDTO(product.getBrandId(), brandRepository.findById(product.getBrandId()).getBrandName()));
		productDTO.setBranch(
				new BranchDTO(product.getBranchId(), branchRepository.findById(product.getBranchId()).getBranchName()));
		productDTO.setCategory(new CategoryDTO(product.getCategoryId(),
				categoryRepository.findById(product.getCategoryId()).getCategoryName()));
		productDTO.setIndustry(new IndustryDTO(product.getIndustryId(),
				industryRepository.findById(product.getIndustryId()).getIndustryName()));
		productDTO.setDiscPercent(discountRepositoryCustomImpl.findLastedDiscount(product.getId()));
		return productDTO;
	}

	// load product detail
	@CrossOrigin(origins = "*")
	@PostMapping(API.ONE)
	@ResponseBody
	public ProductDTO loadProduct(@RequestBody ProductDTO productDTO) {
		Product product = productRepository.findById(productDTO.getId());
		return tranferDTO(product);
	}

	// search product
	@CrossOrigin(origins = "*")
	@PostMapping(API.ALL)
	@ResponseBody
	public ListProductDTO searchProduct(@RequestBody ProductSearchDTO productSearchDTO) {
		ListProductDTO listProductDTO = new ListProductDTO();
		List<Product> product = productRepositoryCustomImpl.search(productSearchDTO);
		List<ProductDTO> lProductDTO = new ArrayList<ProductDTO>();
		for (Product pro : product) {
			lProductDTO.add(tranferDTO(pro));
		}
		listProductDTO.setProducts(lProductDTO);
		productSearchDTO.setLimit(Integer.MAX_VALUE);
		productSearchDTO.setOffset(0);
		listProductDTO.setTotalItems(productRepositoryCustomImpl.search(productSearchDTO).size());
		return listProductDTO;
	}
}
