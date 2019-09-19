package com.beyeon.common.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingUtil {
	private static Logger logger =LoggerFactory.getLogger(PingUtil.class);

	/**
	 * 判断ip地址是否可达
	 * 
	 * <pre>
	 * @param ip		ip地址
	 * @param timeout	等待时间（单位 毫秒）
	 * @return
	 * </pre>
	 */
	public static boolean isPing(String ip, int timeout) {
		if (n(ip) || "0.0.0.0".equals(ip)) {
			return false;
		}
		boolean status = false;
		try {
			status = InetAddress.getByName(ip).isReachable(timeout);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		logger.info("ping " + ip + " ,return " + status);
		return status;
	}

	/**
	 * 判断网络地址是否可以连接
	 * 
	 * <pre>
	 * @param ip			ip地址
	 * @param port			端口号
	 * @param timeout 		超时时间(单位毫秒)
	 * @return
	 * </pre>
	 */
	public static boolean conn(String ip, int port, int timeout) {
		if (n(ip) || n(port) || n(timeout)) {
			logger.info("ping " + ip + ", port " + port + ", result false");
			return false;
		}
		Socket socket = null;
		try {
			socket = new Socket();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
			socket.connect(inetSocketAddress, timeout);
			logger.info("ping " + ip + ", port " + port + ", result true");
			return true;
		} catch (Exception e) {
			logger.info("ping " + ip + ", port " + port + ", result false");
			logger.info(e.getMessage(), e);
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				logger.info("socket close exception ");
			}
		}
		return false;
	}

	public static boolean n(int n) {
		return 0 >= n;
	}

	public static boolean n(String ip) {
		if (StringUtils.isBlank(ip)) {
			logger.info("ip is null");
			return true;
		}
		return false;
	}
}
