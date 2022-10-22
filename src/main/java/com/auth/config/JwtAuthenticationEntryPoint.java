package com.auth.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.auth.dto.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ResponseBody responseBody = new ResponseBody();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentLocalDateTime = LocalDateTime.now();
		String currentDateTimeStr = formatter.format(currentLocalDateTime);
		responseBody.setTimestamp(currentDateTimeStr);

		String key = response.getHeader("key") != null ? response.getHeader("key") : "";
		if ("clientauthfailed".equalsIgnoreCase(key)) {
			responseBody.setMessage("Client Id is invalid.");
		} else if ("userauthfailed".equalsIgnoreCase(key)) {
			responseBody.setMessage("JWT is invalid.");
		}
		responseBody.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		response.getWriter().println(mapper.writeValueAsString(responseBody));
	}

}
