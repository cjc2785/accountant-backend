package com.gcit.accountant.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="orderDetails")
public class OrderDetail {
	
	@EmbeddedId
	private OrderDetailId id;
	
	@Column
	private Double unitPrice;
	
	@Column
	private Integer quantity;
	
	@Column
	private Double taxes;
	
	@ManyToOne
	@MapsId("orderId")
	private Order order;
	
	
	public OrderDetail() { }

	public OrderDetail(OrderDetailId id, 
			Double unitPrice, 
			Integer quantity,
			Double taxes,
			Order order) {
		this.id = id;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.taxes = taxes;
		this.order = order;
	}

	public OrderDetailId getId() {
		return id;
	}

	public void setId(OrderDetailId id) {
		this.id = id;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Override 
	public boolean equals(Object other) {
		if(other == null || !(other instanceof OrderDetail)) {
			return false;
		}
		OrderDetail detail = (OrderDetail)other;
		return Objects.equals(id, detail.getId());
	}
}
