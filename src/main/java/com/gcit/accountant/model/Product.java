package com.gcit.accountant.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productId;
	
	@Column
	private String brand;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	
	public Product() { }
	
	public Product(Integer productId, String brand, String name, Category category) {
		this.productId = productId;
		this.brand = brand;
		this.name = name;
		this.category = category;
	}


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override 
	public boolean equals(Object other) {
		if(other == null || !(other instanceof Product)) {
			return false;
		}
		Product product = (Product)other;
		return Objects.equals(productId, product.getProductId());
	}
}
