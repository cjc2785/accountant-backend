package com.gcit.accountant.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.gcit.accountant.dao.*;
import com.gcit.accountant.model.*;

@Service
public class UserPrincipalService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public UserPrincipal findById(Integer userId) {
		return userDao.findById(userId)
				.map(this::toPrincipal)
				.orElseThrow(() -> new UsernameNotFoundException("user not found"));
	}


	@Override
	@Transactional
	public UserPrincipal loadUserByUsername(String email)
			throws UsernameNotFoundException {

		User user = userDao.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						"user not found"));

		return toPrincipal(user);
	}


	private UserPrincipal toPrincipal(User user) {

		List<GrantedAuthority> authorities = user.getRoles()
				.stream()
				.map(Role::getRoleName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UserPrincipal(user, authorities);
	}
}
