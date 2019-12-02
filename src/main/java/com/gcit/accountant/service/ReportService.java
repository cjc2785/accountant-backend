package com.gcit.accountant.service;

import com.gcit.accountant.dao.OrderDetailDao;
import com.gcit.accountant.model.*;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReportService {
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	
	public Report getReport(Date start, Date end) {
		
		List<OrderDetail> details = orderDetailDao.getAllBetween(start, end);
		
		double revenue = details.stream()
				.mapToDouble(detail -> detail.getQuantity() * detail.getUnitPrice())
				.sum();
		
		double taxesDue = details.stream()
				.mapToDouble(detail -> detail.getQuantity() * detail.getTaxes())
				.sum();
		
		return new Report(revenue, taxesDue);
	}
}
