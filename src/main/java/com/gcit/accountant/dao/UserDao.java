package com.gcit.accountant.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcit.accountant.model.*;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	public Optional<User> findByEmail(String username);
}
