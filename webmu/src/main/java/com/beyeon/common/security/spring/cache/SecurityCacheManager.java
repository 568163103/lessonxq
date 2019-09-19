package com.beyeon.common.security.spring.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.NullUserCache;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.security.spring.detail.ResourceDetail;
import com.beyeon.common.security.spring.provider.dao.ResourceDaoImpl;
import com.beyeon.common.security.spring.provider.dao.UserDaoImpl;

/**
 * AcegiCacheManager是对缓存进行统一管理，以屏蔽其它类对缓存的直接操作
 * 对缓存中的用户和资源进行初始化、增、删、改、清空等操作
 * @author liwf
 *
 */
@Cache(cacheName = "权限管理")
public class SecurityCacheManager {	
	private UserDaoImpl userDao;
	private ResourceDaoImpl resourceDao;
	
	private UserCache userCache = new NullUserCache();
	private ResourceCache resourceCache;
	private Collection<String> resources;
	
	//-------getters and setters-------------------
	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	public void setResourceDao(ResourceDaoImpl resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Cache(type = Cache.REFRESH,refreshExpre = Cache.BH,cacheName = "权限")
	public void init() {
		if(! (userCache instanceof NullUserCache)){
			List<UserDetails> users =userDao.getUserDetails();
			for (UserDetails user : users) {
				userCache.putUserInCache(user);
			}
		}
		List<ResourceDetail> rescs =resourceDao.getResourceDetails();
		resourceCache.removeAllResources();
		for (Iterator<ResourceDetail> iter = rescs.iterator(); iter.hasNext();) {
			ResourceDetail resc = iter.next();
			resourceCache.putResourceInCache(resc.getUrl(), Arrays.asList(resc.getAuthorities()));
		}
		resources = orderResources(resourceCache.getAllResources());
	}

	public Collection<String> getAllResources() {
		return resources;
	}
	
	//倒叙排序获取的url
	private Collection<String> orderResources(Collection<String> resources) {
		List<String> urls = new ArrayList<String>(resources);
		Collections.sort(urls);
		Collections.reverse(urls);
		return urls;
	}
}
