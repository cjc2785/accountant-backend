package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailDao extends JpaRepository<OrderDetail, OrderDetailId> {
	
	@Query("SELECT d "
			+ "FROM OrderDetail d "
			+ "JOIN d.order o " 
			+ "WHERE o.orderDate "
			+ "BETWEEN :start AND :end")
	public List<OrderDetail> getAllBetween(
			@Param("start") Date start, 
			@Param("end") Date end);
}
