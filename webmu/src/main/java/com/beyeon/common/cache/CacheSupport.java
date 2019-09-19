package com.beyeon.common.cache;

import java.util.Set;

public interface CacheSupport {
	String get(String s);

	String set(String s, String s1);

	String setex(String s, int coreConfInt, String loginTime);

	Set<String> keys(String s);

	Long del(String s);

	Long del(String[] keys);
}
