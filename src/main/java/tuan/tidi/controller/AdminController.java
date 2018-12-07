package tuan.tidi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.DTO.admin.AccountsDTO;
import tuan.tidi.DTO.admin.BranchDTO;
import tuan.tidi.DTO.admin.BranchIndustryDTO;
import tuan.tidi.DTO.admin.BrandDTO;
import tuan.tidi.DTO.admin.CampaignDTO;
import tuan.tidi.DTO.admin.CampaignShortDTO;
import tuan.tidi.DTO.admin.CategoryBranchIndustryDTO;
import tuan.tidi.DTO.admin.CategoryDTO;
import tuan.tidi.DTO.admin.CouponDTO;
import tuan.tidi.DTO.admin.CouponListProductDTO;
import tuan.tidi.DTO.admin.DiscountListProductDTO;
import tuan.tidi.DTO.admin.DiscountDTO;
import tuan.tidi.DTO.admin.IndustryDTO;
import tuan.tidi.DTO.admin.ListAccountsDTO;
import tuan.tidi.DTO.admin.ListBranchIndustryDTO;
import tuan.tidi.DTO.admin.ListBrandDTO;
import tuan.tidi.DTO.admin.ListCampaignDTO;
import tuan.tidi.DTO.admin.ListCategoryBranchIndustryDTO;
import tuan.tidi.DTO.admin.ListCouponDTO;
import tuan.tidi.DTO.admin.ListDiscountDTO;
import tuan.tidi.DTO.admin.ListIndustryDTO;
import tuan.tidi.DTO.admin.ListProductDTO;
import tuan.tidi.DTO.admin.ProductDTO;
import tuan.tidi.DTO.admin.ProductUpdateDTO;
import tuan.tidi.DTO.product.ProductSearchDTO;
import tuan.tidi.api.API;
import tuan.tidi.entity.Accounts;
import tuan.tidi.entity.Branch;
import tuan.tidi.entity.Brand;
import tuan.tidi.entity.Campaign;
import tuan.tidi.entity.Category;
import tuan.tidi.entity.Coupon;
import tuan.tidi.entity.Discount;
import tuan.tidi.entity.Industry;
import tuan.tidi.entity.Product;
import tuan.tidi.entity.Verification;
import tuan.tidi.repository.account.AccountsRepository;
import tuan.tidi.repository.account.AccountsRepositoryCustomImpl;
import tuan.tidi.repository.account.VerificationRepository;
import tuan.tidi.repository.product.BranchRepository;
import tuan.tidi.repository.product.BranchRepositoryCustomImpl;
import tuan.tidi.repository.product.BrandRepository;
import tuan.tidi.repository.product.BrandRepositoryCustomImpl;
import tuan.tidi.repository.product.CampaignRepository;
import tuan.tidi.repository.product.CampaignRepositoryCustomImpl;
import tuan.tidi.repository.product.CategoryRepository;
import tuan.tidi.repository.product.CategoryRepositoryCustomImpl;
import tuan.tidi.repository.product.CouponRepository;
import tuan.tidi.repository.product.CouponRepositoryCustomImpl;
import tuan.tidi.repository.product.DiscountRepository;
import tuan.tidi.repository.product.DiscountRepositoryCustomImpl;
import tuan.tidi.repository.product.IndustryRepository;
import tuan.tidi.repository.product.IndustryRepositoryCustomImpl;
import tuan.tidi.repository.product.ProductRepository;
import tuan.tidi.repository.product.ProductRepositoryCustomImpl;
import tuan.tidi.service.CheckJWT;
import tuan.tidi.service.FormatDate;
import tuan.tidi.service.HashPassword;
import tuan.tidi.service.RandomVerificationCode;

@Controller
public class AdminController {

	@Autowired
	private CheckJWT checkJwt;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductRepositoryCustomImpl productRepositoryCustomImpl;

	@Autowired
	private IndustryRepository industryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private DiscountRepository discountRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private AccountsRepository accountsRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private VerificationRepository verificationRepository;

	@Autowired
	private CouponRepositoryCustomImpl couponRepositoryCustomImpl;

	@Autowired
	private AccountsRepositoryCustomImpl accountsRepositoryCustomImpl;

	@Autowired
	private BrandRepositoryCustomImpl brandRepositoryCustomImpl;

	@Autowired
	private CategoryRepositoryCustomImpl categoryRepositoryCustomImpl;

	@Autowired
	private HashPassword hashPassword;

	@Autowired
	private IndustryRepositoryCustomImpl industryRepositoryCustomImpl;

	@Autowired
	private BranchRepositoryCustomImpl branchRepositoryCustomImpl;

	@Autowired
	private CampaignRepositoryCustomImpl campaignRepositoryCustomImpl;

	@Autowired
	private DiscountRepositoryCustomImpl discountRepositoryCustomImpl;

	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTPRODUCT)
	@ResponseBody
	public StatusDTO insertProduct(@RequestBody Product product, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		product.setActive("TRUE");

		statusDTO = productRepositoryCustomImpl.insertProduct(product);
		return statusDTO;
	}

	// search account
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETACCOUNTS)
	@ResponseBody
	public ListAccountsDTO searchAccounts(@RequestBody SearchDTO accountSearchDTO,
			HttpServletRequest httpServletRequest) {
		ListAccountsDTO listAccountsDTO = new ListAccountsDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listAccountsDTO.setStatus(statusDTO);
			return listAccountsDTO;
		}
		List<Accounts> accounts = accountsRepositoryCustomImpl.search(accountSearchDTO);
		List<AccountsDTO> accountsDTO = new ArrayList<AccountsDTO>();
		for (Accounts acc : accounts) {
			accountsDTO.add(new AccountsDTO(acc));
		}
		listAccountsDTO.setAccounts(accountsDTO);
		accountSearchDTO.setLimit(0);
		accountSearchDTO.setOffset(0);
		listAccountsDTO.setTotalItems(accountsRepositoryCustomImpl.search(accountSearchDTO).size());
		statusDTO.setMessage("Successful");
		statusDTO.setStatus("TRUE");
		listAccountsDTO.setStatus(statusDTO);
		return listAccountsDTO;
	}

	@CrossOrigin(origins = "*")
	@PostMapping(API.CREATEACCOUNTS)
	@ResponseBody
	public StatusDTO createAccounts(@RequestBody Accounts accounts, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		// check username
		if (accountsRepository.findByUsernameLike(accounts.getUsername()) != null) {
			statusDTO.setMessage("Username has been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (accounts.getUsername() == null || accounts.getUsername().isEmpty()) {
			statusDTO.setMessage("Username is null!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// check email
		if (accounts.getEmail() == null || accounts.getEmail().isEmpty()) {
			statusDTO.setMessage("Email is null!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (accountsRepository.findByEmailLike(accounts.getEmail()) != null) {
			statusDTO.setMessage("Email has been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// save
		accounts.setPassword(hashPassword.hash(accounts.getPassword()));
		accounts.setIsVerified("FALSE");
		accounts.setActive("TRUE");
		accountsRepository.save(accounts);

		// create new code
		Accounts account = accountsRepository.findByUsernameLike(accounts.getUsername());
		RandomVerificationCode rand = new RandomVerificationCode();
		Verification verification = new Verification(account.getId(),
				rand.randomCode() + Integer.toString(account.getId()), "EMAIL", "TRUE");
		verificationRepository.save(verification);

		/*
		 * SendEmail sendEmail = new SendEmail(); try{ sendEmail.sendEmail();
		 * }catch(Exception e) {
		 * 
		 * }
		 */
		statusDTO.setStatus("TRUE");
		statusDTO.setMessage("Successful");
		return statusDTO;
	}

	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEACCOUNTS)
	@ResponseBody
	public StatusDTO updateAccounts(@RequestBody AccountsDTO accountsDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		// check ID
		if (accountsRepository.findById(accountsDTO.getId()) == null) {
			statusDTO.setMessage("AccountID has not been existed!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// check username

		if (accountsDTO.getUsername() != null
				&& accountsRepository.findByUsernameLike(accountsDTO.getUsername()) != null) {
			statusDTO.setMessage("Username has been existed!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// check Email
		if (accountsDTO.getEmail() != null && accountsRepository.findByEmailLike(accountsDTO.getEmail()) != null) {
			statusDTO.setMessage("Email has been existed!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		accountsRepositoryCustomImpl.adminUpdate(accountsDTO.getId(), accountsDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update product
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEPRODUCT)
	@ResponseBody
	public StatusDTO updateProduct(@RequestBody ProductUpdateDTO productUpdateDTO,
			HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		if (productRepository.findById(productUpdateDTO.getId()) == null) {
			statusDTO.setMessage("ProductId has not been existed!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		productRepositoryCustomImpl.updateProduct(productUpdateDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// delete product
	@CrossOrigin(origins = "*")
	@PostMapping(API.DELETEPRODUCT)
	@ResponseBody
	public StatusDTO deleteProduct(@RequestBody ProductDTO productDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;

		Product product = productRepository.findById(productDTO.getId());
		if (product == null) {
			statusDTO.setMessage("ProductId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		product.setActive("FALSE");
		productRepository.save(product);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

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
		productDTO.setDiscount(discountRepository.findByProductIdLike(product.getId()));
		List<Coupon> coupon = couponRepositoryCustomImpl.findByProductId(product.getId());
		List<CouponDTO> lcouponDTO = new ArrayList<CouponDTO>();
		for (Coupon cou : coupon) {
			CouponDTO couponDTO = new CouponDTO(cou);
			couponDTO.setCampaign(new CampaignShortDTO(campaignRepository.findById(cou.getCampaignId())));
			lcouponDTO.add(couponDTO);
		}
		productDTO.setCoupon(lcouponDTO);
		return productDTO;
	}

	// get all product
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETALLPRODUCT)
	@ResponseBody
	public ListProductDTO searchProduct(@RequestBody ProductSearchDTO productSearchDTO,
			HttpServletRequest httpServletRequest) {
		ListProductDTO listProductDTO = new ListProductDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listProductDTO.setStatus(statusDTO);
			return listProductDTO;
		}

		List<Product> product = productRepositoryCustomImpl.search(productSearchDTO);
		List<ProductDTO> lProductDTO = new ArrayList<ProductDTO>();
		for (Product pro : product) {
			lProductDTO.add(tranferDTO(pro));
		}
		productSearchDTO.setLimit(100);
		productSearchDTO.setOffset(0);
		listProductDTO.setTotalItems(productRepositoryCustomImpl.search(productSearchDTO).size());
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listProductDTO.setStatus(statusDTO);
		listProductDTO.setProducts(lProductDTO);
		return listProductDTO;
	}

	// get one product
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETONEPRODUCT)
	@ResponseBody
	public ProductDTO loadProduct(@RequestBody ProductDTO productDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return null;
		Product product = productRepository.findById(productDTO.getId());
		return tranferDTO(product);
	}

	// get all brand
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETBRAND)
	@ResponseBody
	public ListBrandDTO loadBrand(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		ListBrandDTO listBrandDTO = new ListBrandDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listBrandDTO.setStatus(statusDTO);
			return listBrandDTO;
		}
		listBrandDTO.setBrands(brandRepositoryCustomImpl.search(searchDTO));
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listBrandDTO.setStatus(statusDTO);
		searchDTO.setLimit(0);
		searchDTO.setOffset(0);
		listBrandDTO.setTotalItems(brandRepositoryCustomImpl.search(searchDTO).size());
		return listBrandDTO;
	}

	// insert brand
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTBRAND)
	@ResponseBody
	public StatusDTO insertBrand(@RequestBody Brand brand, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		brand.setActive("TRUE");
		brandRepository.save(brand);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update brand
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEBRAND)
	@ResponseBody
	public StatusDTO updateBrand(@RequestBody Brand brand, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		if (brandRepository.findById(brand.getId()) == null) {
			statusDTO.setMessage("BrandId has not been existed!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		brandRepositoryCustomImpl.updateBrand(brand);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// get all industries
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETINDUSTRY)
	@ResponseBody
	public ListIndustryDTO loadIndustry(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		ListIndustryDTO listIndustryDTO = new ListIndustryDTO();
		if (statusDTO.getStatus() == "FALSE") {
			listIndustryDTO.setStatus(statusDTO);
			return listIndustryDTO;
		}
		listIndustryDTO.setIndustries(industryRepositoryCustomImpl.search(searchDTO));
		searchDTO.setLimit(0);
		searchDTO.setOffset(0);
		listIndustryDTO.setTotalItems(industryRepositoryCustomImpl.search(searchDTO).size());
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listIndustryDTO.setStatus(statusDTO);
		return listIndustryDTO;
	}

	// insert industry
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTINDUSTRY)
	@ResponseBody
	public StatusDTO insertIndustry(@RequestBody Industry industry, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		industry.setActive("TRUE");
		industryRepository.save(industry);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update industry
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEINDUSTRY)
	@ResponseBody
	public StatusDTO updateIndustry(@RequestBody Industry industry, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		if (industryRepository.findById(industry.getId()) == null) {
			statusDTO.setMessage("IndustryId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		industryRepositoryCustomImpl.updateIndustry(industry);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// get all branches
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETBRANCH)
	@ResponseBody
	public ListBranchIndustryDTO loadBranch(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		ListBranchIndustryDTO listBranchIndustryDTO = new ListBranchIndustryDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listBranchIndustryDTO.setStatus(statusDTO);
			return listBranchIndustryDTO;
		}
		List<BranchIndustryDTO> lbranchIndustryDTO = new ArrayList<BranchIndustryDTO>();
		List<Branch> branch = branchRepositoryCustomImpl.search(searchDTO);
		for (Branch bra : branch) {
			BranchIndustryDTO branchIndustryDTO = new BranchIndustryDTO();
			branchIndustryDTO.setBranchName(bra.getBranchName());
			branchIndustryDTO.setId(bra.getId());
			branchIndustryDTO.setIndustry(new IndustryDTO(bra.getIndustryId(),
					industryRepository.findById(bra.getIndustryId()).getIndustryName()));
			lbranchIndustryDTO.add(branchIndustryDTO);
		}
		listBranchIndustryDTO.setBranches(lbranchIndustryDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listBranchIndustryDTO.setStatus(statusDTO);
		searchDTO.setLimit(0);
		searchDTO.setOffset(0);
		listBranchIndustryDTO.setTotalItems(branchRepositoryCustomImpl.search(searchDTO).size());
		return listBranchIndustryDTO;
	}

	// insert branch
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTBRANCH)
	@ResponseBody
	public StatusDTO insertBranch(@RequestBody Branch branch, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		branch.setActive("TRUE");
		branchRepository.save(branch);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update Branch
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEBRANCH)
	@ResponseBody
	public StatusDTO updateBranch(@RequestBody Branch branch, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		if (branchRepository.findById(branch.getId()) == null) {
			statusDTO.setMessage("Branch has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		branchRepositoryCustomImpl.updateBranch(branch);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// Get all categories
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETCATEGORY)
	@ResponseBody
	public ListCategoryBranchIndustryDTO loadCategory(@RequestBody SearchDTO searchDTO,
			HttpServletRequest httpServletRequest) {
		ListCategoryBranchIndustryDTO listCategoryBranchIndustryDTO = new ListCategoryBranchIndustryDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listCategoryBranchIndustryDTO.setStatus(statusDTO);
			return listCategoryBranchIndustryDTO;
		}
		List<CategoryBranchIndustryDTO> lcategoryBranchIndustryDTO = new ArrayList<CategoryBranchIndustryDTO>();
		List<Category> category = categoryRepositoryCustomImpl.search(searchDTO);
		for (Category cat : category) {
			CategoryBranchIndustryDTO categoryBranchIndustryDTO = new CategoryBranchIndustryDTO();
			categoryBranchIndustryDTO.setCategoryName(cat.getCategoryName());
			categoryBranchIndustryDTO.setId(cat.getId());
			BranchIndustryDTO branchIndustryDTO = new BranchIndustryDTO();
			Branch branch = branchRepository.findById(cat.getBranchId());
			branchIndustryDTO.setId(cat.getBranchId());
			branchIndustryDTO.setBranchName(branch.getBranchName());
			branchIndustryDTO.setIndustry(new IndustryDTO(branch.getIndustryId(),
					industryRepository.findById(branch.getIndustryId()).getIndustryName()));
			categoryBranchIndustryDTO.setBranch(branchIndustryDTO);
			lcategoryBranchIndustryDTO.add(categoryBranchIndustryDTO);
		}
		listCategoryBranchIndustryDTO.setCategories(lcategoryBranchIndustryDTO);
		searchDTO.setLimit(0);
		searchDTO.setOffset(0);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listCategoryBranchIndustryDTO.setStatus(statusDTO);
		listCategoryBranchIndustryDTO.setTotalItems(categoryRepositoryCustomImpl.search(searchDTO).size());
		return listCategoryBranchIndustryDTO;
	}

	// insert category
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTCATEGORY)
	@ResponseBody
	public StatusDTO insertCategory(@RequestBody Category category, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		category.setActive("TRUE");
		categoryRepository.save(category);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update Category
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATECATEGORY)
	@ResponseBody
	public StatusDTO updateCategory(@RequestBody Category category, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		if (branchRepository.findById(category.getId()) == null) {
			statusDTO.setMessage("Category has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		categoryRepositoryCustomImpl.updateCategory(category);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// Get all campaign
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETCAMPAIGN)
	@ResponseBody
	public ListCampaignDTO loadCampaign(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		ListCampaignDTO listCampaignDTO = new ListCampaignDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listCampaignDTO.setStatus(statusDTO);
			return listCampaignDTO;
		}
		listCampaignDTO.setCampaign(campaignRepositoryCustomImpl.search(searchDTO));
		searchDTO.setLimit(0);
		searchDTO.setOffset(0);
		listCampaignDTO.setTotalItems(campaignRepositoryCustomImpl.search(searchDTO).size());
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listCampaignDTO.setStatus(statusDTO);
		return listCampaignDTO;
	}

	// insert campaign
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTCAMPAIGN)
	@ResponseBody
	public StatusDTO insertCampaign(@RequestBody CampaignDTO campaignDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;

		// check start time and expired time
		if (FormatDate.parseDateTime(campaignDTO.getStartTime()) == null) {
			statusDTO.setMessage("StartTime is null or ERROR!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (FormatDate.parseDateTime(campaignDTO.getExpiredTime()) == null) {
			statusDTO.setMessage("ExpiredTime is null or ERROR!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (FormatDate.parseDateTime(campaignDTO.getStartTime())
				.compareTo(FormatDate.parseDateTime(campaignDTO.getExpiredTime())) > 0) {
			statusDTO.setMessage("StartTime after ExpiredTime!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		Campaign campaign = new Campaign();
		campaign.setStartTime(FormatDate.parseDateTime(campaignDTO.getStartTime()));
		campaign.setExpiredTime(FormatDate.parseDateTime(campaignDTO.getExpiredTime()));
		campaign.setCampaignName(campaignDTO.getCampaignName());
		campaign.setDescription(campaignDTO.getDescription());
		campaign.setActive("TRUE");
		campaignRepository.save(campaign);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// update Campaign
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATECAMPAIGN)
	@ResponseBody
	public StatusDTO updateCampaign(@RequestBody CampaignDTO campaignDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		//check campaignId
		if (campaignRepository.findById(campaignDTO.getId()) == null) {
			statusDTO.setMessage("Campaign has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		// check start time and expired time
		if (FormatDate.parseDateTime(campaignDTO.getExpiredTime()) != null
				|| FormatDate.parseDateTime(campaignDTO.getStartTime()) != null) {
			int checkNull = 0;
			if (FormatDate.parseDateTime(campaignDTO.getStartTime()) == null) {
				if (campaignRepository.findById(campaignDTO.getId()).getStartTime()
						.compareTo(FormatDate.parseDateTime(campaignDTO.getExpiredTime())) >= 0) {
					statusDTO.setMessage("StartTime >= ExpiredTime!!!");
					statusDTO.setStatus("FALSE");
					return statusDTO;
				}
				checkNull = 1;
			}
			if (FormatDate.parseDateTime(campaignDTO.getExpiredTime()) == null) {
				if (campaignRepository.findById(campaignDTO.getId()).getExpiredTime().compareTo(FormatDate.parseDateTime(campaignDTO.getStartTime())) < 0) {
				statusDTO.setMessage("StartTime >= ExpiredTime!!!");
				statusDTO.setStatus("FALSE");
				return statusDTO;
				}
				checkNull = 1;
			}
			if (checkNull == 0)
			if (FormatDate.parseDateTime(campaignDTO.getStartTime())
					.compareTo(FormatDate.parseDateTime(campaignDTO.getExpiredTime())) >= 0) {
				statusDTO.setMessage("StartTime >= ExpiredTime!!!");
				statusDTO.setStatus("FALSE");
				return statusDTO;
			}
		}
		campaignRepositoryCustomImpl.updateCampaign(campaignDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// get all coupon
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETCOUPON)
	@ResponseBody
	public ListCouponDTO loadCoupon(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		ListCouponDTO listCouponDTO = new ListCouponDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listCouponDTO.setStatus(statusDTO);
			return listCouponDTO;
		}
		List<Coupon> coupon = couponRepositoryCustomImpl.search(searchDTO);
		List<CouponDTO> lcouponDTO = new ArrayList<CouponDTO>();
		for (Coupon cou : coupon) {
			CouponDTO couponDTO = new CouponDTO(cou);
			Campaign campaign = campaignRepository.findById(cou.getCampaignId());
			couponDTO.setCampaign(new CampaignShortDTO(campaign));
			lcouponDTO.add(couponDTO);
		}
		listCouponDTO.setCoupons(lcouponDTO);
		searchDTO.setLimit(0);
		searchDTO.setLimit(0);
		listCouponDTO.setTotalItems(couponRepositoryCustomImpl.search(searchDTO).size());
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listCouponDTO.setStatus(statusDTO);
		return listCouponDTO;
	}

	// Insert coupon
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTCOUPON)
	@ResponseBody
	public StatusDTO insertCoupon(@RequestBody CouponListProductDTO couponListProductDTO,
			HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		// check couponCode
		if (couponRepository.findByCouponCodeLike(couponListProductDTO.getCouponCode()) != null) {
			statusDTO.setMessage("CouponCode has been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check CampaignId
		if (campaignRepository.findById(couponListProductDTO.getCampaignId()) == null) {
			statusDTO.setMessage("CampaignId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check productsId
		statusDTO.setMessage(null);
		int numberProduct = couponListProductDTO.getProductsId().size();
		for (int i = 0; i < numberProduct; i++) {
			if (productRepository.findById(couponListProductDTO.getProductsId().get(i)) == null) {
				if (statusDTO.getMessage() != null)
					statusDTO.setMessage(statusDTO.getMessage() + "ProductId "
							+ couponListProductDTO.getProductsId().get(i) + " has not been existed\n");
				else
					statusDTO.setMessage(
							"ProductId " + couponListProductDTO.getProductsId().get(i) + " has not been existed\n");
			}
		}

		couponRepositoryCustomImpl.insertCoupon(couponListProductDTO);
		if (statusDTO.getMessage() == null)
			statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// Update Coupon
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATECOUPON)
	@ResponseBody
	public StatusDTO updateCoupon(@RequestBody CouponListProductDTO couponListProductDTO,
			HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		Coupon coupon;
		// check couponId
		if ((couponRepository.findById(couponListProductDTO.getId())) == null) {
			statusDTO.setMessage("CouponId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check couponCode
		if ((coupon = couponRepository.findByCouponCodeLike(couponListProductDTO.getCouponCode())) != null) {
			if (coupon.getId() != couponListProductDTO.getId()) {
				statusDTO.setMessage("CouponCode has been existed!!!");
				statusDTO.setStatus("FALSE");
				return statusDTO;
			}
		}
		// check CampaignId
		if (campaignRepository.findById(couponListProductDTO.getCampaignId()) == null) {
			statusDTO.setMessage("CampaignId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check productsId
		statusDTO.setMessage(null);
		int numberProduct = couponListProductDTO.getProductsId().size();
		for (int i = 0; i < numberProduct; i++) {
			if (productRepository.findById(couponListProductDTO.getProductsId().get(i)) == null) {
				if (statusDTO.getMessage() != null)
					statusDTO.setMessage(statusDTO.getMessage() + "ProductId "
							+ couponListProductDTO.getProductsId().get(i) + " has not been existed\n");
				else
					statusDTO.setMessage(
							"ProductId " + couponListProductDTO.getProductsId().get(i) + " has not been existed\n");
			}
		}

		couponRepositoryCustomImpl.updateCoupon(couponListProductDTO);
		if (statusDTO.getMessage() == null)
			statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// get all discounts
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETDISCOUNT)
	@ResponseBody
	public ListDiscountDTO loadDiscount(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		ListDiscountDTO listDiscountDTO = new ListDiscountDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			listDiscountDTO.setStatus(statusDTO);
			return listDiscountDTO;
		}
		List<Discount> discount = discountRepositoryCustomImpl.search(searchDTO);
		List<DiscountDTO> ldiscountDTO = new ArrayList<DiscountDTO>();
		for (Discount dis : discount) {
			DiscountDTO discountDTO = new DiscountDTO(dis);
			ldiscountDTO.add(discountDTO);
		}
		listDiscountDTO.setDiscounts(ldiscountDTO);
		searchDTO.setLimit(0);
		searchDTO.setLimit(0);
		listDiscountDTO.setTotalItems(discountRepositoryCustomImpl.search(searchDTO).size());
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listDiscountDTO.setStatus(statusDTO);
		return listDiscountDTO;
	}

	// insert discount
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTDISCOUNT)
	@ResponseBody
	public StatusDTO insertDiscount(@RequestBody DiscountListProductDTO discountDTO,
			HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE") {
			return statusDTO;
		}
		// check start time expired time
		if (FormatDate.parseDateTime(discountDTO.getStartTime()) == null) {
			statusDTO.setMessage("StartTime is null or ERROR!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (FormatDate.parseDateTime(discountDTO.getExpiredTime()) == null) {
			statusDTO.setMessage("ExpiredTime is null or ERROR!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (FormatDate.parseDateTime(discountDTO.getStartTime())
				.compareTo(FormatDate.parseDateTime(discountDTO.getExpiredTime())) >= 0) {
			statusDTO.setMessage("StartTime after ExpiredTime!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check percent
		if (Float.parseFloat(discountDTO.getPercent()) == 0) {
			statusDTO.setMessage("Percent is null or ERROR!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		// check ProductId
		statusDTO.setMessage(null);
		int numberProduct = discountDTO.getProductsId().size();
		for (int i = 0; i < numberProduct; i++) {
			if (productRepository.findById(discountDTO.getProductsId().get(i)) == null) {
				if (statusDTO.getMessage() != null)
					statusDTO.setMessage(statusDTO.getMessage() + "ProductId " + discountDTO.getProductsId().get(i)
							+ " has not been existed\n");
				else
					statusDTO.setMessage("ProductId " + discountDTO.getProductsId().get(i) + " has not been existed\n");
			}
		}

		discountRepositoryCustomImpl.insertDiscount(discountDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// Update product
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEDISCOUNT)
	@ResponseBody
	public StatusDTO updateDiscount(@RequestBody DiscountDTO discountDTO,
			HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, true);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		
		//check discountId
		if (discountRepository.findById(discountDTO.getId()) == null) {
			statusDTO.setMessage("DiscountId has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//check start time and expired time
		if (FormatDate.parseDateTime(discountDTO.getExpiredTime()) != null
				|| FormatDate.parseDateTime(discountDTO.getStartTime()) != null) {
			int checkNull = 0;
			if (FormatDate.parseDateTime(discountDTO.getStartTime()) == null) {
				if (discountRepository.findById(discountDTO.getId()).getStartTime()
						.compareTo(FormatDate.parseDateTime(discountDTO.getExpiredTime())) >= 0) {
					statusDTO.setMessage("StartTime >= ExpiredTime!!!");
					statusDTO.setStatus("FALSE");
					return statusDTO;
				}
				checkNull = 1;
			}
			if (FormatDate.parseDateTime(discountDTO.getExpiredTime()) == null) {
				if (discountRepository.findById(discountDTO.getId()).getExpiredTime().compareTo(FormatDate.parseDateTime(discountDTO.getStartTime())) < 0) {
				statusDTO.setMessage("StartTime >= ExpiredTime!!!");
				statusDTO.setStatus("FALSE");
				return statusDTO;
				}
				checkNull = 1;
			}
			if (checkNull == 0)
			if (FormatDate.parseDateTime(discountDTO.getStartTime())
					.compareTo(FormatDate.parseDateTime(discountDTO.getExpiredTime())) >= 0) {
				statusDTO.setMessage("StartTime >= ExpiredTime!!!");
				statusDTO.setStatus("FALSE");
				return statusDTO;
			}
		}
		// check productId
		statusDTO.setMessage(null);
		if (productRepository.findById(discountDTO.getId()) == null) {
			statusDTO.setMessage("ProductId has not been existed\n");
		}

		discountRepositoryCustomImpl.updateDiscount(discountDTO);
		if (statusDTO.getMessage() == null)
			statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
}
