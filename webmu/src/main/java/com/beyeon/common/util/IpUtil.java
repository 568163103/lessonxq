package com.beyeon.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ip地址工具类
 */
public class IpUtil {
	private static final Logger logger =LoggerFactory.getLogger(IpUtil.class);

	/** ip正则 */
	private static final String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

	public static List<String> getLocalIps() {
		List<String> ips = new ArrayList<String>();
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress ip = (InetAddress) addresses.nextElement();
					if (null != ip && ip instanceof Inet4Address) {
						String hostAddress = ip.getHostAddress();
						if (!"127.0.0.1".equals(hostAddress)) {
							ips.add(ip.getHostAddress());
						}
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
		return ips;
	}

	/**
	 * IP是否合法
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean matchesIp(String ip) {
		if (StringUtils.isBlank(ip)) {
			return false;
		}
		return ip.matches(regex);
	}

	/**
	 * 判断ip地址是否为网络地址
	 * 
	 * <pre>
	 * @param ip	网络ip
	 * @param mask	子网位数
	 * @return
	 * </pre>
	 */
	public static boolean matchesNetIp(String ip, int mask) {
		if (!matchesIp(ip) || mask > 32) {
			return false;
		}

		Long b_ip = ipToLong(ip) & 0xFFFFFFFF << (32 - mask);
		return b_ip.equals(ipToLong(ip));
	}

	/**
	 * IP转换成十进制数字
	 * 
	 * @param ip
	 * @return
	 */
	public static long ipToLong(String ip) {
		String[] ips = ip.split("[.]");
		return 16777216L * Long.parseLong(ips[0]) + 65536L * Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
	}

	/**
	 * 十进制数字转化成IP
	 * 
	 * @param ipLong
	 * @return
	 */
	public static String longToIp(long ipLong) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);

			if (i > 0) {
				ipInfo.insert(0, ".");
			}

			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}

	/**
	 * 获取子网掩码
	 * 
	 * <pre>
	 * @param maskNum	子网掩码位数
	 * @return	ip
	 * </pre>
	 */
	public static String maskIp(int maskNum) {
		StringBuffer sb = new StringBuffer();
		/** 子网掩码二进制1的个数 */
		int max = 32;
		for (int i = 1; i <= maskNum; i++) {
			sb.append("1");
		}

		int zero = max - maskNum;
		for (int i = 0; i < zero; i++) {
			sb.append("0");
		}

		return thirtytwoToIp(sb.toString());
	}

	/**
	 * 根据子网掩码计算转换子网掩码时的位数
	 * 
	 * <pre>
	 * @param ip	子网掩码
	 * @return	subnetMask
	 * </pre>
	 */
	public static int maskNum(String ip) {
		String x = ipToThirtytwo(ip);

		if (StringUtils.isBlank(x)) {
			return 0;
		}

		int count = 0;
		for (int i = 0; i < x.length(); i++) {
			if ("1".equals(String.valueOf(x.charAt(i)))) {
				count++;
			}
		}

		return count;
	}

	/**
	 * 最小ip地址
	 * 
	 * <pre>
	 * @param ip			ip地址
	 * @param maskIp		子网
	 * @return	long
	 * </pre>
	 */
	public static String minIp(String ip, String maskIp) {
		if ("255.255.255.255".equals(maskIp)) {
			return "0.0.0.0";
		}
		int mask = maskNum(maskIp);
		long netIp = ipToLong(netIp(ip, mask)) + 1;
		return longToIp(netIp);
	}

	/**
	 * 最大ip地址
	 * 
	 * <pre>
	 * @param ip			ip地址
	 * @param maskIp		子网
	 * @return	long
	 * </pre>
	 */
	public static String maxIp(String ip, String maskIp) {
		if ("255.255.255.255".equals(maskIp)) {
			return "0.0.0.0";
		}
		int mask = maskNum(maskIp);
		long netIp = ipToLong(radioIp(ip, mask)) - 1;
		return longToIp(netIp);
	}

	/**
	 * 网络地址
	 * 
	 * <pre>
	 * @param ip		ip地址
	 * @param maskNum	掩码数
	 * @return
	 * </pre>
	 */
	public static String netIp(String ip, int maskNum) {
		if (32 == maskNum) {
			return "0.0.0.0";
		}
		String x = ipToThirtytwo(ip);
		x = x.substring(0, maskNum);
		for (int i = 0; i < 32 - maskNum; i++) {
			x = x + "0";
		}
		return thirtytwoToIp(x);
	}

	/**
	 * 网络地址
	 * 
	 * <pre>
	 * @param ip		ip地址
	 * @param maskNum	掩码数
	 * @return
	 * </pre>
	 */
	public static String radioIp(String ip, int maskNum) {
		if (32 == maskNum) {
			return "0.0.0.0";
		}
		String x = ipToThirtytwo(ip);
		x = x.substring(0, maskNum);
		for (int i = 0; i < 32 - maskNum; i++) {
			x = x + "1";
		}
		return thirtytwoToIp(x);
	}

	/**
	 * ip转32位二进制
	 * 
	 * @param ip
	 * @return
	 */
	public static String ipToThirtytwo(String ip) {
		String x = "";
		String[] arr = ip.split("[.]");
		for (int i = 0; i < arr.length; i++) {
			int count = 8;
			String s = Integer.toBinaryString(Integer.parseInt(arr[i]));
			int len = count - s.length();
			if (count != len) {
				for (int j = 0; j < len; j++) {
					s = "0" + s;
				}
			}
			x += s;
		}

		return x;
	}

	/**
	 * 32位二进制转ip地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String thirtytwoToIp(String ip) {
		String cmds[] = new String[] {};
		int count = 0;
		for (int i = 1; i <= ip.length(); i++) {
			if (i % 8 == 0) {
				cmds = ArrayUtils.add(cmds, ip.substring(count, i));
				count = i;
			}
		}

		StringBuffer sb = new StringBuffer();
		int len = cmds.length;
		for (int i = 0; i < len; i++) {
			sb.append(Integer.valueOf(cmds[i], 2).toString());
			if (i + 1 == len) {
				continue;
			}

			sb.append(".");
		}

		return sb.toString();
	}
	/*** 
     * 获取客户端ip地址(可以穿透代理) 
     * @param request 
     * @return 
     */  
    public static String getClientIpAddr(HttpServletRequest request) {    
        String ip = request.getHeader("X-Forwarded-For");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_FORWARDED");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_VIA");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("REMOTE_ADDR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    }  
	public static void main(String[] args) {
		System.out.println(IpUtil.ipToLong("192.168.99.0"));
	}
}
