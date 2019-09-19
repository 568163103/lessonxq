package com.beyeon.common.security.spring;


import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.beyeon.common.security.spring.access.intercept.CacheBaseUrlDefinitionSource;


/**
 * 对用户授权进行认证的Tag, 如果用户没相应授权，则Tag中的Html代码全部不显示
 * @author liwf
 */
public class SecurityTag extends TagSupport {

	private static final long serialVersionUID = 0L;

	private String resc = "";
	// ~ Methods
	// ========================================================================================================

	public int doStartTag() throws JspException {
		ServletContext sc = pageContext.getServletContext();
		WebApplicationContext webAppCtx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		AffirmativeBased accessDecisionManager = 
			(AffirmativeBased) webAppCtx.getBean("filterAccessDecisionManager");
		CacheBaseUrlDefinitionSource filterDefinitionSource = 
			(CacheBaseUrlDefinitionSource) webAppCtx.getBean("urlDefinitionSource");
		//找出相应的资源
		Collection<ConfigAttribute> attrs = filterDefinitionSource.lookupAttributes(resc);

		if (attrs != null) {
			//判断权限
			try {
				accessDecisionManager.decide(getPrincipalAuthorities(), null, attrs);
			} catch (AccessDeniedException e1) {
				return Tag.SKIP_BODY;
			}
		}
		return Tag.EVAL_BODY_INCLUDE;
	}
	/**
	 * 获取当前用户授权
	 * @return
	 */
	private Authentication getPrincipalAuthorities() {
		return SecurityContextHolder.getContext()
				.getAuthentication();
	}

	public void setResc(String resc) {
		this.resc = resc;
	}

}
