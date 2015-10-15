package com.baoju.common.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class MessConfigUtil {

	public static String getString(String key){
		ResourceBundle rb=ResourceBundle.getBundle("config/properties/message");
		return rb.getString(key);
	}
	
	
	public static void main(String[] args) {
	//	System.out.println(getString("uploadpath"));
		///System.out.println(getString2("uploadpath"));
		String me= getString("develop.copyright");
		System.out.println(me);
	}
}
