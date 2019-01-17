package tuan.tidi.DTO.checkout;

public class ZaloPayCallback {
	private String mac;
	private String data;
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	@Override
	public String toString() {
		return "mac: " + mac +" ---- data " + data;
	}
}
