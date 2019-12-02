package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
