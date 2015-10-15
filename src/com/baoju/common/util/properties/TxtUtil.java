package com.baoju.common.util.properties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.baoju.common.util.str.StringUtils;


public class TxtUtil {

	public static String getCode(String name){
		FileReader fr=null;
		BufferedReader br=null;
		try {
			String path=Class.class.getClass().getResource("/").getPath();
			fr=new FileReader(path+"config/properties/china_weather_city_code.txt");
		    br=new BufferedReader(fr);
		    String line="";
		    String [] arrs=null;
		    while((line=br.readLine())!=null){
		    	arrs=line.split("=");
		    	if(StringUtils.isNotEmpty(arrs[0]) && name.trim().equals(arrs[1].trim())){
		    		return arrs[0];
		    	}
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return "找不到对应的城市代码";
	}
	public static void main(String[] args) {
		System.out.println(getCode("广州"));
	}
}
