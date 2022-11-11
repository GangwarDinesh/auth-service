package com.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.auth.entity.User;
import com.auth.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AllybotAppUserServiceImpl implements AppUserService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User getUserDetails(String username, String password) {
		User appUser = new User();
		try {
			Map<String, String> requestBody = new HashMap<>();
			requestBody.put("email", username);
			requestBody.put("password", password);
			HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(requestBody);
			ResponseEntity<JsonNode> responseEntity = restTemplate
					.postForEntity(jwtUtil.getClientDetailsDto().getUserApiBaseUrl()
							+ jwtUtil.getClientDetailsDto().getApiEndpointName(), httpEntity, JsonNode.class);
			JsonNode body = responseEntity.getBody();
			appUser.setUsername(body.get("data").get("email").asText());
			appUser.setPassword(encoder.encode(body.get("data").get("password").asText()));
		} catch (HttpStatusCodeException e) {
			System.out.println("Exception occurred from get user api with status code: " + e.getRawStatusCode());
			appUser = new User();
		}
		return appUser;
	}

}
