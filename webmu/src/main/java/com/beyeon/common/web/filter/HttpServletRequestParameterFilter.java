package com.beyeon.common.web.filter;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.beyeon.common.config.ResourceUtil;

/**
 * User: lwf
 * Date: 13-12-24
 * Time: 下午4:53
 */
public class HttpServletRequestParameterFilter extends OncePerRequestFilter {
	private static String[] appSpecialCharactersSearch = ResourceUtil.getGlobalPublicConf("app.special.characters.search").split(",");
	private static String[] appSpecialCharactersReplace = ResourceUtil.getGlobalPublicConf("app.special.characters.replace").split(",");
	private final String DEFAULT_LOGO = "/css/common/images/logo.png";
	private Map<String,Boolean> logoExits = new HashMap<String,Boolean>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if (request instanceof HttpParameterRequestWrapper){
				filterChain.doFilter(request, response);
			} else {
				filterChain.doFilter(new HttpParameterRequestWrapper(request), response);
			}
		} finally {
			ResourceUtil.cleanRequestContext();
		}
	}

	private String handleIdentityCode(HttpServletRequest request, String identityCode){
		try {
			while (StringUtils.isNotBlank(identityCode)) {
				Boolean logoExit = logoExits.get(identityCode);
				if (null == logoExit) {
					File file = new File(request.getSession().getServletContext().getRealPath(DEFAULT_LOGO.replace("_hd", identityCode)));
					logoExit = file.exists();
					logoExits.put(ResourceUtil.getIdentityCode(), logoExit);
					logger.debug("logo is exit: " + logoExit);
				}
				if (logoExit){
					return identityCode;
				} else {
					identityCode = identityCode.substring(0,identityCode.lastIndexOf("_"));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "_" + ResourceUtil.getAppRscConf("app_hg_code");
	}

	private String handleSpecialCharacters(String input) {
		if (input == null) {
			return input;
		}
		return StringUtils.replaceEach(input,appSpecialCharactersSearch,appSpecialCharactersReplace);
	}

	private String handleParameterValue(String parameterValue){
		if(null == parameterValue){
			return null;
		}
		parameterValue = parameterValue.trim();
		parameterValue = handleSpecialCharacters(parameterValue);
		return parameterValue;
	}

	private String[] handleParameterValue(String[] parameterValues){
		if(null == parameterValues){
			return null;
		}
		for (int i = 0; i < parameterValues.length; i++) {
			parameterValues[i] = handleParameterValue(parameterValues[i]);
		}
		return parameterValues;
	}

	private class HttpParameterRequestWrapper extends HttpServletRequestWrapper {

		public HttpParameterRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public Object getAttribute(String name) {
			Object value = ResourceUtil.getRequestContext(name);
			if(null != value){
				return value;
			}
			value = ResourceUtil.getAppRscConf(name);
			if(null != value){
				return value;
			}
			return super.getAttribute(name);
		}

		@Override
		public Map getParameterMap() {
			Map<String, String[]> map = new HashMap<String, String[]>();
			Enumeration enumeration = super.getParameterNames();

			while (enumeration.hasMoreElements()) {
				String name = (String) enumeration.nextElement();
				map.put(name, this.getParameterValues(name));
			}

			return map;
		}

		@Override
		public String getParameter(String name) {
			return handleParameterValue(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			return handleParameterValue(super.getParameterValues(name));
		}

	}
}
