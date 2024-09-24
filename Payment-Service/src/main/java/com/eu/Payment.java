package com.eu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="payment")
public class Payment {
    @Id
	private String paymentId;
	private String paymentStatus;
	private String transactionId;
	private String id;
	private int amount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Payment(String paymentId,String paymentStatus, String transactionId) {
		super();
		this.paymentId=paymentId;
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
	}
	
	public Payment() {
		
	}
	public Payment(String paymentStatus, String transactionId, String id, int amount) {
		super();
		this.paymentStatus = paymentStatus;
		this.transactionId = transactionId;
		this.id = id;
		this.amount = amount;
	}
	
}
