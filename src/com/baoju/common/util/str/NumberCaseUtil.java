package com.baoju.common.util.str;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 进制转换
 * @author demi
 *
 */
public class NumberCaseUtil {


	public static void main(String[] args) throws ParseException {

		System.out.println(toHexString(16777985));//1010502
       //
      /*  String sixteen=toHexString(51973945);
       System.out.println("转化成16进制"+sixteen);
       Integer ten= hexToDecimal(sixteen);
       System.out.println("转化成十进制："+ten);
       System.out.println(decimalCaseDateTime(ten));*/
       //开始时间：167772160  结束时间：503709696
		//269621507 18:40
		//;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//10:00 00000100
		//6:30 00000630
		//6:00 00000060
		Date date=df.parse("2015-04-08 19:00:00");
		//Integer time=getNowtimeToDecimal(new Date());
		//Integer time=getTimeToDecimal(date);
		//System.out.println("time:"+time);
		//time=appendZero(time.toString(), 8);
		//System.out.println("time："+time);
		
		System.out.println(getDecimalToTime(83886080));
		System.out.println(dateMinCaseTime(decimalCaseDateTime(923467776)));;
	}
	/*
	           设置月日时分为3月25日15时57分
		要先把每一字节换为16进制，例如03 25 15 57转为03 19 0F 39
		39 0F 19 03（这里换下大小端）
		然后再把390F1903作为一个数转为十进制957290755发送
		其他时间设置问题同理  
		
	 */
	
	/**
	 * 十六进制时间转换成顺序时间  （例如：991363072（长度9）转换成：00002359）
	 * @param decimal
	 * @return
	 */
	 public static String decimalCaseDateTime(Integer decimal){
			SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm:ss");
	       //10进制时间转换成可识别的时间
			String sixteen=toHexString(decimal);
			sixteen=appendZero(sixteen, 8);
			System.out.println("16进制："+sixteen);
			String ten="";
			for(int i=0;i<4;i++){
				ten=hexToDecimal(sixteen.substring(i*2, i*2+2))+ten;
				ten=appendZero(ten, i*2+2);
				//System.out.println(ten);
				
			}
			ten=appendZero(ten, 8);
			System.out.println("ten:"+ten);
			return ten;
		 }
	/**
	 * 十进制转成二进制
	 * @param x
	 * @return
	 */
	public static Long toBinary(int x){
		Long i = Long.valueOf(Long.toBinaryString(x));
		//System.out.println("十进制数："+x+"\n转换为二进制数后为："+i);
		return i;
	}
	
	/**
	 * 10进制转化成16进制
	 * @param x
	 * @return
	 */
	public static String toHexString(Integer x){
		 String str = Long.toHexString(x).toUpperCase();
	     //System.out.println(str);
	     return str;
	}
	
	/**
	 * 16转成10
	 * @param hex
	 * @return
	 */
	 public static Integer hexToDecimal(String hex){  
		  int decimalValue = 0;  
		  for(int i = 0;i<hex.length();i++){   
		   if(hex.charAt(i)>'9'){//这里改一下    
		    if(hex.charAt(i)>'F' || hex.charAt(i)<65){//这里改一下     
		     return 00000;
		    }    
		    else   
		     decimalValue =decimalValue+(int) (Math.pow(16, hex.length()-1-i)*(hex.charAt(i)-55));
		   }else{    
		    decimalValue =decimalValue+(int) (Math.pow(16, hex.length()-1-i)*(Integer.parseInt(hex.charAt(i)+"",10)));//这里改一下
		   }  
		  }  
		  return decimalValue; 
		 }
	 
	 /**
	  * 获取当前时间 MM-dd-HH-mm 转换成10进制
	  */
	 public static Integer getNowtimeToDecimal(Date dateTime){
		// SimpleDateFormat df=new SimpleDateFormat("MM-dd-HH-mm");
		 SimpleDateFormat df=new SimpleDateFormat("mm-HH-dd-MM");
	       String curDate=String.valueOf(df.format(dateTime));
	       //System.out.println("当前时间："+curDate);
	       String [] date=curDate.split("-");
	       String sixteen="";
	       for(int i=0;i<date.length;i++){
	    	   String ss=toHexString(Integer.valueOf(date[i].toString()));
	    	   ss=appendZero(ss, 2);
	    	   sixteen=sixteen+""+ss;
	    	  // System.out.println("i:"+date[i]+" >十六进制："+ss);
	       }
	       Integer ten= hexToDecimal(sixteen);
	       System.out.println("当前时间："+curDate+" 个个转换16进制： "+sixteen+"最后转化成十进制："+ten);
	       return ten;
	 }
	 
	 /**
	  * 时分转换 :00002359转换成23:59
	  * @param str
	  * @return
	  */
	public static String dateMinCaseTime(String str){
		//10:00 00000100
		//6:30 00000630
		//6:00 00000060
	  
		Integer intstr=Integer.parseInt(str);
		str=intstr.toString();
		int len=str.length();
		String res="";
		if(len<5){
			if(len==4){
				res=str.substring(0, 2)+":"+str.substring(2, 4);
			}else if(len==3){
				res="0"+str.substring(0, 1)+":"+str.substring(1, 3);
			}else if(len==2){
				res="00:"+str.substring(0, 1)+str.substring(1, 2);
			}else{
				res="00:0"+str.substring(0, 1);
			}
		}
		System.out.println("结果："+res);
		return res;
	}
	/**
	 * Date类型(时分)的日期转换成十进制
	 * @param dateTime
	 * @return
	 */
	 public static Integer getTimeToDecimal(Date dateTime){
			// SimpleDateFormat df=new SimpleDateFormat("MM-dd-HH-mm");
			 SimpleDateFormat df=new SimpleDateFormat("mm-HH-00-00");
		       String curDate=String.valueOf(df.format(dateTime));
		       System.out.println("当前时间："+curDate);
		       String [] date=curDate.split("-");
		       String sixteen="";
		       for(int i=0;i<date.length;i++){
		    	   String ss=toHexString(Integer.valueOf(date[i].toString()));
		    	   ss=appendZero(ss, 2);
		    	   sixteen=sixteen+""+ss;
		    	  // System.out.println("i:"+date[i]+" >十六进制："+ss);
		       }
		       Integer ten= hexToDecimal(sixteen);
		       System.out.println("当前时间："+curDate+" 个个转换16进制： "+sixteen+"最后转化成十进制："+ten);
		       return ten;
		 }
	 /**
		 * Date类型(时分)的日期转换成十进制
		 * @param dateTime
		 * @return
		 */
		 public static String getDecimalToTime(Integer Decimal){
				// SimpleDateFormat df=new SimpleDateFormat("MM-dd-HH-mm");
			 Calendar c = Calendar.getInstance();
			 c.setTime(new Date());			
			 String t=toHexString(Decimal);
			 //System.out.print(Decimal+"整体转十六进制："+t);
			 t=appendZero(t,8);
			 
				String time= c.get(Calendar.YEAR)+"-";
				time+=appendZero(hexToDecimal(t.substring(6, 8)).toString(),2)+"-";
				time+=appendZero(hexToDecimal(t.substring(4, 6)).toString(),2)+"  "; 
				time+=appendZero(hexToDecimal(t.substring(2, 4)).toString(),2)+":"; 
				time+=appendZero(hexToDecimal(t.substring(0, 2)).toString(),2); 
			       return time;
			 }
	 public static String appendZero(String str,int lenth){
		 for (int i = str.length(); i < lenth; i++) {
			str="0"+str;
		}
		 return str;
	 }
	 
	 /**
	  * 二进制数据倒叙排列
	  * @param send
	  * @return
	  */
	 public static String getBintryBack(String send){
		 String newstr="";
			for (int i = 0; i < send.length(); i++) {
				newstr=send.charAt(i)+newstr;
				//System.out.println(i+"="+send.charAt(i));
			}
			System.out.println(newstr);
			/*for (int i = 0; i < newstr.length(); i++) {
				System.out.println(i+"=="+newstr.charAt(i));
			}*/
			return newstr;
	 }
	 
}
