package tuan.tidi.DTO.checkout;

public class CheckoutDTO {
	private String couponCode;
	private String fullName;
	private String phone;
	private String address;
	private String email;
	private String note;
	private String shippingMethod;
	private String username;
	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Override
	public String toString() {
		String address = (this.address == null) ? "\"address\":" + this.address + "" : "\"address\":\"" + this.address + "\"";
		String phone = (this.phone == null) ? "\"phone\":" + this.phone + "" : "\"phone\":\"" + this.phone + "\"";
		String fullName = (this.fullName == null) ? "\"fullName\":" + this.fullName + "" : "\"fullName\":\"" + this.fullName + "\"";
		String couponCode = (this.couponCode == null) ? "\"couponCode\":" + this.couponCode + "" : "\"couponCode\":\"" + this.couponCode + "\"";
		String email = (this.email == null) ? "\"email\":" + this.email + "" : "\"email\":\"" + this.email + "\"";
		String note = (this.note == null) ? "\"note\":" + this.note + "" : "\"note\":\"" + this.note + "\"";
		String shippingMethod = (this.shippingMethod == null) ? "\"shippingMethod\":" + this.shippingMethod + "" : "\"shippingMethod\":\"" + this.shippingMethod + "\"";
		String username = (this.username == null) ? "\"username\":" + this.username + "" : "\"username\":\"" + this.username + "\"";

		
		return "{" + address + "," + phone + ","+ fullName + ","+ couponCode + ","+ email + ","+ note + ","+ shippingMethod + ","+ username + "}";
	}
}
