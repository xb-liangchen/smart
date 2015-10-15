package com.baoju.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class SystemCache {

	private static Logger log = LoggerFactory.getLogger(SystemCache.class.getName());  
	
	private static SystemCache instance = null;
	
	//微信access_token
	public static String access_token="";
	//access_token的有效时间为：7200秒(2小时)
	public static long access_token_time=0L;
	//微信js_ticket（JS SDK）
	public static String js_ticket="";
	//js_ticket的有效时间为：7200秒(2小时)
	public static long js_ticket_time=0L;
	
	//设置缓存
	private static Map<String, Object> systemCache = new HashMap<String, Object>();
	
	
	public static SystemCache getInstance() {
		if (instance == null)
			instance = new SystemCache();
		return instance;
	}
	
	/**
	 * 清除系统缓存,根据key
	 * @param sessionId
	 */
	public static void clearSystemCache(String key){
		systemCache.remove(key);
	}
	
	/**
	 * 设置系统缓存
	 * @param sessionId
	 */
	public static void addSystemCache(String key,Object value){
		systemCache.put(key, value);
	}
	
	/**
	 * 设置系统缓存
	 * @param sessionId
	 */
	public static Object getSystemCache(String key){
		return systemCache.get(key);
	}
	
}
