package com.gcit.accountant.dao;

import com.gcit.accountant.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
