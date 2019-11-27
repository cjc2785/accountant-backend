package com.gcit.accountant.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderDetailId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	@Column
	private Integer orderId;
	
	@Column
	private Integer productId;
	
	public OrderDetailId() { }
	
	public OrderDetailId(Integer orderId, Integer productId) {
		this.orderId = orderId;
		this.productId = productId;
	}
	
	

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null || !(other instanceof OrderDetailId)) {
			return false;
		}
		OrderDetailId key = (OrderDetailId)other;
		return key.orderId == orderId &&
				key.productId == productId;
	}
}
