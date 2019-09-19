package com.beyeon.common.security.spring.access.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SLAuthenticationFilter extends AuthenticationHandlerImpl {

	public void doAuthentication(ServletRequest request, ServletResponse response,Object object) throws IOException, ServletException {
		FilterChain chain = (FilterChain) object;
		chain.doFilter(request, response);
	}

}