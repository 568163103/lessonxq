package com.beyeon.common.security.spring.access.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * User: lwf
 * Date: 13-11-12
 * Time: 上午11:25
 */
public interface AuthenticationHandler {
	Boolean authentication(ServletRequest request, ServletResponse response, Object object) throws IOException, ServletException;
}
