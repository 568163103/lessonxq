package com.beyeon.common.web.control.intercept;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import com.beyeon.common.config.ResourceUtil;

public class LongAccessLogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5639935293884137695L;
	private Logger logger = LoggerFactory.getLogger("Global.LongAccess");

	public String intercept(ActionInvocation invocation) throws Exception {
		long startTime = System.currentTimeMillis();
        String result;
		try {
			result = invocation.invoke();
		} finally {
			long executionTime = System.currentTimeMillis() - startTime;
			if (executionTime >= Integer.parseInt(ResourceUtil.getCoreConf("log.long.access.time"))) {
			    StringBuilder message = new StringBuilder(100);
			    message.append("Executed [");
			    String namespace = invocation.getProxy().getNamespace();
			    if ((namespace != null) && (namespace.trim().length() > 0)) {
			        message.append(namespace).append("/");
			    }
			    message.append(invocation.getProxy().getActionName());
			    message.append("!");
			    message.append(invocation.getProxy().getMethod());
			    message.append("] used [").append(executionTime).append("] ms.");
			    logger.error(message.toString());
			}
		}
        return result;
	}
}
