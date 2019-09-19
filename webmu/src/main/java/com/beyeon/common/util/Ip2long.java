package com.beyeon.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * IP,十进制数字互转
 */
public class Ip2long {

	private static final String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
			+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

	/**
	 * IP转换成十进制数字
	 * 
	 * @param ip
	 * @return
	 */
	public static long ip2long(String ip) {
		String[] ips = ip.split("[.]");
		long num = 16777216L * Long.parseLong(ips[0]) + 65536L * Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
		return num;
	}

	/**
	 * 十进制数字转化成IP
	 * 
	 * @param ipLong
	 * @return
	 */
	public static String long2ip(long ipLong) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}

	/**
	 * IP是否合法
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static boolean matchesIp(String ipAddress) {
		if (StringUtils.isBlank(ipAddress)) {
			return false;
		}
		return ipAddress.matches(regex);
	}

	public static void main(String[] args) throws Exception {
		long ip1 = ip2long("192.168.255.103");
		long ip2 = ip2long("10.255.0.221");
		System.out.println(ip1 + ":" + long2ip(ip1));
		System.out.println(ip2 + ":" + long2ip(ip2));
	}
}
