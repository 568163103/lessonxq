package com.beyeon.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.exception.AppExceptionRtImpl;

public class SendMail {
	private static Logger logger =LoggerFactory.getLogger(SendMail.class);
	private ImageHtmlEmail email = new ImageHtmlEmail();

	public SendMail() {
		String hostName = ResourceUtil.getGlobalPublicConf("app.email.host");
		String port = ResourceUtil.getGlobalPublicConf("app.email.smtp.port");
		String fromAddress = ResourceUtil.getGlobalPublicConf("app.email.fromAddress");
		boolean ssl = Boolean.valueOf(ResourceUtil.getGlobalPublicConf("app.email.ssl"));
		String sslport = ResourceUtil.getGlobalPublicConf("app.email.sslsmtp.port");
		boolean tls = Boolean.valueOf(ResourceUtil.getGlobalPublicConf("app.email.tls"));
		String userName = fromAddress.substring(0, fromAddress.indexOf("@"));
		String password = ResourceUtil.getGlobalPublicConf("app.email.password");
		int connectiontimeout = ResourceUtil.getGlobalPublicConfInt("app.email.connectiontimeout");
		email.setHostName(hostName);
		email.setAuthentication(userName, password);
		email.setSocketConnectionTimeout(connectiontimeout);
		email.setCharset("utf-8");
		if(tls){
			email.setStartTLSEnabled(tls);
		}
		if(ssl){
			email.setSSLOnConnect(ssl);
			email.setSslSmtpPort(sslport);
		} else {
			email.setSmtpPort(Integer.parseInt(port));
		}
		try {
			email.setFrom(fromAddress,userName);
		} catch (EmailException e) {
			throw new RuntimeException("邮件地址异常："+fromAddress, e);
		}
	}
	/**
	 * @param hostName 如：smtp.163.com
	 * @param userName 邮箱址@前边部分
	 * @param password 邮箱密码
	 * @param sendAddress 发送方邮件地址
	 * @throws Exception
	 */
	public SendMail(String hostName, String userName, String password, String sendAddress, int port) throws Exception {
		email.setHostName(hostName);
		email.setFrom(sendAddress, userName);
		email.setAuthentication(userName, password);
		email.setSmtpPort(port);
	}
	
	/**
	 * @param subject 邮件标题
	 */
	public void setSubject(String subject){
		email.setSubject(subject);
	}
	
	/**
	 * @param content 邮件内容
	 * @throws Exception
	 */
	public void setContent(String content) throws Exception{
		email.setTextMsg(content);
		email.setHtmlMsg(content);
	}
	
	/**
	 * @param reciever 接受方地址
	 * @throws Exception
	 */
	public void addTo(String reciever) throws Exception{
		email.addTo(reciever);
	}
	
	/**
	 * 设置邮件字符集
	 * @param charset
	 */
	public void setCharset(String charset){
		email.setCharset(charset);
	}
	
	/**
	 * @param attaPath 附件地址
	 * @param description 附件描述
	 * @param name 附件名
	 * @throws Exception
	 */
	public void addMultiPart(String attaPath,String description,String name) throws Exception{
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(attaPath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(description);
		attachment.setName(name);
		email.attach(attachment);
	}
	
	/**
	 * 需要将先执行setSubject、setContent、addTo方法
	 * @throws Exception
	 */
	public String send() throws Exception{
		String result = email.send();
		email.getToAddresses().clear();
		return result;
	}

	/**
	 * 发送邮件
	 * @throws Exception 
	 */
	public String send(String[] reciever, String subject, String content){
		try {
			for (int i = 0; i < reciever.length; i++) {
				email.addTo(reciever[i]);
			}
			email.setSubject(subject);
			email.setHtmlMsg(content);
			email.setTextMsg(content);
			String result = email.send();
			email.getToAddresses().clear();
			return result;
		} catch (EmailException e) {
			throw new AppExceptionRtImpl("发送邮件失败！",e);
		}
	}
	
	public static String getEmailTemplate(String fileName) {
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = SendMail.class.getClassLoader().getResourceAsStream("email/"+fileName+".templ");
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
				sb.append("\r\n");
			}
		} catch (IOException e) {
			logger.info("读取模板文件出错", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String [] args) throws Exception{
		SendMail sendMail = new SendMail();
		sendMail.send(new String[]{"lskj201506@163.com"}, "忘记密码提示(网络视频监控系统)", "4563");
	}
}
