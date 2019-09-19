package com.beyeon.common.security.spring;

import java.io.IOException;

import org.springframework.security.taglibs.authz.JspAuthorizeTag;

/**
 * Access control tag which evaluates its body based either on
 * <ul>
 * <li>an access expression (the "access" attribute), or</li>
 * <li>by evaluating the current user's right to access a particular URL (set using the "url" attribute).</li>
 * </ul>
 * @author Luke Taylor
 * @since 3.0
 */
public class AuthorizeTag extends JspAuthorizeTag {
	public boolean authorizeUsingUrlCheck() throws IOException {
		if(getUrl().indexOf("!") == 0)
			return !super.authorizeUsingUrlCheck();
		else
			return super.authorizeUsingUrlCheck();
	}
}
