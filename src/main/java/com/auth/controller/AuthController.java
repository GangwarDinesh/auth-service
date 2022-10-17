package com.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.AuthRequest;
import com.auth.dto.AuthResponse;
import com.auth.exception.AuthenticationException;
import com.auth.util.JwtUtil;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/jwt")
	public ResponseEntity<AuthResponse> generateJwt(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
		String key = response.getHeader("key");
		if("clientauthfailed".equalsIgnoreCase(key)) {
			throw new AuthenticationException(401, "Client Authentication failed!");
		}else if("userauthfailed".equalsIgnoreCase(key)) {
			throw new AuthenticationException(401, "User Authentication failed!");
		}
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt("Bearer " + jwtUtil.generateToken(authRequest.getUsername()));
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}

	@GetMapping("/jwt/verify")
	public ResponseEntity<Boolean> verifyJwt(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//JWT will be validated through AuthFilter
		String key = response.getHeader("key");
		if("clientauthfailed".equalsIgnoreCase(key)) {
			throw new AuthenticationException(401, "Client Authentication failed!");
		}else if("userauthfailed".equalsIgnoreCase(key)) {
			throw new AuthenticationException(401, "User Authentication failed!");
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping("/error")
	public String error() throws AuthenticationException {
		throw new AuthenticationException(401, "User Authentication failed!");
	}
}
