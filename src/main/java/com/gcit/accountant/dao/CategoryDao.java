package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryDao extends JpaRepository<Category, Integer> {

	public List<Category> findByName(String name);
}
