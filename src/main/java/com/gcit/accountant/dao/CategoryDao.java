package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryDao extends JpaRepository<Category, Integer> {
	
}
