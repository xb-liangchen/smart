package com.baoju.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ObjCaseUtil {

	public static HashMap<String,Object> objTohash(Object obj) throws IllegalArgumentException, IllegalAccessException{
		HashMap<String,Object> map=new HashMap<String, Object>();
		Class cla=obj.getClass();
		List<Class> list=new ArrayList<Class>();
		do {
			list.add(cla);
			cla=cla.getSuperclass();
			
		} while (!cla.equals(Object.class));
		
		for (Class class1 : list) {
		 java.lang.reflect.Field [] fileds=	class1.getDeclaredFields();
		 for (Field field : fileds) {
			Object objval=null;
			field.setAccessible(true);
			objval=field.get(obj);
			map.put(field.getName(), objval);
		}
		}
		return map;
	}
	public static String objToJson(Object obj){
		try {
			return hashMapToJson(objTohash(obj));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	  /**把数据源HashMap转换成json
     * @param map 
     */ 
    public static String hashMapToJson(HashMap map) { 
        String string = "{"; 
        for (java.util.Iterator it = map.entrySet().iterator(); it.hasNext();) { 
            Entry e = (Entry) it.next(); 
            string += "'" + e.getKey() + "':"; 
            string += "'" + e.getValue() + "',"; 
        } 
        string = string.substring(0, string.lastIndexOf(",")); 
        string += "}"; 
        return string; 
    }
    public static void main(String[] args) {
		Object a=null;
		System.out.println((String)a);
		System.out.println(String.valueOf(a));
		System.out.println(a.toString());
	}
}
