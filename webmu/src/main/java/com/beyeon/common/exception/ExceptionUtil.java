package com.beyeon.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: lwf
 * Date: 12-7-2
 * Time: 上午10:45
 */
public class ExceptionUtil {
	private static Logger logger = LoggerFactory.getLogger("Global.Exception");

	public static void error(String message){
		logger.error(message);
	}

	public static void error(Throwable t){
		error(t.getMessage(),t);
	}

	public static void error(String message,Throwable t){
		logger.error(message,t);
	}
}
