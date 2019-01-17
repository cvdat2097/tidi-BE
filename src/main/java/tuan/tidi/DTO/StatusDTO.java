package tuan.tidi.DTO;

public class StatusDTO {
	private String status;
	private String message;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		
		return "{\"status\":\""+status+"\",\"message\":\""+message+"\"}";
	}
	
}
