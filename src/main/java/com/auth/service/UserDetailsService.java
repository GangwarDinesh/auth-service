package com.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.entity.User;
import com.auth.util.JwtUtil;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User appUser = appUserService.getUserDetails(username, jwtUtil.getPassword());
		return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(),
				new ArrayList<>());
	}
}
