/*
    ShengDao Android Client, LruCacheManager
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.core.common;

import android.util.LruCache;

/**
 * [A brief description]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-11-6
 * 
 **/
public class LruCacheManager {

	/**
	 * lruCache
	 */
	private LruCache<String, Object> lruCache;
	/**
	 * instance
	 */
	private static LruCacheManager instance;
	/**
	 * cache size
	 */
	private final int CACHE_SIZE = 50;

	
	/**
	 *  LruCacheManager constructor
	 */
	private LruCacheManager() {
		this.lruCache = new LruCache<String, Object>(CACHE_SIZE);
	}

	/**
	 * get the LruCacheManager instance, it is the singleton
	 * 
	 * @return LruCacheManager
	 */
	public static LruCacheManager getInstance() {
		if (instance == null) {
			synchronized (LruCacheManager.class) {
				if (instance == null) {
					instance = new LruCacheManager();
				}
			}
		}
		return instance;
	}

	/**
	 * put
	 * @param key String
	 * @param value Object
	 */
	public void put(String key, Object value) {
		lruCache.put(key, value);
	}

	/**
	 * get
	 * @param key String
	 * @return Object
	 */
	public Object get(String key) {
		return lruCache.get(key);
	}

	/**
	 * remove 
	 * @param key String
	 * @return Object
	 */
	public Object remove(String key) {
		return lruCache.remove(key);
	}

	/**
	 * evictAll
	 */
	public void evictAll() {
		lruCache.evictAll();
	}

	/**
	 * maxSize
	 * @return int
	 */
	public int maxSize() {
		return lruCache.maxSize();
	}

	/**
	 * size
	 * @return int
	 */
	public int size() {
		return lruCache.size();
	}

	/**
	 * trimToSize
	 * @param maxSize
	 */
	public void trimToSize(int maxSize) {
		lruCache.trimToSize(maxSize);
	}
}
