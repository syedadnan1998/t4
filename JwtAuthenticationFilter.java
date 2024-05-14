package com.example.project.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.example.project.service.UserAuthService;

public class JwtAuthenticationFilter extends GenericFilterBean {

	private static final String BEARER = "Bearer";

	@Autowired
	private UserAuthService authService;

	public JwtAuthenticationFilter(UserAuthService authService) {
		this.authService = authService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		
		if (!servletRequest.getRequestURI().contains("/h2-console")) {
			getToken(servletRequest).ifPresent(token -> {
				authService.getUserByJwtToken(token).ifPresent(userDetails -> {
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
							userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()));
				});
			});
		}

		filterChain.doFilter(request, response);
	}

	public Optional<String> getToken(HttpServletRequest req) {
		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null && !token.isEmpty()) {
			return Optional.of(token.replace(BEARER, "").trim());
		}
		return Optional.empty();
	}

}