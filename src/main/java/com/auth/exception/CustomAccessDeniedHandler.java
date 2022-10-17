package com.auth.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
    public void handle(
      HttpServletRequest request,
      HttpServletResponse response, 
      AccessDeniedException exc) throws IOException, ServletException {
        
        Authentication auth 
          = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
             String s =   " attempted to access the protected URL: "+ request.getRequestURI();
             System.out.println(s);
        }

        response.sendRedirect(request.getContextPath() + "/v1/auth/error");
    }
}