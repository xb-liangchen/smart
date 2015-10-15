package com.baoju.common.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class SysConfigUtil {

	public static String getString(String key){
		ResourceBundle rb=ResourceBundle.getBundle("config/properties/sysconfig");
		return rb.getString(key);
	}
	
	public static String getString2(String key){
		Properties pro=new Properties();
		InputStream in= Class.class.getClass().getResourceAsStream("/config/properties/sysconfig.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return pro.getProperty(key).toString();
	}
	
	public static void main(String[] args) {
	//	System.out.println(getString("uploadpath"));
		///System.out.println(getString2("uploadpath"));
		String me= getString("develop.copyright");
		System.out.println(me);
	}
}
