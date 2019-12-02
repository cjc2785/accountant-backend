package com.gcit.accountant.service;

import com.gcit.accountant.dao.OrderDetailDao;
import com.gcit.accountant.model.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportService {
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	
	public Summary getSummary(Date start, Date end) {
		
		List<OrderDetail> details = orderDetailDao.getAllBetween(start, end);
		
		double revenue = details.stream()
				.mapToDouble(detail -> detail.getQuantity() * detail.getUnitPrice())
				.sum();
		
		double taxesDue = details.stream()
				.mapToDouble(detail -> detail.getQuantity() * detail.getTaxes())
				.sum();
		
		return new Summary(revenue, taxesDue);
	}
	
	public List<ProductReport> getCategoryReport(String categoryName, Date start, Date end) {
		
		List<OrderDetail> details = orderDetailDao.getAllBetween(start, end);
		
		Map<Product, Integer> productQuantities = details.stream()
				.filter(d -> d.getProduct().getCategory().getName().equals(categoryName))
				.collect(Collectors.groupingBy(
						OrderDetail::getProduct, 
						Collectors.summingInt(OrderDetail::getQuantity)));
		
		Map<Product, Double> productSales = details.stream()
				.collect(Collectors.groupingBy(
						OrderDetail::getProduct, 
						Collectors.summingDouble(OrderDetail::getUnitPrice)));
		
		return productQuantities.entrySet()
				.stream() 
				.map(e -> {
					Product product = e.getKey();
					ProductReport report = new ProductReport();
					report.setProduct(product);
					report.setCount(e.getValue());
					report.setSales(productSales.get(product));
					return report;
				})
				.collect(Collectors.toList());
	}
}
