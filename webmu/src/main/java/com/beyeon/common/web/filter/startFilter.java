package com.beyeon.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 创建人：FH 
 * 创建时间：2014年2月17日
 * @version
 */
public class startFilter  implements Filter{

	
	
	
	/**
	 * 初始化
	 */
	public void init(FilterConfig fc) throws ServletException {
			FlyLoggerThread t = new FlyLoggerThread();
		    FlyLoggerThreadListener listener = new FlyLoggerThreadListener();
		    t.addObserver(listener);
		    new Thread(t).start();
	}
	

	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
}
