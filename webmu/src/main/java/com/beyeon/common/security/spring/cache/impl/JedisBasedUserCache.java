package com.beyeon.common.security.spring.cache.impl;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSON;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.security.spring.detail.UserDetail;

public class JedisBasedUserCache implements UserCache {
    //~ Static fields/initializers =====================================================================================

    private static final Logger logger = LoggerFactory.getLogger(JedisBasedUserCache.class);
	private static final String USER = "REDIS_USER_";

    private CacheSupport cache;

    public CacheSupport getCache() {
        return cache;
    }

    public void setCache(CacheSupport cache) {
        this.cache = cache;
    }

    public UserDetails getUserFromCache(String username) {
        String element = null;

        try {
            element = cache.get(USER+username);
        } catch (Exception cacheException) {
            throw new DataRetrievalFailureException("Cache failure: " + cacheException.getMessage());
        }

        logger.debug("Cache hit: " + (element != null) + "; username: " + username);

        if (element == null) {
            return null;
        } else {
            return JSON.parseObject(element, UserDetail.class);
        }
    }

    public void putUserInCache(UserDetails user) {
        cache.set(USER+user.getUsername(),JSON.toJSONString(user));
    }

    public void removeUserFromCache(UserDetails user) {
        if (logger.isDebugEnabled()) {
            logger.debug("Cache remove: " + user.getUsername());
        }

        this.removeUserFromCache(user.getUsername());
    }

    public void removeUserFromCache(String username) {
        cache.del(USER + username);
    }
	
	public void removeAllUserDetails() {
		Set<String> keyset = this.cache.keys(USER+"*");
		String [] keys = new String[keyset.size()];
		keyset.toArray(keys);
		this.cache.del(keys);
	}
}
