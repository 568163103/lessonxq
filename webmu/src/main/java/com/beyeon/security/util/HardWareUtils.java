package com.beyeon.security.util;

/**
 * User: Administrator
 * Date: 2016/1/13
 * Time: 10:19
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class HardWareUtils {

	private static String getOsName() {
		String os = "";
		os = System.getProperty("os.name");
		return os;
	}
	/**
	 * 获取主板序列号
	 *
	 * @return
	 */
	public static String getMotherboardSN() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_BaseBoard\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.SerialNumber \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
		}
		return result.trim();
	}

	/**
	 * 获取CPU序列号
	 *
	 * @return
	 */
	public static String getCPUSerial() {
		String result = "";
		Process p = null;BufferedReader input = null;
		String os = getOsName();
		if (os.startsWith("Windows")) {
			try {
				File file = File.createTempFile("tmp", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new FileWriter(file);

				String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"
						+ "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
						+ "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
						+ "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
						+ "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "
						+ "    exit for  ' do the first cpu only! \r\n" + "Next                    ";

				fw.write(vbs);
				fw.close();
				p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line.trim();
				}
				file.delete();
			} catch (Exception e) {
			}
		}else if (os.startsWith("Linux")) {
			String CPU_ID_CMD = "dmidecode -t 4 | grep ID |sort -u |awk -F': ' '{print $2}'";
			try {
				p = Runtime.getRuntime().exec(new String[]{"sh","-c",CPU_ID_CMD});//管道
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line.trim();
					break;
				}
			} catch (Exception e) {
			}
		}
		if (null != input) {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		if (result.trim().length() < 1 || result == null) {
			result = "无CPU_ID被读取";
		}
		return result.trim();
	}

	/**
	 * 获取MAC地址
	 */
	public static String getMac() {
		String address = "";
		Process p = null;BufferedReader input = null;
		String os = getOsName();
		if (os.startsWith("Windows")) {
			try {
				String command = "cmd.exe /c ipconfig /all";
				p = Runtime.getRuntime().exec(command);
				input = new BufferedReader(new InputStreamReader(p.getInputStream(),"gb18030"));
				String line;
				while ((line = input.readLine()) != null) {
					if (line.indexOf("Physical Address") > 0) {
						int index = line.indexOf(":");
						index += 2;
						address = line.substring(index);
						break;
					}else  if (line.indexOf("物理地址") > 0) {
						int index = line.indexOf(":");
						index += 2;
						address = line.substring(index);
						break;
					}
				}
			} catch (Exception e) {
			}
		} else if (os.startsWith("Linux")) {
			String command = "/bin/sh -c ifconfig -a";
			try {
				p = Runtime.getRuntime().exec(command);
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					if (line.indexOf("HWaddr") > 0) {
						int index = line.indexOf("HWaddr") + "HWaddr".length();
						address = line.substring(index);
						break;
					}
				}
			} catch (Exception e) {
			}
		}
		if (null != input) {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		address = address.trim();
		return address;
	}

	public static String getUUID() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMac());
		sb.append(getCPUSerial());
		sb.append(getMotherboardSN());
		return sb.toString();
	}

}
