package com.auth.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.dto.ClientDetailsDto;
import com.auth.exception.AuthenticationException;
import com.auth.service.UserDetailsService;
import com.auth.util.JwtUtil;

@Component
public class AuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		String token = null;
		String userName = null;
		boolean isJwtValidationFailed = false;
		boolean isClientValidationFailed = false;
		try {
			Optional<ClientDetailsDto> clientDetailsDtoOpt = jwtUtil
					.getClientDetailsDto(request.getHeader("Client-Id"));
			clientDetailsDtoOpt.ifPresent(obj -> jwtUtil.setClientDetailsDto(obj));
			/*String payload = new String(req.getReader().lines().reduce("", String::concat));

			@SuppressWarnings("unchecked")
			Map<String, Object> jsonRequest = new ObjectMapper().readValue(payload, Map.class);
			jwtUtil.setPassword((String) jsonRequest.get("password"));*/

		} catch (Exception e) {
			isClientValidationFailed = true;
		}
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				userName = jwtUtil.extractUsername(token);
			}

			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

				if (jwtUtil.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}else {
					isJwtValidationFailed = true;
				}
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			isJwtValidationFailed = true;
		} catch (Exception e) {
			e.printStackTrace();
			isJwtValidationFailed = true;
		}

		if (isClientValidationFailed) {
			response.setHeader("key", "clientauthfailed");
		} else if (isJwtValidationFailed) {
			response.setHeader("key", "userauthfailed");
		}
		filterChain.doFilter(request, response);
	}

}
