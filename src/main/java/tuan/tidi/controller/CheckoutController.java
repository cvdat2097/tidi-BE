package tuan.tidi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.checkout.CartDetailDTO;
import tuan.tidi.DTO.checkout.CheckoutDTO;
import tuan.tidi.DTO.checkout.ListOrdersDTO;
import tuan.tidi.DTO.checkout.ListProductDTO;
import tuan.tidi.DTO.checkout.OrderDTO;
import tuan.tidi.DTO.checkout.OrderHistoryDTO;
import tuan.tidi.DTO.checkout.OrderIdDTO;
import tuan.tidi.DTO.checkout.OrderStatusDTO;
import tuan.tidi.DTO.checkout.OrdersDTO;
import tuan.tidi.DTO.product.BranchDTO;
import tuan.tidi.DTO.product.BrandDTO;
import tuan.tidi.DTO.product.CategoryDTO;
import tuan.tidi.DTO.product.IndustryDTO;
import tuan.tidi.DTO.product.ProductDTO;
import tuan.tidi.api.API;
import tuan.tidi.entity.Accounts;
import tuan.tidi.entity.Cart;
import tuan.tidi.entity.Coupon;
import tuan.tidi.entity.Orders;
import tuan.tidi.entity.OrdersDetail;
import tuan.tidi.entity.OrdersHistory;
import tuan.tidi.entity.Product;
import tuan.tidi.jwt.JWTService;
import tuan.tidi.repository.account.AccountsRepository;
import tuan.tidi.repository.checkout.CartRepository;
import tuan.tidi.repository.checkout.CartRepositoryCustomImpl;
import tuan.tidi.repository.checkout.OrderDetailRepository;
import tuan.tidi.repository.checkout.OrderRepository;
import tuan.tidi.repository.checkout.OrdersHistoryRepository;
import tuan.tidi.repository.product.BranchRepository;
import tuan.tidi.repository.product.BrandRepository;
import tuan.tidi.repository.product.CategoryRepository;
import tuan.tidi.repository.product.CouponRepository;
import tuan.tidi.repository.product.DiscountRepositoryCustomImpl;
import tuan.tidi.repository.product.IndustryRepository;
import tuan.tidi.repository.product.ProductRepository;
import tuan.tidi.service.CheckJWT;
import tuan.tidi.service.FormatDate;

@Controller
public class CheckoutController {
	
	@Autowired
	private CheckJWT checkJwt;
	
	@Autowired 
	private JWTService jwtService;
	
	@Autowired 
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private IndustryRepository industryRepository;
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private CartRepositoryCustomImpl cartRepositoryCustomImpl;
	
	@Autowired
	private DiscountRepositoryCustomImpl discountRepositoryCustomImpl;
	
	@Autowired
	private OrdersHistoryRepository ordersHistoryRepository;
	
	//Insert product
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTITEM)
	@ResponseBody
	public StatusDTO insertItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE")
			return statusDTO;
		
		//Check accountID
		String username = jwtService.getUsernameFromToken(authToken);
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		if (accounts == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//Check ProductId
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Product has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//Check amount
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Amount must be greater than 0!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		Cart cart;
		if (cartRepositoryCustomImpl.findAccountProduct(accounts.getId(), cartDetailDTO.getProductId()) == null) {
			cart = new Cart(accounts.getId(), cartDetailDTO.getProductId(), cartDetailDTO.getAmount());
		}else {
			cart = cartRepositoryCustomImpl.findAccountProduct(accounts.getId(), cartDetailDTO.getProductId());
			cart.setAmount(cart.getAmount() + cartDetailDTO.getAmount());
		}
		cartRepository.save(cart);
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
		productDTO.setDiscPercent(discountRepositoryCustomImpl.findLastedDiscount(product.getId()));
		return productDTO;
	}
	
	//get all item in cart
	@CrossOrigin(origins = "*")
	@GetMapping(API.GETITEM)
	@ResponseBody
	public ListProductDTO loadItem(HttpServletRequest httpServletRequest){
		ListProductDTO listProductDTO = new ListProductDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			listProductDTO.setStatus(statusDTO);
			return listProductDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
		List<Cart> cart = cartRepository.findByAccountsId(account.getId());
		if(cart == null || cart.isEmpty()) {
			statusDTO.setMessage("Your cart is empty! Continue shopping :D");
			statusDTO.setStatus("TRUE");
			listProductDTO.setStatus(statusDTO);
			return listProductDTO;
		}
		List<ProductDTO> productDTO = new ArrayList<ProductDTO>();
		for (Cart car : cart) {
			Product pro = productRepository.findById(car.getProductId());
			ProductDTO proDTO = tranferDTO(pro);
			proDTO.setAmount(car.getAmount());
			productDTO.add(proDTO);
		}
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listProductDTO.setStatus(statusDTO);
		listProductDTO.setProducts(productDTO);
		return listProductDTO;
	}
	
	//update item
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEITEM)
	@ResponseBody
	public StatusDTO updateItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			return statusDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
				
		//check accountId
		if (account == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//Check ProductId
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Product has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//Check amount
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Amount must be greater than 0!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		if (cartRepositoryCustomImpl.findAccountProduct(account.getId(), cartDetailDTO.getProductId()) == null) {
			Cart cart = new Cart();
			cart.setAccountsId(account.getId());
			cart.setAmount(cartDetailDTO.getAmount());
			cart.setProductId(cartDetailDTO.getProductId());
			cartRepository.save(cart);
		}else {
			Cart cart = cartRepositoryCustomImpl.findAccountProduct(account.getId(), cartDetailDTO.getProductId());
			cart.setAmount(cartDetailDTO.getAmount());
			cartRepository.save(cart);
		}
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//Delete item
	@CrossOrigin(origins = "*")
	@PostMapping(API.DELETEITEM)
	@ResponseBody
	public StatusDTO deleteItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			return statusDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
				
		//check accountId
		if (account == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		if (cartRepositoryCustomImpl.findAccountProduct(account.getId(), cartDetailDTO.getProductId()) != null) {
			Cart cart = cartRepositoryCustomImpl.findAccountProduct(account.getId(), cartDetailDTO.getProductId());
			cartRepository.delete(cart);
		}
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//Checkout
	@CrossOrigin(origins = "*")
	@PostMapping(API.CHECKOUT)
	@ResponseBody
	public StatusDTO checkout(@RequestBody CheckoutDTO checkoutDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") return statusDTO;
		
		int accountId = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken)).getId();
		//check cart
		List<Cart> cart = cartRepository.findByAccountsId(accountId);
		if(cart == null || cart.isEmpty()) {
			statusDTO.setMessage("Your cart is empty! Continue shopping :D");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		Coupon coupon = new Coupon();
		if (checkoutDTO.getCouponCode()!=null && !checkoutDTO.getCouponCode().isEmpty())
			coupon = couponRepository.findByCouponCodeLike(checkoutDTO.getCouponCode());
		else {
			coupon.setPercent(0);
			coupon.setThreshold(0);
			coupon.setMoney(0);
		}
			
		//check coupon
		if (coupon == null) {
			statusDTO.setMessage("Coupon is wrong!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (coupon.getActive() == "FALSE") {
			statusDTO.setMessage("Coupon is expired!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		if (coupon.getAmount() == 0) {
			statusDTO.setMessage("Coupon has run out!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		ListProductDTO listProductDTO = loadItem(httpServletRequest);
		int total = 0;
		//check products in cart
		statusDTO.setMessage("");
		for (ProductDTO pro : listProductDTO.getProducts()) {
			Product product = productRepository.findById(pro.getId());
			if (product.getActive() == "FALSE") statusDTO.setMessage(statusDTO.getMessage() + "ProductId " +pro.getId()+ " has been block!!!\n");
			if (product.getAmount() == 0) statusDTO.setMessage(statusDTO.getMessage() + "ProductId " +pro.getId()+ " has run out!!!\n");
			if (product.getAmount() < pro.getAmount()) statusDTO.setMessage(statusDTO.getMessage() + "ProductId " +pro.getId()+ " is not enough!!!\n");
			total += pro.getPrice() * pro.getAmount() * (float)(1 - pro.getDiscPercent());
		}
		if (statusDTO.getMessage() != "") {
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		if (total >= coupon.getThreshold())
			total = (int)(total * (1-coupon.getPercent()) - coupon.getMoney());
		else {
			statusDTO.setMessage("Total < Threshold!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}
		
		//subtract coupon amount
		if (checkoutDTO.getCouponCode() != null && !checkoutDTO.getCouponCode().isEmpty())
		if (coupon.getAmount() != -1)
		coupon.setAmount(coupon.getAmount()-1);
		
		//subtract product amount
		for (ProductDTO pro : listProductDTO.getProducts()) {
			Product product = productRepository.findById(pro.getId());
			product.setAmount(product.getAmount() - 1);
			productRepository.save(product);
		}
		
		int orderId = orderRepository.getMaxId()+1;
		//insert order
		Orders order = new Orders();
		order.setAccountsId(accountId);
		order.setActive("TRUE");
		order.setAddress(checkoutDTO.getAddress());
		order.setCouponId(coupon.getId());
		order.setEmail(checkoutDTO.getEmail());
		order.setFullName(checkoutDTO.getFullName());
		order.setNote("Giao hang du kien: " + FormatDate.formatDate(new Date()));
		order.setPhone(checkoutDTO.getPhone());
		order.setStatus("CHECKED");
		order.setTotal(total);
		order.setId(orderId);
		orderRepository.save(order);
		
		//insert orderDetails
		for (ProductDTO pro : listProductDTO.getProducts()) {
			OrdersDetail ordersDetail = new OrdersDetail();
			Product product = productRepository.findById(pro.getId());
			ordersDetail.setActive("TRUE");
			ordersDetail.setAmount(pro.getAmount());
			ordersDetail.setFinalPrice( (int) (pro.getPrice()*pro.getDiscPercent()));
			ordersDetail.setOrdersId(orderId);
			ordersDetail.setOriginalPrice(pro.getPrice());
			ordersDetail.setProductId(pro.getId());
			orderDetailRepository.save(ordersDetail);
		}
		
		//insert orderHistory
		OrdersHistory ordershistory = new OrdersHistory();
		ordershistory.setActive("TRUE");
		ordershistory.setDateTime(new Date());
		ordershistory.setOrderId(orderId);
		ordershistory.setStatus("CHECKED");
		ordersHistoryRepository.save(ordershistory);
		
		//delete cart
		cartRepository.deleteAll(cartRepository.findByAccountsId(accountId));
		
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}
	
	//get all orders
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETORDERS)
	@ResponseBody
	public ListOrdersDTO loadOrders(HttpServletRequest httpServletRequest) {
		ListOrdersDTO listOrdersDTO = new ListOrdersDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			listOrdersDTO.setStatus(statusDTO);
			return listOrdersDTO;
		}
		
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
		List<Orders> orders = orderRepository.findByAccountsId(account.getId());
		if (orders == null) {
			statusDTO.setMessage("You dont have any orders! Continue shopping :D");
			statusDTO.setStatus("TRUE");
			return listOrdersDTO;
		}
		List<OrdersDTO> lordersDTO = new ArrayList<OrdersDTO>();
		for(Orders ord : orders) {
			OrdersDTO ordersDTO = new OrdersDTO(ord);
			List<OrdersHistory> ordersHistory = ordersHistoryRepository.findByOrderId(ord.getId());
			for(OrdersHistory orh : ordersHistory) {
				 if (ordersDTO.getDate() == null) {
					 ordersDTO.setDate(FormatDate.formatDateTime(orh.getDateTime()));
					 ordersDTO.setStatus(orh.getStatus());
				 }else {
					 if (orh.getDateTime().compareTo(FormatDate.parseDateTime(ordersDTO.getDate())) > 0) {
						 ordersDTO.setDate(FormatDate.formatDateTime(orh.getDateTime()));
						 ordersDTO.setStatus(orh.getStatus());
					 }
				 }
			}
			lordersDTO.add(ordersDTO);
		}
		listOrdersDTO.setOrders(lordersDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listOrdersDTO.setStatus(statusDTO);
		return listOrdersDTO;
	}
	
	//get one orders
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETORDER)
	@ResponseBody
	public OrderStatusDTO getOneOrder(@RequestBody OrderIdDTO orderIdDTO,HttpServletRequest httpServletRequest) {
		OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus() == "FALSE") {
			orderStatusDTO.setStatus(statusDTO);
			return orderStatusDTO;
		}
		Orders order = orderRepository.findById(orderIdDTO.getOrderId());
		//check orderId == token
		if (order.getAccountsId() != accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken)).getId()) {
			statusDTO.setMessage("OrderId is not yours");
			statusDTO.setStatus("FAlSE");
			orderStatusDTO.setStatus(statusDTO);
			return orderStatusDTO;
		}
		OrderDTO orderDTO = new OrderDTO(order);
		
		//add products
		List<OrdersDetail> ordersDetail = orderDetailRepository.findByOrdersId(orderIdDTO.getOrderId());
		List<ProductDTO> lproductDTO = new ArrayList<ProductDTO>();
		for (OrdersDetail ord : ordersDetail) {
			Product product = productRepository.findById(ord.getProductId());
			ProductDTO productDTO = tranferDTO(product);
			productDTO.setAmount(ord.getAmount());
			productDTO.setDiscPercent(1-((float)ord.getFinalPrice()/(float)ord.getOriginalPrice()));
			productDTO.setPrice(ord.getOriginalPrice());
			lproductDTO.add(productDTO);
		}
		orderDTO.setProducts(lproductDTO);
		
		//add history
		List<OrdersHistory> ordersHistory = ordersHistoryRepository.findByOrderId(orderIdDTO.getOrderId());
		List<OrderHistoryDTO> lorderHistoryDTO = new ArrayList<OrderHistoryDTO>();
		for (OrdersHistory ord : ordersHistory) {
			lorderHistoryDTO.add(new OrderHistoryDTO(ord));
		}
		orderDTO.setHistory(lorderHistoryDTO);
		
		orderStatusDTO.setOrder(orderDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		orderStatusDTO.setStatus(statusDTO);
		return orderStatusDTO;
	}
}
