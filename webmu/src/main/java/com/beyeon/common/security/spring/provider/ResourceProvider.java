package com.beyeon.common.security.spring.provider;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.beyeon.common.security.spring.cache.ResourceCache;
import com.beyeon.common.security.spring.provider.dao.ResourceDaoImpl;

/**
 * ResourceManager是对缓存进行统一管理，以屏蔽其它类对缓存的直接操作
 * 对缓存中的资源进行初始化、增、删、改、清空等操作
 * @author liwf
 */
public class ResourceProvider {
	private Logger logger =LoggerFactory.getLogger(getClass());
	private ResourceDaoImpl resourceDao;
	private ResourceCache resourceCache;

	public void setResourceDao(ResourceDaoImpl resourceDao) {
		this.resourceDao = resourceDao;
	}

	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}
	
	public Collection<GrantedAuthority> getResourceDetail(String resource) {
		Collection<GrantedAuthority> authorities = resourceCache.getResourceFromCache(resource);
		if(null == authorities){
			logger.debug("库中查询:"+resource);
			authorities = resourceDao.getGrantedAuthoritys(resource);
			if(null != authorities){
				resourceCache.putResourceInCache(resource, authorities);
			}
		}
		return authorities;
	}
}
