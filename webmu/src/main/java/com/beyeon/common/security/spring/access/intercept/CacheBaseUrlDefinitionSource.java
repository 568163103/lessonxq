package com.beyeon.common.security.spring.access.intercept;

import java.util.Collection;
import java.util.Iterator;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.beyeon.common.security.spring.SecurityHelper;
import com.beyeon.common.security.spring.cache.SecurityCacheManager;
import com.beyeon.common.security.spring.provider.ResourceProvider;


/**
 * 在resourceCache中获取当前调用方法相对应类型Url 的Resouce实例
 * lookupAttributes(String url) 方法被{@link org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource} 调用
 * useAntPath 是否选用ant path的匹配模
 * protectAllResource 是否默认情况下所有的资源都需要受保护
 * convertUrlToLowercaseBeforeComparison 是否把Url转为小写后再进行比较
 * @author liwf
 */
public class CacheBaseUrlDefinitionSource implements FilterInvocationSecurityMetadataSource {
	private Logger logger =LoggerFactory.getLogger(getClass());
	private boolean convertUrlToLowercaseBeforeComparison = false;
	
	private boolean useAntPath = true;
	
	private boolean protectAllResource = false;
	
	private final PathMatcher pathMatcher = new AntPathMatcher();
	
	private final PatternMatcher matcher = new Perl5Matcher();
	
	private ResourceProvider resourceProvider;
	private SecurityCacheManager securityCacheManager;

	public void setResourceProvider(ResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}

	public void setSecurityCacheManager(SecurityCacheManager securityCacheManager) {
		this.securityCacheManager = securityCacheManager;
	}

	/**
	 * 返回当前URL对应的权
	 * @see org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource#lookupAttributes(String url, String method)
	 */
	public Collection<ConfigAttribute> lookupAttributes(String url) {
		Collection<String> resources = securityCacheManager.getAllResources();
		
		Collection authorities = null;
		for (Iterator iterator = resources.iterator(); iterator.hasNext();) {
			String resString = (String) iterator.next();
			//以首先匹配成功的资源的权限作为当前Url的权
			if (isResourceMatch(isUseAntPath(),url,resString+"*")) {
				authorities = resourceProvider.getResourceDetail(resString);
				break;
			}
		}

		return SecurityHelper.getCadByAuthorities(authorities, isProtectAllResource());
	}
	
	/**
	 * 根据是否使用UseAntPath和是否字符小写化来预先格式化url
	 * @param url
	 * @param isUseAntPath
	 * @param isToLowercase
	 * @return
	 */
	private String preDealUrl(String url, boolean isUseAntPath,boolean isToLowercase){
		if (isUseAntPath) {
			// Strip anything after a question mark symbol, as per SEC-161.
			int firstQuestionMarkIndex = url.lastIndexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}
		if (isToLowercase) {
			url = url.toLowerCase();
		}
		return url;
	}	
	
	/**
	 * 查看当前url和资源中的url是否匹配
	 * @param isUseAntPath
	 * @param runningUrl
	 * @param rescUrl
	 * @return
	 * @throws Exception 
	 */
	private boolean isResourceMatch(boolean isUseAntPath,String runningUrl, String rescUrl){
		if (isUseAntPath) {
			return pathMatcher.match(rescUrl.replaceAll("\\?","P"), runningUrl.replaceAll("\\?","P"));
		} else {
			Pattern compiledPattern;
			Perl5Compiler compiler = new Perl5Compiler();
			try {
				compiledPattern = compiler.compile(rescUrl, Perl5Compiler.READ_ONLY_MASK);
			} catch (MalformedPatternException mpe) {
				throw new RuntimeException("Malformed regular expression: " + rescUrl, mpe);
			}
			return matcher.matches(runningUrl, compiledPattern);
		}
	}

	public Collection getAllConfigAttributes() {
		return null;
	}
	
	//---------getters and setters---------------------
	public void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	public void setProtectAllResource(boolean protectAllResource) {
		this.protectAllResource = protectAllResource;
	}

	public boolean isProtectAllResource() {
		return protectAllResource;
	}
	
	public Collection<ConfigAttribute> getAttributes(Object object) throws RuntimeException {
        if ((object == null) || !this.supports(object.getClass())) {
            throw new RuntimeException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();

        return lookupAttributes(url);
    }

	public boolean supports(Class clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
