package com.baoju.common.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.baoju.common.util.str.StringUtils;

public class DateDiffUtil {

	public static String dateDiffCurrent(Date startDate){
		
		String mes="";
		
		if(StringUtils.isNotEmpty(startDate)){
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			
			long diff=new Date().getTime()-startDate.getTime();
			
			//int month=1000*60*60*30;//月
			int day=1000*60*60*24; //天
			int hour=1000*60*60;//时
			int minute=1000*60;//分
			int second=1000; //秒
			
			long day_ = diff/day;
			long hour_ = diff%day/hour;
			long min_ = diff%day%hour/minute;
			long second_ = diff%day%hour%minute/second;
			
			if(day_>0){
				if(day_>15){
					mes=df.format(startDate);
				}else{
					 mes=day_+"天";
				}
				
			}else if(hour_>0){
				 mes=hour_+"小时";
			}else if(min_>0){
				 mes=min_+"分";
			}else if(second_>0){
				 mes=second_+"秒";
			}else{
				mes="刚刚";
			}
		}
		return mes;
	}
}
