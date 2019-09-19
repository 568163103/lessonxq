package com.beyeon.common.cache.impl;

import java.util.Arrays;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import org.springframework.dao.DataRetrievalFailureException;

import com.beyeon.common.cache.CacheSupport;

/**
 * User: Administrator
 * Date: 2015/10/14
 * Time: 9:12
 */
public class EhcacheSupport implements CacheSupport {
	Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Cache getCache() {
		return this.cache;
	}

	@Override
	public String get(String s) {
		Element element = null;
		try {
			element = cache.get(s);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("Cache failure: "
					+ cacheException.getMessage(), cacheException);
		}

		if (element == null) {
			return null;
		} else {
			return (String) element.getObjectValue();
		}
	}

	@Override
	public String set(String key, String value) {
		Element element = new Element(key,value);
		this.cache.put(element);
		return value;
	}

	@Override
	public String setex(String key, int seconds, String value) {
		Element element = new Element(key,value);
		element.setTimeToLive(seconds);
		this.cache.put(element);
		return value;
	}

	@Override
	public Set<String> keys(String s) {
		return null;
	}

	@Override
	public Long del(String key) {
		return Long.valueOf(this.cache.remove(key)?1:0);
	}

	@Override
	public Long del(String[] keys) {
		this.cache.removeAll(Arrays.asList(keys));
		return Long.valueOf(keys.length);
	}
}
