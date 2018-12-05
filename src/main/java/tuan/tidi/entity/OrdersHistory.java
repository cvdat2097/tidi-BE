package tuan.tidi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ordersHistory")
public class OrdersHistory {
	@Id
	private int id;
	
	@Column(name = "ordersId", nullable = false)
	private int orderId;
	
	@Column(name = "statuss", nullable = false)
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateTime", nullable = false)
	private Date dateTime;
	
	@Column(name = "active", nullable = false)
	private String active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
}
