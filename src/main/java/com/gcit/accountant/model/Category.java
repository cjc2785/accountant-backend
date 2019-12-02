package com.gcit.accountant.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column
	private String name;
	

	
	
	public Category() { }


	public Category(Integer categoryId, String name) {
		this.categoryId = categoryId;
		this.name = name;
	}

	
	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override 
	public boolean equals(Object other) {
		if(other == null || !(other instanceof Product)) {
			return false;
		}
		Category product = (Category)other;
		return Objects.equals(categoryId, product.getCategoryId());
	}
}
