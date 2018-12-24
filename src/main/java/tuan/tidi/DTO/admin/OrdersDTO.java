package tuan.tidi.DTO.admin;

import java.util.List;

import tuan.tidi.DTO.checkout.OrderHistoryDTO;
import tuan.tidi.DTO.product.ProductDTO;
import tuan.tidi.entity.Orders;

public class OrdersDTO {
	private List<OrderHistoryDTO> history;
	private List<ProductDTO> products;
	private AccountsDTO user;
	private int id;
	private String fullName;
	private int total;
	private int couponId;
	private String phone;
	private String email;
	private String address;
	private String status;
	private String note;
	private String date;
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public OrdersDTO() {
		
	}
	
	public OrdersDTO(Orders order) {
		this.id = order.getId();
		this.fullName = order.getFullName();
		this.total = order.getTotal();
		this.couponId = order.getCouponId();
		this.phone = order.getPhone();
		this.email = order.getEmail();
		this.address = order.getAddress();
		this.status = order.getStatus();
		this.note = order.getNote();
	}

	public List<OrderHistoryDTO> getHistory() {
		return history;
	}
	public void setHistory(List<OrderHistoryDTO> history) {
		this.history = history;
	}
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public AccountsDTO getUser() {
		return user;
	}
	public void setUser(AccountsDTO user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
