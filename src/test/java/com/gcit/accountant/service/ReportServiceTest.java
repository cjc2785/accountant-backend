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
public class ReportServiceTest {
	
	@Autowired
	OrderDetailDao orderDetailDao;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	ProductDao productDao;
	
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
		
		
		Category shoes = new Category();
		shoes.setName("shoes");
		
		categoryDao.save(shoes);
		
		Product shoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("shoe");
		
		productDao.save(shoe);
		
		
		OrderDetail oldDetail = new OrderDetail();
		oldDetail.setId(new OrderDetailId(old.getOrderId(), shoe.getProductId()));
		oldDetail.setOrder(old);
		oldDetail.setProduct(shoe);
		oldDetail.setQuantity(1);
		oldDetail.setUnitPrice(1d);
		oldDetail.setTaxes(1d);
		
		OrderDetail recentDetail = new OrderDetail();
		recentDetail.setId(new OrderDetailId(recent.getOrderId(), shoe.getProductId()));
		recentDetail.setOrder(recent);
		recentDetail.setProduct(shoe);
		recentDetail.setQuantity(1);
		recentDetail.setUnitPrice(1d);
		recentDetail.setTaxes(1d);
		
		
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
		
		
		Category shoes = new Category();
		shoes.setName("shoes");
		
		categoryDao.save(shoes);
		
		Product shoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("shoe");
		
		Product runningShoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("running shoe");
		
		productDao.save(shoe);
		productDao.save(runningShoe);
		
		
		OrderDetail janOrderDetail1 = new OrderDetail();
		janOrderDetail1.setId(new OrderDetailId(
				janOrder.getOrderId(),
				shoe.getProductId()));
		janOrderDetail1.setOrder(janOrder);
		janOrderDetail1.setProduct(shoe);
		janOrderDetail1.setQuantity(1);
		janOrderDetail1.setUnitPrice(100d);
		janOrderDetail1.setTaxes(10d);
		
		OrderDetail janOrderDetail2 = new OrderDetail();
		janOrderDetail2.setId(new OrderDetailId(
				janOrder.getOrderId(),
				runningShoe.getProductId()));
		janOrderDetail2.setOrder(janOrder);
		janOrderDetail2.setProduct(runningShoe);
		janOrderDetail2.setQuantity(1);
		janOrderDetail2.setUnitPrice(20d);
		janOrderDetail2.setTaxes(2d);
		
		OrderDetail febOrderDetail1 = new OrderDetail();
		febOrderDetail1.setId(new OrderDetailId(
				febOrder.getOrderId(),
				shoe.getProductId()));
		febOrderDetail1.setOrder(febOrder);
		febOrderDetail1.setProduct(shoe);
		febOrderDetail1.setQuantity(1);
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
		
		Category shoes = new Category();
		shoes.setName("shoes");
		
		categoryDao.save(shoes);
		
		Product shoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("shoe");
		
		Product runningShoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("running shoe");
		
		productDao.save(shoe);
		productDao.save(runningShoe);
		
		
		OrderDetail janOrderDetail1 = new OrderDetail();
		janOrderDetail1.setId(new OrderDetailId(
				janOrder.getOrderId(),
				shoe.getProductId()));
		janOrderDetail1.setOrder(janOrder);
		janOrderDetail1.setProduct(shoe);
		janOrderDetail1.setQuantity(1);
		janOrderDetail1.setUnitPrice(100d);
		janOrderDetail1.setTaxes(10d);
		
		OrderDetail janOrderDetail2 = new OrderDetail();
		janOrderDetail2.setId(new OrderDetailId(
				janOrder.getOrderId(),
				runningShoe.getProductId()));
		janOrderDetail2.setOrder(janOrder);
		janOrderDetail2.setProduct(runningShoe);
		janOrderDetail2.setQuantity(1);
		janOrderDetail2.setUnitPrice(20d);
		janOrderDetail2.setTaxes(2d);
		
		OrderDetail febOrderDetail1 = new OrderDetail();
		febOrderDetail1.setId(new OrderDetailId(
				febOrder.getOrderId(),
				shoe.getProductId()));
		febOrderDetail1.setOrder(febOrder);
		febOrderDetail1.setProduct(shoe);
		febOrderDetail1.setQuantity(1);
		febOrderDetail1.setUnitPrice(10d);
		febOrderDetail1.setTaxes(1d);
		

		orderDetailDao.save(janOrderDetail1);
		orderDetailDao.save(janOrderDetail2);
		orderDetailDao.save(febOrderDetail1);

		
		
		Report actual = reportService.getReport(start, end);
		
		
		
		Double expectedTaxesDue = 13d;
		
		assertEquals(expectedTaxesDue, actual.getTaxesDue(), 0.0001);
	}
	
	@Test
	void getReportAccountsForProductQuantity() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order janOrder = new Order();
		janOrder.setOrderDate(Date.valueOf("2019-01-15"));

		
		orderDao.save(janOrder);
		

		Category shoes = new Category();
		shoes.setName("shoes");
		
		categoryDao.save(shoes);
		
		Product shoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("shoe");
		
		productDao.save(shoe);
		
		
		OrderDetail janOrderDetail1 = new OrderDetail();
		janOrderDetail1.setId(new OrderDetailId(
				janOrder.getOrderId(),
				shoe.getProductId()));
		janOrderDetail1.setOrder(janOrder);
		janOrderDetail1.setProduct(shoe);
		janOrderDetail1.setUnitPrice(100d);
		janOrderDetail1.setTaxes(10d);
		janOrderDetail1.setQuantity(3);

		orderDetailDao.save(janOrderDetail1);

		
		
		Report actual = reportService.getReport(start, end);
		
		
		
		Double expectedRevenue = 300d;
		Double expectedTaxesDue = 30d;
		
		assertEquals(expectedRevenue, actual.getRevenue(), 0.0001);
		assertEquals(expectedTaxesDue, actual.getTaxesDue(), 0.0001);
	}
}
