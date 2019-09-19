package com.beyeon.common.web.control.intercept;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.TokenHelper;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

import com.beyeon.common.exception.AppExceptionImpl;

/**
 * User: lwf
 * Date: 12-6-20
 * Time: 下午3:47
 */
public class TokenInterceptor extends AbstractInterceptor {
    protected Logger logger =LoggerFactory.getLogger(getClass());
	public static final String SAVED_REQUEST = "SAVED_REQUEST_TOKEN_KEY";
    
    protected Set<String> excludeMethods = Collections.emptySet();
    protected Set<String> includeMethods = Collections.emptySet();

    public void setExcludeMethods(String excludeMethods) {
        this.excludeMethods = TextParseUtil.commaDelimitedStringToSet(excludeMethods);
    }
    
    public Set<String> getExcludeMethodsSet() {
    	return excludeMethods;
    }

    public void setIncludeMethods(String includeMethods) {
        this.includeMethods = TextParseUtil.commaDelimitedStringToSet(includeMethods);
    }
    
    public Set<String> getIncludeMethodsSet() {
    	return includeMethods;
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        if (applyInterceptor(invocation)) {
            return doIntercept(invocation);
        } 
        return invocation.invoke();
    }

    protected boolean applyInterceptor(ActionInvocation invocation) {
        String method = invocation.getProxy().getMethod();
        boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(excludeMethods, includeMethods, method);
        return applyMethod;
    }

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		if(isMatchSavedRequestToken(request)){
			ActionProxy ap = invocation.getProxy();
			throw new AppExceptionImpl("相同数据重复提交");
		}
		return invocation.invoke();
	}

	public boolean isMatchSavedRequestToken(HttpServletRequest request){
		HttpSession session = request.getSession();
		synchronized (session) {

			String tokenName = TokenHelper.getTokenName();
			if (tokenName != null) {
				String token = TokenHelper.getToken(tokenName);
				if (token != null) {
					String sessionToken = (String) session.getAttribute(tokenName);
					if (token.equals(sessionToken)) {
						session.removeAttribute(tokenName);
						return false;
					}
				}
			}

			SavedRequestToken savedRequestToken = (SavedRequestToken) session.getAttribute(SAVED_REQUEST);
			if(null != savedRequestToken && savedRequestToken.doesRequestMatch(request)){
				return true;
			} else {
				request.getSession().removeAttribute(SAVED_REQUEST);
				request.getSession().setAttribute(SAVED_REQUEST, new SavedRequestToken(request));
				return false;
			}
		}
	}

	public class SavedRequestToken {
		//~ Instance fields ================================================================================================
		private final Map<String, String[]> parameters = new TreeMap<String, String[]>(String.CASE_INSENSITIVE_ORDER);
		private final String method;
		private final String requestURI;
		private final String queryString;
		private final Date token;

		public Map<String, String[]> getParameters() {
			return parameters;
		}

		public String getMethod() {
			return method;
		}

		public String getRequestURI() {
			return requestURI;
		}

		public String getQueryString() {
			return queryString;
		}
		public String[] getParameterValue(String key) {
			return parameters.get(key);
		}
		public SavedRequestToken(HttpServletRequest request) {
			Map<String,String[]> parameterMap = request.getParameterMap();

			for(String paramName : parameterMap.keySet()) {
				String[] paramValues = parameterMap.get(paramName);
				this.addParameter(paramName, paramValues);
			}
			this.method = request.getMethod();
			this.requestURI = request.getRequestURI();
			this.queryString = request.getQueryString();
			this.token = new Date();
		}

		private void addParameter(String name, String[] values) {
			parameters.put(name, values);
		}

		public boolean doesRequestMatch(HttpServletRequest request) {
			if((System.currentTimeMillis() - token.getTime()) > 5000){
				return false;
			}
			SavedRequestToken savedRequestToken = new SavedRequestToken(request);
			if (!propertyEquals(this.method,savedRequestToken.getMethod())) {
				return false;
			}

			if (!propertyEquals(this.requestURI, savedRequestToken.getRequestURI())) {
				return false;
			}

			if (!propertyEquals(this.queryString, savedRequestToken.getQueryString())) {
				return false;
			}
			if(this.parameters.size() != savedRequestToken.getParameters().size() ){
				return false;
			}
			Set<Map.Entry<String, String[]>> entrySet = parameters.entrySet();
			for (Iterator<Map.Entry<String, String[]>> iterator = entrySet.iterator(); iterator.hasNext(); ) {
				Map.Entry<String, String[]> next =  iterator.next();
				String[] prev = next.getValue();String[] curr = savedRequestToken.getParameterValue(next.getKey());
				if(!propertyEquals(prev,curr)){
					return false;
				}
			}
			return true;
		}

		private boolean propertyEquals(String arg1, String arg2) {
			if ((arg1 == null) && (arg2 == null)) {
				return true;
			}

			if (((arg1 == null) && (arg2 != null)) || ((arg1 != null) && (arg2 == null))) {
				return false;
			}

			return arg1.equals(arg2);
		}

		private boolean propertyEquals(String[] arg1, String[] arg2) {
			if ((arg1 == null) && (arg2 == null)) {
				return true;
			}

			if (((arg1 == null) && (arg2 != null)) || ((arg1 != null) && (arg2 == null))) {
				return false;
			}

			List list1 = Arrays.asList(arg1);
			List list2 = Arrays.asList(arg2);
			if (list1.size() != list2.size()) {
				return false;
			} else {
				return CollectionUtils.isEqualCollection(list1, list2);
			}
		}
	}

}
