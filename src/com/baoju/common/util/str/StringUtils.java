package com.baoju.common.util.str;

import java.util.Random;
import java.util.UUID;

import org.aspectj.lang.annotation.DeclareWarning;

/**
 * 字符串处理工具
 * 
 * @author xinbing
 *
 */
public class StringUtils {
	private static String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 判断对象是否不为空
	 * 
	 * @param obj
	 * @return true-不为空 false-为空
	 */
	@Deprecated
	public static boolean isNotEmpty(Object obj) {
		if (null == obj || "".equals(obj) || "null".equals(obj)) {
			return false;
		}
		return true;
	}
	

	/**
	 * 判断输入的字符串是否有为空值的，参数为可变参数；
	 * 如果为有为空的字符串，返回true，并打印出是第几个字符串出错了（0为下标），方便调试；
	 * 否则返回false;
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isEmpty(String... strs) {
		boolean bool = false;
		if (strs == null || strs.length == 0) {
			bool = true;
		} else {
			for (int i = 0; i < strs.length; i++) {
				if (strs[i] == null || strs[i].trim().isEmpty() || strs[i].equals("null")) {
					bool = true;
System.out.println("第" + i + "个字符串为空或者为null");
					break;
				}
			}
		}
		return bool;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            要生成的字符串长度
	 * @return
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 处理从数据库中拿出来的时间数据尾部会多一个.0的状况
	 * 
	 * @param dateString
	 * @return
	 */
	public static String formatTimestamp(String dateString) {
		if (dateString != null) {
			dateString = dateString.replace(".0", "");
		}
		return dateString;
	}

	/**
	 * uuid生成器 函数返回的是32位定长的小写字符串
	 * 
	 * @return
	 */
	public static String uuidGenerator() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		
	}


	public static Long[] changeToLongArray(String[] sceneIds) {
		Long[] array = new Long[sceneIds.length];
		for(int i=0;i<sceneIds.length;i++) {
			array[i] = Long.parseLong(sceneIds[i]);
		}
		return array;
	}
}
