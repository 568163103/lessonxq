package com.beyeon.common.security.spring.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public abstract class ResourceCache {
	private Set<String> urls = new HashSet<String>();

	public abstract Collection getResourceFromCache(String url);
	
	public void putResourceInCache(String url, Collection<GrantedAuthority> authorities){
		int indexOff = url.indexOf("?");
		if(url.indexOf("index.do?parentId") == -1 && indexOff > 0){
			url = url.substring(0, indexOff);
		}
		this.urls.add(url);
		handlePutResourceInCache(url, authorities);
	}

	protected abstract void handlePutResourceInCache(String url, Collection<GrantedAuthority> authorities);
	
	public Set<String> getAllResources(){
		return this.urls;
	}

	public void removeResourceFromCache(String url){
		this.urls.remove(url);
		handleRemoveResourceFromCache(url);
	}

	protected abstract void handleRemoveResourceFromCache(String url);

	public void removeAllResources(){
		this.urls.clear();
		handleRemoveAllResources();
	}

	protected abstract void handleRemoveAllResources();
	
}