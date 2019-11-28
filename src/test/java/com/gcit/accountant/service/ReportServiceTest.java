package com.gcit.accountant.service;

import com.gcit.accountant.dao.*;

import com.gcit.accountant.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Transactional
class OrderDetailDaoTest {
	
	@Autowired
	OrderDetailDao orderDetailDao;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	ReportService reportService;

	@Test
	void getReportShouldReturn0RevenueAnd0TaxesDueWhenNoOrderDetailsMatch() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order old = new Order();
		old.setOrderDate(Date.valueOf("2018-01-1"));
		
		Order recent = new Order();
		recent.setOrderDate(Date.valueOf("2019-03-01"));
		
		orderDao.save(old);
		orderDao.save(recent);
		
		
		OrderDetail oldDetail = new OrderDetail();
		oldDetail.setId(new OrderDetailId(old.getOrderId(), 1));
		oldDetail.setOrder(old);
		
		OrderDetail recentDetail = new OrderDetail();
		recentDetail.setId(new OrderDetailId(recent.getOrderId(), 1));
		recentDetail.setOrder(recent);
		
		
		orderDetailDao.save(oldDetail);
		orderDetailDao.save(recentDetail);

		
		
		Report actual = reportService.getReport(start, end);
		
		
		
		Double expectedRevenue = 0d;
		Double expectedTaxesDue = 0d;
		
		assertEquals(expectedRevenue, actual.getRevenue(), 0.0001);
		assertEquals(expectedTaxesDue, actual.getTaxesDue(), 0.0001);
	}
	
	@Test
	void getReportShouldGenerateTheRevenueFromTheOrderDetailsUnitPrices() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order janOrder = new Order();
		janOrder.setOrderDate(Date.valueOf("2019-01-15"));
		
		Order febOrder = new Order();
		febOrder.setOrderDate(Date.valueOf("2019-02-01"));
		
		orderDao.save(janOrder);
		orderDao.save(febOrder);
		
		
		OrderDetail janOrderDetail1 = new OrderDetail();
		janOrderDetail1.setId(new OrderDetailId(
				janOrder.getOrderId(),
				1));
		janOrderDetail1.setOrder(janOrder);
		janOrderDetail1.setUnitPrice(100d);
		janOrderDetail1.setTaxes(10d);
		
		OrderDetail janOrderDetail2 = new OrderDetail();
		janOrderDetail2.setId(new OrderDetailId(
				janOrder.getOrderId(),
				3));
		janOrderDetail2.setOrder(janOrder);
		janOrderDetail2.setUnitPrice(20d);
		janOrderDetail2.setTaxes(2d);
		
		OrderDetail febOrderDetail1 = new OrderDetail();
		febOrderDetail1.setId(new OrderDetailId(
				febOrder.getOrderId(),
				1));
		febOrderDetail1.setOrder(febOrder);
		febOrderDetail1.setUnitPrice(10d);
		febOrderDetail1.setTaxes(1d);
		

		orderDetailDao.save(janOrderDetail1);
		orderDetailDao.save(janOrderDetail2);
		orderDetailDao.save(febOrderDetail1);

		
		
		Report actual = reportService.getReport(start, end);
		
		
		
		Double expectedRevenue = 130d;
		
		assertEquals(expectedRevenue, actual.getRevenue(), 0.0001);
	}
	
	@Test
	void getReportShouldGenerateTheTaxesDueFromTheOrderDetailsTaxes() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order janOrder = new Order();
		janOrder.setOrderDate(Date.valueOf("2019-01-15"));
		
		Order febOrder = new Order();
		febOrder.setOrderDate(Date.valueOf("2019-02-01"));
		
		orderDao.save(janOrder);
		orderDao.save(febOrder);
		
		
		OrderDetail janOrderDetail1 = new OrderDetail();
		janOrderDetail1.setId(new OrderDetailId(
				janOrder.getOrderId(),
				1));
		janOrderDetail1.setOrder(janOrder);
		janOrderDetail1.setUnitPrice(100d);
		janOrderDetail1.setTaxes(10d);
		
		OrderDetail janOrderDetail2 = new OrderDetail();
		janOrderDetail2.setId(new OrderDetailId(
				janOrder.getOrderId(),
				3));
		janOrderDetail2.setOrder(janOrder);
		janOrderDetail2.setUnitPrice(20d);
		janOrderDetail2.setTaxes(2d);
		
		OrderDetail febOrderDetail1 = new OrderDetail();
		febOrderDetail1.setId(new OrderDetailId(
				febOrder.getOrderId(),
				1));
		febOrderDetail1.setOrder(febOrder);
		febOrderDetail1.setUnitPrice(10d);
		febOrderDetail1.setTaxes(1d);
		

		orderDetailDao.save(janOrderDetail1);
		orderDetailDao.save(janOrderDetail2);
		orderDetailDao.save(febOrderDetail1);

		
		
		Report actual = reportService.getReport(start, end);
		
		
		
		Double expectedTaxesDue = 13d;
		
		assertEquals(expectedTaxesDue, actual.getTaxesDue(), 0.0001);
	}
}
