package com.sso.dao;


public interface JedisClient {
	String get(String key);
	String set(String key, String value);
	String hget(String hkey, String key);
	long hset(String hkey, String key, String value);
	/**
	 * 递增
	 * @param key
	 * @return
	 */
	long incr(String key);
	/**
	 * 设置过期时间
	 * @param key
	 * @param second
	 * @return
	 */
	long expire(String key, int second);
	/**
	 * 返回过期时间
	 * @param key
	 * @return
	 */
	long ttl(String key);

	long del(String key);
}
