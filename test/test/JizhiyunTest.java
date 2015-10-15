package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.baoju.common.util.properties.SysConfigUtil;

import net.sf.json.JSONObject;

public class JizhiyunTest {

   public static final String ADD_URL = "http://api.gizwits.com/app/users";

   public static String getJSON() {

	   StringBuffer sb = new StringBuffer("");
	   
       try {
           //创建连接
           URL url = new URL(ADD_URL);
           HttpURLConnection connection = (HttpURLConnection) url
                   .openConnection();
           connection.setDoOutput(true);
           connection.setDoInput(true);
           connection.setRequestMethod("POST");
           connection.setUseCaches(false);
           connection.setInstanceFollowRedirects(true);
           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           connection.setRequestProperty("X-Gizwits-Application-Id", "55e333e7f5be4c8e8aa05cacccaf53f2");

           connection.connect();

           //POST请求
           DataOutputStream out = new DataOutputStream(
                   connection.getOutputStream());
           JSONObject obj = new JSONObject();
          // obj.element("phone_id", "ojcuMuCvdXNLoqgx6fGNmtDbPlDE");
           
           obj.element("phone_id", "13172055451");
          

           out.writeBytes(obj.toString());
           out.flush();
           out.close();

           //读取响应
           BufferedReader reader = new BufferedReader(new InputStreamReader(
                   connection.getInputStream()));
           String lines;
          
           while ((lines = reader.readLine()) != null) {
               lines = new String(lines.getBytes(), "utf-8");
               sb.append(lines);
           }
           System.out.println(sb);
           reader.close();
           // 断开连接
           connection.disconnect();
          
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return sb.toString();

   }

   public static void main(String[] args) {
	 //  String jsonstr=getJSON();
	 //  JSONObject jobj=new JSONObject();
	 //  JSONObject obj=jobj.fromObject(jsonstr);
	//   System.out.println(obj.get("token"));
	 //  System.out.println(obj.get("uid"));
	  
	   String name=(String) null;
	   System.out.println(name);
	   
	//   System.out.println(TimeStamp2Date(obj.get("expire_at").toString()));;
   }
   
   public static String TimeStamp2Date(String timestampString){    
	      Long timestamp = Long.parseLong(timestampString)*1000;    
	      String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));    
	      return date;    
	    }  

}