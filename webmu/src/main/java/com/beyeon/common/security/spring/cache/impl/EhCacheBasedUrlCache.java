package com.beyeon.common.security.spring.cache.impl;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

import com.beyeon.common.security.spring.cache.UrlCache;

public class EhCacheBasedUrlCache implements UrlCache {
	private static final Logger logger = LoggerFactory.getLogger(EhCacheBasedUrlCache.class);
	Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}

	public Integer getUrlFromCache(String url) {
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
			return (Integer) element.getValue();
		}
	}
	
	public void putUrlInCache(String url, Integer mid) {
		Element element = new Element(url,mid);
		this.cache.put(element);
	}

	public void removeUrlFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		this.cache.remove(resString);
	}

	public List getAllUrls() {
		return this.cache.getKeys();
	}
	
	public void removeAllUrls() {
		this.cache.removeAll();
	}

}