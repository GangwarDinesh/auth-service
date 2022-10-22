package com.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.auth.entity.User;
import com.auth.util.JwtUtil;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User appUser = null;
		try {
			ResponseEntity<User> responseEntity = restTemplate
					.getForEntity(
							jwtUtil.getClientDetailsDto().getUserApiBaseUrl()
									+ jwtUtil.getClientDetailsDto().getApiEndpointName() + "?username=" + username,
							User.class);
			appUser = responseEntity.getBody();
		} catch (HttpStatusCodeException e) {
			System.out.println("Exception occurred from get user api with status code: " + e.getRawStatusCode());
			appUser = new User();
		}
		return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(),
				new ArrayList<>());
	}
}
