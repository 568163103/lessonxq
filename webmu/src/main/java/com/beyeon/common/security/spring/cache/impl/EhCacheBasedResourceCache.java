package com.beyeon.common.security.spring.cache.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;

import com.beyeon.common.security.spring.cache.ResourceCache;

public class EhCacheBasedResourceCache extends ResourceCache {
	private static final Logger logger = LoggerFactory.getLogger(EhCacheBasedResourceCache.class);
	Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}

	public Set<GrantedAuthority> getResourceFromCache(String url) {
		Element element = null;
		try {
			element = cache.get(url);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (Set<GrantedAuthority>) element.getValue();
		}
	}

	protected void handlePutResourceInCache(String key,Collection<GrantedAuthority> authorities) {
		Element element = this.cache.get(key);
		if (null == element){
			element = new Element(key,new HashSet());
			this.cache.put(element);
		}
		((Set<GrantedAuthority>) element.getValue()).addAll(authorities);
	}

	protected void handleRemoveResourceFromCache(String url) {
		this.cache.remove(url);
	}

	protected void handleRemoveAllResources() {
		this.cache.removeAll();
	}

}