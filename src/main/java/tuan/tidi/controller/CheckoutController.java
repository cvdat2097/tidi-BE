package tuan.tidi.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tuan.tidi.DTO.StatusDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.DTO.checkout.CartDetailDTO;
import tuan.tidi.DTO.checkout.CheckoutDTO;
import tuan.tidi.DTO.checkout.CouponDTO;
import tuan.tidi.DTO.checkout.CouponStatusDTO;
import tuan.tidi.DTO.checkout.ListOrdersDTO;
import tuan.tidi.DTO.checkout.ListProductDTO;
import tuan.tidi.DTO.checkout.OrderDTO;
import tuan.tidi.DTO.checkout.OrderHistoryDTO;
import tuan.tidi.DTO.checkout.OrderIdDTO;
import tuan.tidi.DTO.checkout.OrderStatusDTO;
import tuan.tidi.DTO.checkout.OrdersDTO;
import tuan.tidi.DTO.checkout.ReturnCallbackDTO;
import tuan.tidi.DTO.checkout.ZPTokenDTO;
import tuan.tidi.DTO.checkout.ZaloPayCallback;
import tuan.tidi.DTO.checkout.ZaloPayStatusDTO;
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
import tuan.tidi.hmacutil.HMACUtil;
import tuan.tidi.jwt.JWTService;
import tuan.tidi.repository.account.AccountsRepository;
import tuan.tidi.repository.checkout.CartRepository;
import tuan.tidi.repository.checkout.CartRepositoryCustomImpl;
import tuan.tidi.repository.checkout.OrderDetailRepository;
import tuan.tidi.repository.checkout.OrderRepository;
import tuan.tidi.repository.checkout.OrderRepositoryCustomImpl;
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

	private final static int APPID = 12606;

	private final static String KEY1 = "KHprobjCJn1Ohq1CwOvKAxuWs6f7iBcI";

	@Autowired
	private JmsTemplate jmsTemplate;
	
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
	private OrderRepositoryCustomImpl orderRepositoryCustomImpl;

	@Autowired
	private OrdersHistoryRepository ordersHistoryRepository;

	// Insert product
	@CrossOrigin(origins = "*")
	@PostMapping(API.INSERTITEM)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public StatusDTO insertItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE"))
			return statusDTO;

		// Check accountID
		String username = jwtService.getUsernameFromToken(authToken);
		Accounts accounts = accountsRepository.findByUsernameLike(username);
		if (accounts == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// Check ProductId
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Product has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// Check amount
		if (cartDetailDTO.getAmount() <= 0) {
			statusDTO.setMessage("Amount must be greater than 0!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		Cart cart;
		if (cartRepositoryCustomImpl.findAccountProduct(accounts.getId(), cartDetailDTO.getProductId()) == null) {
			cart = new Cart(accounts.getId(), cartDetailDTO.getProductId(), cartDetailDTO.getAmount());
		} else {
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

	// get all item in cart
	@CrossOrigin(origins = "*")
	@GetMapping(API.GETITEM)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public ListProductDTO loadItem(HttpServletRequest httpServletRequest) {
		ListProductDTO listProductDTO = new ListProductDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE")) {
			listProductDTO.setStatus(statusDTO);
			return listProductDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
		List<Cart> cart = cartRepository.findByAccountsId(account.getId());
		if (cart == null || cart.isEmpty()) {
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

	// update item
	@CrossOrigin(origins = "*")
	@PostMapping(API.UPDATEITEM)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public StatusDTO updateItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE")) {
			return statusDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));

		// check accountId
		if (account == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// Check ProductId
		if (productRepository.findById(cartDetailDTO.getProductId()) == null) {
			statusDTO.setMessage("Product has not been existed!!!");
			statusDTO.setStatus("FALSE");
			return statusDTO;
		}

		// Check amount
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
		} else {
			Cart cart = cartRepositoryCustomImpl.findAccountProduct(account.getId(), cartDetailDTO.getProductId());
			cart.setAmount(cartDetailDTO.getAmount());
			cartRepository.save(cart);
		}
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		return statusDTO;
	}

	// Delete item
	@CrossOrigin(origins = "*")
	@PostMapping(API.DELETEITEM)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public StatusDTO deleteItem(@RequestBody CartDetailDTO cartDetailDTO, HttpServletRequest httpServletRequest) {
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE")) {
			return statusDTO;
		}
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));

		// check accountId
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

	// Checkout

	@CrossOrigin(origins = "*")
	@PostMapping(API.CHECKOUT)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public OrderIdDTO checkout(@RequestBody CheckoutDTO checkoutDTO, HttpServletRequest httpServletRequest) {
		
		OrderIdDTO orderIdDTO = new OrderIdDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		int orderId = orderRepository.getMaxId() + 1;

		checkoutDTO.setUsername(jwtService.getUsernameFromToken(authToken));
		checkoutDTO.setOrderId(orderId);
		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));
		orderIdDTO.setOrderId(orderId);
		StatusDTO status = new StatusDTO();
		status.setMessage("");
		status.setStatus("TRUE");
		orderIdDTO.setStatus(status);
		Orders order = new Orders();

		order.setAccountsId(account.getId());
		order.setActive("FALSE");
		order.setAddress(checkoutDTO.getAddress());
		order.setCouponId(1);
		order.setEmail(checkoutDTO.getEmail());
		order.setFullName(checkoutDTO.getFullName());
		order.setNote(checkoutDTO.getNote());
		order.setPhone(checkoutDTO.getPhone());
		order.setStatus("");
		order.setTotal(0);
		order.setId(orderId);
		order.setApptransid(FormatDate.formatDateZP(new Date()) +"-"+ String.valueOf(orderId));
		orderRepository.save(order);
		jmsTemplate.convertAndSend("tuanQueue", checkoutDTO);
		return orderIdDTO;
	}

	@JmsListener(destination = "tuanQueue", containerFactory = "myFactory")
	public void saveOder(CheckoutDTO checkoutDTO) {
		ZPTokenDTO zPTokenDTO = checkout2(checkoutDTO);
		Orders order = orderRepository.findById(checkoutDTO.getOrderId());
		order.setJsonzptoken(zPTokenDTO.toString());
		orderRepository.save(order);
	}
	
	
	public ZPTokenDTO checkout2(CheckoutDTO checkoutDTO) {
		ZPTokenDTO zPTokenDTO = new ZPTokenDTO();
		StatusDTO statusDTO = new StatusDTO();
		
		// check null
		if (checkoutDTO.getAddress() == null || checkoutDTO.getAddress().isEmpty()) {
			statusDTO.setMessage("Address must be not null!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		if (checkoutDTO.getPhone() == null || checkoutDTO.getPhone().isEmpty()) {
			statusDTO.setMessage("Phone must be not null!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		if (checkoutDTO.getEmail() == null || checkoutDTO.getEmail().isEmpty()) {
			statusDTO.setMessage("Email must be not null!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		if (checkoutDTO.getFullName() == null || checkoutDTO.getFullName().isEmpty()) {
			statusDTO.setMessage("FullName must be not null!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		int accountId = accountsRepository.findByUsernameLike(checkoutDTO.getUsername()).getId();
		// check cart
		List<Cart> cart = cartRepository.findByAccountsId(accountId);
		if (cart == null || cart.isEmpty()) {
			statusDTO.setMessage("Your cart is empty! Continue shopping :D");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}
		Coupon coupon = new Coupon();
		if (checkoutDTO.getCouponCode() != null && !checkoutDTO.getCouponCode().isEmpty())
			coupon = couponRepository.findByCouponCodeLike(checkoutDTO.getCouponCode());
		else {
			coupon = couponRepository.findById(1);
		}

		// check coupon
		if (coupon == null) {
			statusDTO.setMessage("Coupon is wrong!!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}
		if (coupon.getActive().equals("FALSE")) {
			statusDTO.setMessage("Coupon is expired!!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}
		if (coupon.getAmount() == 0) {
			statusDTO.setMessage("Coupon has run out!!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		Accounts account = accountsRepository.findByUsernameLike(checkoutDTO.getUsername());
		List<Cart> cartt = cartRepository.findByAccountsId(account.getId());
		if (cart == null || cart.isEmpty()) {
			statusDTO.setMessage("Your cart is empty! Continue shopping :D");
			statusDTO.setStatus("TRUE");
		}
		List<ProductDTO> listProductDTO = new ArrayList<ProductDTO>();
		for (Cart car : cartt) {
			Product pro = productRepository.findById(car.getProductId());
			ProductDTO proDTO = tranferDTO(pro);
			proDTO.setAmount(car.getAmount());
			listProductDTO.add(proDTO);
		}
		int total = 0;
		// check products in cart
		statusDTO.setMessage("");
		for (ProductDTO pro : listProductDTO) {
			Product product = productRepository.findById(pro.getId());
			if (product.getActive().equals("FALSE"))
				statusDTO.setMessage(statusDTO.getMessage() + "ProductId " + pro.getId() + " has been block!!!\n");
			if (product.getAmount() == 0)
				statusDTO.setMessage(statusDTO.getMessage() + "ProductId " + pro.getId() + " has run out!!!\n");
			if (product.getAmount() < pro.getAmount())
				statusDTO.setMessage(statusDTO.getMessage() + "ProductId " + pro.getId() + " is not enough!!!\n");
			total += pro.getPrice() * pro.getAmount() * (float) (1 - pro.getDiscPercent());
		}
		if (statusDTO.getMessage() != "") {
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}

		if (total >= coupon.getThreshold())
			total = (int) (total * (float) (1 - (float) coupon.getPercent()) - coupon.getMoney());
		else {
			statusDTO.setMessage("Total < Threshold!!!");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}
		int orderId = checkoutDTO.getOrderId();
		Orders order = orderRepository.findById(orderId);

		// ZALOPAY :D
		String zPToken = "";
		String apptransid = order.getApptransid();
		if (checkoutDTO.getShippingMethod() != null && checkoutDTO.getShippingMethod().equals("ZaloPay")) {
			JSONObject embeddata = new JSONObject();
			embeddata.put("userId", accountId);
			embeddata.put("orderId", orderId);
			List<JSONObject> item = new ArrayList<JSONObject>();
			for (ProductDTO pro : listProductDTO) {
				JSONObject obj = new JSONObject();
				Product product = productRepository.findById(pro.getId());
				obj.put("id", pro.getId());
				obj.put("price", (int) ((float) product.getPrice()
						* (float) discountRepositoryCustomImpl.findLastedDiscount(product.getId())));
				obj.put("amount", pro.getAmount());
				item.add(obj);
			}
			Long apptime = System.currentTimeMillis() / 1L;
			String embe = embeddata.toString();
			String ite = item.toString();
			//embe = embe.replace("\"", "\\\"");
			//ite = ite.replace("\"", "\\\"");
			String macFormat = "%s|%s|%s|%s|%s|%s|%s";
			String macData = String.format(macFormat, String.valueOf(APPID) ,apptransid ,String.valueOf(accountId), String.valueOf(total) , apptime ,embe ,ite);
			System.out.println(macData);
			String mac = HMACUtil.HMacHexStringEncode("HmacSHA256", KEY1, macData);
			String JSONzPToken = createOrderZP(APPID, String.valueOf(accountId), apptime,
					Long.valueOf(total), apptransid, embe, ite, "Mua hang TIDI", mac);
			System.out.println(JSONzPToken);
			JSONObject ob;
			try{
				ob = new JSONObject(JSONzPToken);
				zPToken = ob.getString("zptranstoken");
			}catch(Exception e){
				
			}

		}
		// if (zPToken.isEmpty()) return;
		// subtract coupon amount
		if (checkoutDTO.getCouponCode() != null && !checkoutDTO.getCouponCode().isEmpty())
			if (coupon.getAmount() != -1)
				coupon.setAmount(coupon.getAmount() - 1);

		// subtract product amount
		for (ProductDTO pro : listProductDTO) {
			Product product = productRepository.findById(pro.getId());
			product.setAmount(product.getAmount() - pro.getAmount());
			productRepository.save(product);
		}

		// insert order
		order.setAccountsId(accountId);
		order.setActive("TRUE");
		order.setAddress(checkoutDTO.getAddress());
		order.setCouponId(coupon.getId());
		order.setEmail(checkoutDTO.getEmail());
		order.setFullName(checkoutDTO.getFullName());
		order.setNote(checkoutDTO.getNote());
		order.setPhone(checkoutDTO.getPhone());
		if (checkoutDTO.getShippingMethod().equals("ZaloPay")) {
			order.setStatus("PENDING");
			order.setActive("FALSE");
		} else
			order.setStatus("CHECKED");
		order.setZalopayToken(zPToken);
		order.setTotal(total);
		order.setApptransid(apptransid);
		orderRepository.save(order);

		// insert orderDetails
		for (ProductDTO pro : listProductDTO) {
			OrdersDetail ordersDetail = new OrdersDetail();
			Product product = productRepository.findById(pro.getId());
			ordersDetail.setActive("TRUE");
			ordersDetail.setAmount(pro.getAmount());
			ordersDetail.setFinalPrice((int) ((float) product.getPrice()
					* (float) discountRepositoryCustomImpl.findLastedDiscount(product.getId())));
			ordersDetail.setOrdersId(orderId);
			ordersDetail.setOriginalPrice(product.getPrice());
			ordersDetail.setProductId(pro.getId());
			try {
				orderDetailRepository.save(ordersDetail);
			} catch (Exception e) {

			}
			try {
				orderDetailRepository.save(ordersDetail);
			} catch (Exception e) {

			}
		}

		// insert orderHistory
		OrdersHistory ordershistory = new OrdersHistory();
		ordershistory.setActive("TRUE");
		ordershistory.setDateTime(new Date());
		ordershistory.setOrderId(orderId);
		if (checkoutDTO.getShippingMethod() != null && checkoutDTO.getShippingMethod().equals("ZaloPay")) {
			ordershistory.setStatus("PENDING");
		} else
			ordershistory.setStatus("CHECKED");
		ordersHistoryRepository.save(ordershistory);

		// delete cart
		cartRepository.deleteAll(cartRepository.findByAccountsId(accountId));

		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		zPTokenDTO.setOrderId(String.valueOf(orderId));
		zPTokenDTO.setZptranstoken(zPToken);
		zPTokenDTO.setStatus(statusDTO);
		return zPTokenDTO;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(API.CHECKORDER)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public ZPTokenDTO checkorder(@RequestBody OrderIdDTO orderId) {
		ZPTokenDTO zPTokenDTO = new ZPTokenDTO();
		StatusDTO statusDTO = new StatusDTO();
		String s = orderRepository.findById(orderId.getOrderId()).getJsonzptoken();
		if (s.isEmpty()) {
			statusDTO.setStatus("PROCESSING");
			zPTokenDTO.setStatus(statusDTO);
			return zPTokenDTO;
		}
		JSONObject obj = new JSONObject(s);
		zPTokenDTO.setZptranstoken(obj.getString("zptranstoken"));
		statusDTO.setStatus("TRUE");
		zPTokenDTO.setStatus(statusDTO);
		return zPTokenDTO;
	}
	
	// get all orders
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETORDERS)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public ListOrdersDTO loadOrders(@RequestBody SearchDTO searchDTO, HttpServletRequest httpServletRequest) {
		ListOrdersDTO listOrdersDTO = new ListOrdersDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE")) {
			listOrdersDTO.setStatus(statusDTO);
			return listOrdersDTO;
		}

		Accounts account = accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken));

		// check account
		if (account == null) {
			statusDTO.setMessage("Account has not been existed!!!");
			statusDTO.setStatus("FALSE");
			listOrdersDTO.setStatus(statusDTO);
			return listOrdersDTO;
		}

		// check date
		if (searchDTO.getQuery() != null) {
			if ((searchDTO.getQuery().getStartTime() != null) && (searchDTO.getQuery().getExpiredTime() != null)) {
				Date st;
				Date et;
				try {
					st = FormatDate.parseDateTime(searchDTO.getQuery().getStartTime());
				} catch (Exception e) {
					st = FormatDate.parseDateTime("2000-01-01 00:00:00");
				}
				try {
					et = FormatDate.parseDateTime(searchDTO.getQuery().getExpiredTime());
				} catch (Exception e) {
					et = new Date();
				}
				if (st == null)
					st = FormatDate.parseDateTime("2000-01-01 00:00:00");
				if (et == null)
					et = new Date();
				if (st.compareTo(et) > 0) {
					statusDTO.setMessage("startTime > expiredTime!!!");
					statusDTO.setStatus("FALSE");
					listOrdersDTO.setStatus(statusDTO);
					return listOrdersDTO;
				}
			}
		}
		if (orderRepository.findByAccountsId(account.getId()) == null) {
			statusDTO.setMessage("You dont have any orders! Continue shopping :D");
			statusDTO.setStatus("TRUE");
			return listOrdersDTO;
		}
		List<Orders> orders = orderRepositoryCustomImpl.search(searchDTO, account.getId());
		List<OrdersDTO> lordersDTO = new ArrayList<OrdersDTO>();
		if (orders == null) {
			statusDTO.setMessage("No result");
			statusDTO.setStatus("FALSE");
			listOrdersDTO.setStatus(statusDTO);
			return listOrdersDTO;
		}
		for (Orders ord : orders) {
			OrdersDTO ordersDTO = new OrdersDTO(ord);
			List<OrdersHistory> ordersHistory = ordersHistoryRepository.findByOrderId(ord.getId());
			int historyId = 0;
			for (OrdersHistory orh : ordersHistory) {
				if (orh.getId() > historyId) {
					ordersDTO.setDate(FormatDate.formatDateTime(orh.getDateTime()));
					ordersDTO.setStatus(orh.getStatus());
					historyId = orh.getId();
				}
			}
			lordersDTO.add(ordersDTO);
		}
		searchDTO.setLimit(Integer.MAX_VALUE);
		searchDTO.setOffset(0);
		listOrdersDTO.setTotalItems(orderRepositoryCustomImpl.search(searchDTO, account.getId()).size());
		listOrdersDTO.setOrders(lordersDTO);
		statusDTO.setMessage("Successful!");
		statusDTO.setStatus("TRUE");
		listOrdersDTO.setStatus(statusDTO);
		return listOrdersDTO;
	}

	// get one orders
	@CrossOrigin(origins = "*")
	@PostMapping(API.GETONEORDER)
	@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
	@ResponseBody
	public OrderStatusDTO getOneOrder(@RequestBody OrderIdDTO orderIdDTO, HttpServletRequest httpServletRequest) {
		OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
		StatusDTO statusDTO = new StatusDTO();
		String authToken = httpServletRequest.getHeader("authorization");
		statusDTO = checkJwt.checkJWT(authToken, false);
		if (statusDTO.getStatus().equals("FALSE")) {
			orderStatusDTO.setStatus(statusDTO);
			return orderStatusDTO;
		}
		Orders order = orderRepository.findById(orderIdDTO.getOrderId());
		if (order == null) {
			statusDTO.setMessage("OrderId has not been existed!!!");
			statusDTO.setStatus("FAlSE");
			orderStatusDTO.setStatus(statusDTO);
			return orderStatusDTO;
		}
		// check orderId == token
		if (order.getAccountsId() != accountsRepository.findByUsernameLike(jwtService.getUsernameFromToken(authToken))
				.getId()) {
			statusDTO.setMessage("OrderId is not yours");
			statusDTO.setStatus("FAlSE");
			orderStatusDTO.setStatus(statusDTO);
			return orderStatusDTO;
		}
		OrderDTO orderDTO = new OrderDTO(order);

		// add products
		List<OrdersDetail> ordersDetail = orderDetailRepository.findByOrdersId(orderIdDTO.getOrderId());
		List<ProductDTO> lproductDTO = new ArrayList<ProductDTO>();
		for (OrdersDetail ord : ordersDetail) {
			Product product = productRepository.findById(ord.getProductId());
			ProductDTO productDTO = tranferDTO(product);
			productDTO.setAmount(ord.getAmount());
			productDTO.setDiscPercent(1 - ((float) ord.getFinalPrice() / (float) ord.getOriginalPrice()));
			productDTO.setPrice(ord.getOriginalPrice());
			lproductDTO.add(productDTO);
		}
		orderDTO.setProducts(lproductDTO);

		// add history
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

	// Zalo

	// ZaloPay callback
	@CrossOrigin(origins = "*")
	@PostMapping("/api/v1/checkout/callback")
	@ResponseBody
	public ReturnCallbackDTO zPcallback(@RequestBody ZaloPayCallback zaloPayCallback) {
		ReturnCallbackDTO returnCallbackDTO = new ReturnCallbackDTO();
		if (zaloPayCallback.getData() == null || zaloPayCallback.getData().isEmpty()) {
			returnCallbackDTO.setReturncode(0);
			returnCallbackDTO.setReturnmessage("Data is empty!");
			return returnCallbackDTO;
		}
		if (zaloPayCallback.getMac() == null || zaloPayCallback.getMac().isEmpty()) {
			returnCallbackDTO.setReturncode(0);
			returnCallbackDTO.setReturnmessage("Mac is empty!");
			return returnCallbackDTO;
		}
		JSONObject obj = new JSONObject(zaloPayCallback.getData());
		Long zptransid = (Long) obj.get("zptransid");
		String apptransid = (String) obj.get("apptransid");
		if (orderRepository.findByZptransid(zptransid) != null) {
			returnCallbackDTO.setReturncode(2);
			returnCallbackDTO.setReturnmessage("zptransid has been existed!");
			return returnCallbackDTO;
		}
		Orders order = orderRepository.findByApptransid(apptransid);
		if (order != null) {
			order.setZptransid(zptransid);
			orderRepository.save(order);
		}
		returnCallbackDTO.setReturncode(1);
		returnCallbackDTO.setReturnmessage("Successful!");
		System.out.println("\n\n");
		System.out.println(zaloPayCallback.toString());
		System.out.println("\n\n");
		return returnCallbackDTO;
	}

	@PostMapping("/a")
	@ResponseBody
	public String a(HttpServletRequest req) {
		String a = HMACUtil.HMacHexStringEncode("HmacSHA256", "1E3kCLDkLL2GDhaYhEahsbviSfzwSCDXi",
				"1|180208181007242|pmqc|500000|1518088207242|{\"promotioninfo\":\"{\"campaigncode\":\"yeah\"}\",\"merchantinfo\":\"embeddata123\"}|[{\"itemid\":\"knb\",\"itemname\":\"kim nguyen bao\",\"itemquantity\":10,\"itemprice\":50000}]");
		return a;
	}

	// create order zalopay
	public String createOrderZP(@RequestBody int appid, String appuser, Long apptime, Long amount, String apptransid,
			String embeddata, String item, String description, String mac) {
		
		String urlParameters = "appid=" + String.valueOf(appid) + "&appuser=" + appuser + "&apptime="
				+ String.valueOf(apptime) + "&amount=" + String.valueOf(amount) + "&apptransid=" + apptransid
				+ "&embeddata=" + embeddata + "&item=" + item + "&description=" + description + "&mac=" + mac;
		
		System.out.println("\n\n========================================================\n\n\n");
		System.out.println(urlParameters);
		
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		String request = API.ZALOPAY;
		String data = "";
		try {
			// create connection
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			conn.connect();

			// write url
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(postData);

			// read data from api
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			int c;
			while ((c = in.read()) > 0) {
				data += (char) c;
			}

		} catch (Exception e) {
			System.out.println("asdsadsadasdsadsadsa" + e);
		}
		System.out.println(data);
		return data;
	}

	// get zalopayToken
	@CrossOrigin(origins = "*")
	@PostMapping("/api/v1/zp/token")
	@ResponseBody
	public ZPTokenDTO getZPToken(@RequestBody OrderIdDTO orderIdDTO) {
		ZPTokenDTO zPTokenDTO = new ZPTokenDTO();
		String zPToken = null;
		if (orderRepository.findById(orderIdDTO.getOrderId()) != null) {
			zPToken = orderRepository.findById(orderIdDTO.getOrderId()).getZalopayToken();
		}
		if (zPToken != null) {
			zPTokenDTO.setZptranstoken(zPToken);
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setMessage("Successfull");
			statusDTO.setStatus("TRUE");
			zPTokenDTO.setStatus(statusDTO);
		} else {
			zPTokenDTO.setZptranstoken(null);
			StatusDTO statusDTO = new StatusDTO();
			statusDTO.setMessage("ZPToken is emty");
			statusDTO.setStatus("FALSE");
			zPTokenDTO.setStatus(statusDTO);
		}
		return zPTokenDTO;
	}

	// get orderstatus from zalopay
	@CrossOrigin(origins = "*")
	@PostMapping("/api/v1/zp/order")
	@ResponseBody
	public ZaloPayStatusDTO getOrderStatusZP(@RequestBody OrderIdDTO orderIdDTO) {
		ZaloPayStatusDTO zaloPayStatusDTO = new ZaloPayStatusDTO();
		Orders order = orderRepository.findById(orderIdDTO.getOrderId());
		if (order == null) {
			zaloPayStatusDTO.setStatus("FALSE");
			return zaloPayStatusDTO;
		}
		String contentFormat = "%s|%s|%s";
		String contentData = String.format(contentFormat, String.valueOf(APPID), order.getApptransid(), KEY1);
		String mac = HMACUtil.HMacHexStringEncode("HmacSHA256", KEY1, contentData);
		String urlParameters = "appid=" + String.valueOf(APPID) + "&apptransid=" + order.getApptransid() + "&mac=" + mac;
		System.out.println(urlParameters);
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		String request = API.ZALOPAYSTATUS;
		String data = "";
		try {
			// create connection
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			conn.connect();

			// write url
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(postData);

			// read data from api
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			int c;
			while ((c = in.read()) > 0) {
				data += (char) c;
			}
			JSONObject obj = new JSONObject(data);
			if (obj.getBoolean("isprocessing") == true || obj.getInt("returncode") == -49) {
				zaloPayStatusDTO.setStatus("PROCESSING");
				return zaloPayStatusDTO;
			}
			else {
				if (obj.getInt("returncode") == 1) {
					zaloPayStatusDTO.setStatus("SUCCESSFUL");
					order.setActive("TRUE");
					order.setStatus("CHECKED");
					OrdersHistory ordersHistory = new OrdersHistory();
					ordersHistory.setActive("TRUE");
					ordersHistory.setDateTime(new Date());
					ordersHistory.setOrderId(orderIdDTO.getOrderId());
					ordersHistory.setStatus("CHECKED");
					orderRepository.save(order);
					ordersHistoryRepository.save(ordersHistory);
					return zaloPayStatusDTO;
				}
				else {
					System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
					System.out.println(obj.toString());
					zaloPayStatusDTO.setStatus("CANCELED");
					order.setStatus("CANCELED");
					OrdersHistory ordersHistory = new OrdersHistory();
					ordersHistory.setActive("TRUE");
					ordersHistory.setDateTime(new Date());
					ordersHistory.setOrderId(orderIdDTO.getOrderId());
					ordersHistory.setStatus("CANCELED");
					orderRepository.save(order);
					ordersHistoryRepository.save(ordersHistory);
					List<OrdersDetail> ordersDetail = orderDetailRepository.findByOrdersId(orderIdDTO.getOrderId());
					for (OrdersDetail ord : ordersDetail) {
						Product pro = productRepository.findById(ord.getProductId());
						pro.setAmount(pro.getAmount()+ord.getAmount());
						productRepository.save(pro);
					}
					return zaloPayStatusDTO;
				}
			}
		} catch (Exception e) {

		}
		return zaloPayStatusDTO;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(API.CHECKCOUPON)
	@ResponseBody
	public CouponStatusDTO checkCoupon(@RequestBody CouponDTO couponDTO) {
		CouponStatusDTO couponStatusDTO = new CouponStatusDTO();
		Coupon coupon = couponRepository.findByCouponCodeLike(couponDTO.getCoupon());
		if (coupon == null) {
			couponStatusDTO.setMessage("CouponCode is incorrect!!!");
			couponStatusDTO.setStatus(-1);
			return couponStatusDTO;
		}
		
		if (coupon.getActive().equals("FALSE")) {
			couponStatusDTO.setMessage("CouponCode has been expired!!!");
			couponStatusDTO.setStatus(0);
			return couponStatusDTO;
		}
		
		couponStatusDTO.setDiscPercent(coupon.getPercent());
		couponStatusDTO.setMoney(coupon.getMoney());
		couponStatusDTO.setMessage("OK!!!");
		couponStatusDTO.setStatus(1);
		return couponStatusDTO;
	}
}
