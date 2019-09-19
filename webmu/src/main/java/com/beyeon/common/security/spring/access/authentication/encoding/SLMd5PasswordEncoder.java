package com.beyeon.common.security.spring.access.authentication.encoding;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.nvss.log.model.bpo.UserTraceBpo;
import com.socket.sip.SpringInit;

/**
 * User: lwf
 * Date: 14-3-20
 * Time: 下午3:00
 */
public class SLMd5PasswordEncoder extends Md5PasswordEncoder {
	public SLMd5PasswordEncoder() {
		super();
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		if(StringUtils.isBlank(encPass) || StringUtils.isBlank(rawPass)){
			return false;
		}
		String signCode = (String) ResourceUtil.getRequestContext("signCode");
		encPass = encodePassword(encPass+signCode, salt);
		if(encPass.equals(rawPass)){
			return true;
		} else {			
			return false;
		}
	}
}
