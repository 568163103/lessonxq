package com.beyeon.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.config.ResourceUtil;

public class AppConfTag extends TagSupport {
	private String access;

	public void setAccess(String access) {
		this.access = access;
	}

	public int doStartTag() throws JspException {
		if (StringUtils.isBlank(access)){
			return super.doStartTag();
		}
		if(access.indexOf("!") == 0)
			return contains() ? SKIP_BODY : EVAL_BODY_INCLUDE;
		else
			return contains() ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	private boolean contains(){
		boolean isContains = false;
		String industry = ResourceUtil.getIndustry();
		if(access.contains(industry)){
			isContains = true;
		}
		return isContains;
	}
}
