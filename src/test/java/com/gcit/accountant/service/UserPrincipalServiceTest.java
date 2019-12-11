package com.gcit.accountant.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gcit.accountant.dao.UserDao;
import com.gcit.accountant.model.User;
import com.gcit.accountant.model.UserPrincipal;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Transactional
class UserPrincipalServiceTest {

	@Autowired
	public UserDao userDao;

	@Autowired 
	UserPrincipalService service;


	@Test
	void findByIdShouldReturnBobWhenGiveHisId() {


		User al = new User();
		al.setEmail("al@example.com");
		al.setRoles(new HashSet<>());

		User bob = new User();
		bob.setEmail("bob@example.com");
		bob.setRoles(new HashSet<>());

		User leo = new User();
		leo.setEmail("leo@example.com");
		leo.setRoles(new HashSet<>());
		
		List<User> users = Arrays.asList(al, bob, leo);

		userDao.saveAll(users);

		

		UserPrincipal actualPrincipal = service.findById(bob.getUserId());
		


		String actualEmail = actualPrincipal.getUsername();

		assertEquals("bob@example.com", actualEmail);
	}

	@Test
	void findByIdShouldThrowWhenUserDoesNotExist() {

		assertThrows(UsernameNotFoundException.class, () -> {
			service.findById(1);
		});
	}


	@Test
	void loadUserByUsernameShouldReturnBobWhenGivenHisEmail() {


		User al = new User();
		al.setEmail("al@example.com");
		al.setRoles(new HashSet<>());

		User bob = new User();
		bob.setEmail("bob@example.com");
		bob.setRoles(new HashSet<>());

		User leo = new User();
		leo.setEmail("leo@example.com");
		leo.setRoles(new HashSet<>());

		List<User> users = Arrays.asList(al, bob, leo);

		userDao.saveAll(users);



		UserPrincipal actualPrincipal = service.loadUserByUsername("bob@example.com");



		String actualEmail = actualPrincipal.getUsername();

		assertEquals("bob@example.com", actualEmail);
	}


	@Test
	void loadUserByUsernameShouldThrowWhenUserDoesNotExist() {


		User al = new User();
		al.setEmail("al@example.com");
		al.setRoles(new HashSet<>());

		User bob = new User();
		bob.setEmail("bob@example.com");
		bob.setRoles(new HashSet<>());

		User leo = new User();
		leo.setEmail("leo@example.com");
		leo.setRoles(new HashSet<>());

		List<User> users = Arrays.asList(al, bob, leo);

		userDao.saveAll(users);

		

		assertThrows(UsernameNotFoundException.class, () -> {

			service.loadUserByUsername("dan@example.com");
		});
	}
}