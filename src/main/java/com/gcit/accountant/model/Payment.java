package com.gcit.accountant.model;

public class Payment {

	private Double amount;
	
	public Payment() { }

	public Payment(Double amount) {
		this.amount = amount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
