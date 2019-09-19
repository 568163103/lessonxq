package com.beyeon.common.security.spring.cache;

import java.util.List;

public interface UrlCache {
	Integer getUrlFromCache(String url);

	void putUrlInCache(String url, Integer mid);
	
	void removeUrlFromCache(String url);
	
	List getAllUrls();

	void removeAllUrls();
	
}