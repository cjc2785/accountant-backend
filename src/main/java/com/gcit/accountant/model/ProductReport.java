package com.gcit.accountant.model;

public class ProductReport {
	
	private Product product;
	
	//The sales count 
	private Integer count;
	
	private Double sales;
	
	public ProductReport() { }

	public ProductReport(Product product, Integer count, Double sales) {
		this.product = product;
		this.count = count;
		this.sales = sales;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}
}
