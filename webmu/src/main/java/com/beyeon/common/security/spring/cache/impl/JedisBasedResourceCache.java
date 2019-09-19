package com.beyeon.common.security.spring.cache.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.alibaba.fastjson.JSON;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.security.spring.cache.ResourceCache;

public class JedisBasedResourceCache extends ResourceCache {
	private static final Logger logger = LoggerFactory.getLogger(JedisBasedResourceCache.class);
	private static final String resource = "REDIS_RESOURCE_";
	CacheSupport cache;

	public void setCache(CacheSupport cache) {
		this.cache = cache;
	}

	public CacheSupport getCache() {
		return this.cache;
	}

	public Set<GrantedAuthority> getResourceFromCache(String key) {
		String element = null;
		try {
			element = cache.get(resource+key);
		} catch (Exception cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return new HashSet(JSON.parseArray(element, SimpleGrantedAuthority.class));
		}
	}

	protected void handlePutResourceInCache(String key,Collection<GrantedAuthority> authorities) {
		String element = null;
		try {
			element = cache.get(resource+key);
		} catch (Exception cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage(), cacheException);
		}
		Set<GrantedAuthority> elementSet = null;
		if (null == element){
			elementSet = new HashSet<GrantedAuthority>();
		} else {
			elementSet = new HashSet(JSON.parseArray(element, SimpleGrantedAuthority.class));
		}
		elementSet.addAll(authorities);
		this.cache.set(resource+key,JSON.toJSONString(elementSet));
	}

	protected void handleRemoveResourceFromCache(String key) {
		this.cache.del(resource+ key);
	}

	protected void handleRemoveAllResources() {
		Set<String> keyset = this.getAllResources();
		String [] keys = new String[keyset.size()];
		int i = 0;
		for (String key : keyset) {
			keys[i] = resource+key;
			i++;
		}
		this.cache.del(keys);
	}

}