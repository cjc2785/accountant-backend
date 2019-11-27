package com.gcit.accountant.model;

public class Report {
	
	private Double revenue;
	private Double taxesDue;
	
	public Report() { }
	
	public Report(Double revenue, Double taxesDue) {
		this.revenue = revenue;
		this.taxesDue = taxesDue;
	}

	public Double getRevenue() {
		return revenue;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getTaxesDue() {
		return taxesDue;
	}

	public void setTaxesDue(Double taxesDue) {
		this.taxesDue = taxesDue;
	}
}
