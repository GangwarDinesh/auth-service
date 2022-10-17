package com.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.util.JwtUtil;

@Component
public class ClientFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String clientId = request.getHeader("Client-Id");
		boolean isPlanExpired = jwtUtil.validateClientSubscription(clientId);
		if (isPlanExpired) {
			response.setHeader("key", "clientauthfailed");
		}
		filterChain.doFilter(request, response);
	}
}
