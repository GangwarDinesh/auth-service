package com.auth.controller;

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
import com.auth.util.JwtUtil;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/jwt")
	public ResponseEntity<AuthResponse> generateJwt(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("inavalid username/password");
		}
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt("Bearer "+jwtUtil.generateToken(authRequest.getUsername()));
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
	}
	
	@GetMapping("/welcome")
    public String welcome() {
        return "Welcome to auth service !!";
    }
}
