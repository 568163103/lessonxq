package com.beyeon.common.web.control.intercept;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import com.beyeon.common.web.control.action.BaseAction;

public class SessionManageInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 2425293029753423404L;

	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		ActionProxy ap = invocation.getProxy();
		BaseAction ba = ((BaseAction) invocation.getAction());
		if(null != ba.getSessPageObject()){
			ba.getSessPageObject().setResultList(null);
		}
		if(ap.getMethod().startsWith("update") || ap.getMethod().startsWith("delete") || ap.getMethod().endsWith("UP")){
			if(null != ba.getSessPageObject()){
				ba.setPageObject(ba.getSessPageObject());
			}
		}
		result = invocation.invoke();
		if (ap.getMethod().startsWith("findPage")) {
			ba.setSessPageObject();
		}
		return result;
	}

}
