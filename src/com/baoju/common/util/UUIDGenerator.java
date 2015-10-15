package com.baoju.common.util;
import java.util.UUID;

/**
 * @ClassName UUIDGenerator
 * @author 
 * @date 
 * @Description UUID生成器
 */
public class UUIDGenerator {
	public static String getUUID(){     
		String s = UUID.randomUUID().toString(); 
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);     
	} 
	public static void main(String[] args) {
		System.out.println(getUUID());
		String placeholder = UUID.randomUUID().toString();// 生成一个随机的参数名称
		System.out.println("placeholder="+placeholder+"  \n"+placeholder.replace("-", ""));
	}
}
