package com.beyeon.common.security.spring.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.security.spring.cache.UrlCache;

public class JedisBasedUrlCache implements UrlCache {
	private static final Logger logger = LoggerFactory.getLogger(JedisBasedUrlCache.class);
	private static final String URL = "REDIS_URL_";
	CacheSupport cache;

	public void setCache(CacheSupport cache) {
		this.cache = cache;
	}

	public CacheSupport getCache() {
		return this.cache;
	}

	public Integer getUrlFromCache(String url) {
		String element = null;

		try {
			element = cache.get(URL+url);
		} catch (Exception cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return Integer.parseInt(element);
		}
	}
	
	public void putUrlInCache(String url, Integer mid) {
		this.cache.set(URL+url,""+mid);
	}

	public void removeUrlFromCache(String resString) {
		if (logger.isDebugEnabled()) {
			logger.debug("Cache remove: " + resString);
		}
		this.cache.del(URL+resString);
	}

	public List<String> getAllUrls() {
		Set<String> set = this.cache.keys(URL+"*");
		List<String> list = new ArrayList<String>();
		for (String object : set) {
			list.add(object.substring(URL.length()));
		}
		return list;
	}
	
	public void removeAllUrls() {
		Set keyset = this.cache.keys(URL+"*");
		String [] keys = new String[keyset.size()];
		keyset.toArray(keys);
		this.cache.del(keys);
	}

}