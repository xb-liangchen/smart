package com.baoju.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampConvertDate {

	/**
	 * Unix timestamp转换成北京时间
	 * @param timestampString Unix时间 例如：1426744492
	 * @return 北京时间yyyy-MM-dd HH:mm:ss
	 */
	 public static String timestamp2Date(String timestampString){    
	      Long timestamp = Long.parseLong(timestampString)*1000; 
	      System.out.println("timestamp:="+timestamp);
	      String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));    
	      return date;    
	 }  
	 public static String date2Timestamp(String date){    
	      try {
			Date date1= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			return ""+(date1.getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	      
	      return "";    
	 }  
	 public static String curDateToTimestamp(){    
			Date date1= new Date();
			return ""+(date1.getTime()/1000);
	 }  
	 
	 public static void main(String[] args) {
		 //335544320
		 String date=timestamp2Date("1428241764");//335544320
		 System.out.println("==="+date);
		 //2015-04-16 16:30:48
		 Long time=Long.parseLong(date2Timestamp("2015-04-15 16:42:48"));
		 System.out.println("===time:"+time);
		 
		 Long curtime=Long.parseLong(curDateToTimestamp());
		 System.out.println("curtime:"+curtime);
		 System.out.println(time-curtime);
	}
	 
}
