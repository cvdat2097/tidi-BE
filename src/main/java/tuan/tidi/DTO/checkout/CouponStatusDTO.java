package tuan.tidi.DTO.checkout;

public class CouponStatusDTO {
	private int status;
	private String message;
	private int money;
	private float discPercent;
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

	public float getDiscPercent() {
		return discPercent;
	}
	public void setDiscPercent(float discPercent) {
		this.discPercent = discPercent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
