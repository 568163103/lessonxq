package com.beyeon.common.web.control.intercept;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.exception.AppException;
import com.beyeon.common.exception.ExceptionUtil;

public class ExceptionInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 7964549372767828479L;

	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Throwable e) {
			ExceptionUtil.error(e);
			ActionProxy ap = invocation.getProxy();
			ActionSupport as = ((ActionSupport) invocation.getAction());
			if(e instanceof AppException){
				as.addActionMessage(e.getMessage());
			} else {
				as.addActionMessage(ResourceUtil.getCoreConf("system.error"));
			}
			if (ap.getConfig().getResults().containsKey(ap.getMethod()+"Error")) {
				result = ap.getMethod()+"Error";
			} else {
				result = ActionSupport.ERROR;
			}
		}
		return result;
	}

}
