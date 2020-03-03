package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class OrderDetailDaoTest {
	
	@Autowired
	OrderDetailDao orderDetailDao;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	ProductDao productDao;

	@Test
	void getAllBetweenShouldReturnEmptyListWhenDatabaseIsEmpty() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		
		List<OrderDetail> actual = orderDetailDao.getAllBetween(start, end);
		
		
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	void getAllBetweenShouldReturnDetailsWithinTheTimePeriod() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order old = new Order();
		old.setOrderDate(Date.valueOf("2018-01-1"));
		
		Order recent = new Order();
		recent.setOrderDate(Date.valueOf("2019-03-01"));
		
		Order target = new Order();
		target.setOrderDate(Date.valueOf("2019-01-02"));
		
		
		orderDao.save(old);
		orderDao.save(recent);
		orderDao.save(target);
		
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
		
		
		OrderDetail recentDetail = new OrderDetail();
		recentDetail.setId(new OrderDetailId(recent.getOrderId(), 1));
		recentDetail.setOrder(recent);
		recentDetail.setProduct(shoe);
		
		OrderDetail targetDetail = new OrderDetail();
		targetDetail.setId(new OrderDetailId(target.getOrderId(), 1));
		targetDetail.setOrder(target);
		targetDetail.setProduct(shoe);
		
		
		orderDetailDao.save(oldDetail);
		orderDetailDao.save(recentDetail);
		orderDetailDao.save(targetDetail);
		
		
		
		List<OrderDetail> actual = orderDetailDao.getAllBetween(start, end);
		
		
		
		assertTrue(actual.contains(targetDetail));
		assertTrue(actual.size() == 1);
	}
	
	@Test
	void getAllBetweenShouldBeInclusiveOfTheTimePeriodStartAndEnd() {
		
		Date start = Date.valueOf("2019-01-01");
		Date end = Date.valueOf("2019-02-01");
		
		
		Order startOrder = new Order();
		startOrder.setOrderDate(start);
		
		Order endOrder = new Order();
		endOrder.setOrderDate(end);
		
		
		orderDao.save(startOrder);
		orderDao.save(endOrder);
		
		
		orderDao.save(startOrder);
		orderDao.save(endOrder);
		
		Category shoes = new Category();
		shoes.setName("shoes");
		
		categoryDao.save(shoes);
		
		Product shoe = new Product();
		shoe.setCategory(shoes);
		shoe.setBrand("nike");
		shoe.setName("shoe");
		
		productDao.save(shoe);
		
		
		OrderDetail startDetail = new OrderDetail();
		startDetail.setId(new OrderDetailId(startOrder.getOrderId(), shoe.getProductId()));
		startDetail.setOrder(startOrder);
		startDetail.setProduct(shoe);
		
		OrderDetail endDetail = new OrderDetail();
		endDetail.setId(new OrderDetailId(endOrder.getOrderId(), shoe.getProductId()));
		endDetail.setOrder(endOrder);
		endDetail.setProduct(shoe);
		
		
		orderDetailDao.save(startDetail);
		orderDetailDao.save(endDetail);
		
		
		
		List<OrderDetail> actual = orderDetailDao.getAllBetween(start, end);
		
		
		
		assertTrue(actual.contains(startDetail));
		assertTrue(actual.contains(endDetail));
	}
}
