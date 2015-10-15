package com.baoju.common.util.url;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.baoju.common.util.Consts;
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.str.ThrowableToStr;
import com.baoju.common.util.wechat.SSLUtil;
import com.baoju.weixin.model.HttpRespObject;

import net.sf.json.JSONObject;

public class HttpConnUtil {

	private static Logger logger=Logger.getLogger(HttpConnUtil.class);
	
	/**
	 * 主动请求微信平台响应json数据
	 * @param action  URL
	 * @param methodType POST或GET
	 * @param obj  JSONObject (请求体)
	 * @return  jsonstr
	 */ 
	public static String getRespJsonStr(String URL,String methodType,JSONObject obj) {
		 
		String message="";
		try {
			 URL url = new URL(URL);
	         HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
	           
	         //http.setRequestMethod("GET");
	         if(methodType==null){
	        	 http.setRequestMethod("GET");
	         }else{
	        	 http.setRequestMethod("GET".equals(methodType.toUpperCase())?"GET":"POST");
	         }
	        
	         http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
	         http.setDoOutput(true);       
	         http.setDoInput(true);
	         
	         System.setProperty("sun.net.client.defaultConnectTimeout", "40000");//连接超时30秒
	         System.setProperty("sun.net.client.defaultReadTimeout", "40000"); //读取超时30秒
	         http.connect();
	         
	         OutputStream os= http.getOutputStream();   
	         os.write(obj.toString().getBytes("UTF-8"));//传入参数   
	         os.flush();
	         os.close();
	       
	         InputStream is =http.getInputStream();
	         int size =is.available();
	         byte[] jsonBytes =new byte[size];
	         is.read(jsonBytes);
	         message=new String(jsonBytes,"UTF-8");
	       //  System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("e:"+ThrowableToStr.getTrace(e));
		}
		
			return message;
         
	}
	
	/**
	 * 主动请求微信平台响应json数据
	 * @param action  URL
	 * @return  jsonstr
	 */ 
	public static String getRespJsonStr2(String URL) {
		 
		String message="";
		CloseableHttpClient httpclient = SSLUtil.createSSLClientDefault();  
		HttpGet httpget = new HttpGet(URL);  
		StringBuilder entityStringBuilder=new StringBuilder();
		CloseableHttpResponse httpResponse = null;
		BufferedReader bufferedReader = null;
		try {
			httpResponse = httpclient.execute(httpget);
			//得到httpResponse的状态响应码
	        int statusCode=httpResponse.getStatusLine().getStatusCode();
	        if (statusCode==HttpStatus.SC_OK) {
	            //得到httpResponse的实体数据
	            HttpEntity httpEntity=httpResponse.getEntity();
	            if (httpEntity!=null) {
	                try {
	                    bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024);
	                    String line=null;
	                    while ((line=bufferedReader.readLine())!=null) {
	                        entityStringBuilder.append(line+"/n");
	                    }
	                    //利用从HttpEntity中得到的String生成JsonObject
	                    message=entityStringBuilder.toString();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("e:"+ThrowableToStr.getTrace(e));
		}finally {  
		    try {
		    	httpResponse.close();
		    	httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		
			return message;
         
	}
	
	/**
	 * 获取机智云响应json数据
	 * @param URL  地址
	 * @param obj  JSONObject 参数
	 * @return  jsonstr
	 */
	public static String getGizwitsRespJSON(String action,JSONObject obj,Map<String, String> headers,String method) {

		   StringBuffer sb = new StringBuffer("");
		   DataOutputStream out=null;
		   BufferedReader reader=null;
		   HttpURLConnection connection =null;
	       try {
	           //创建连接
	           URL url = new URL(action);
	           connection = (HttpURLConnection) url.openConnection();
	           connection.setDoOutput(true);
	           connection.setDoInput(true);
	           connection.setRequestMethod(method==null?"GET":method);
	           connection.setUseCaches(false);
	           connection.setInstanceFollowRedirects(true);
	         //  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	           connection.setRequestProperty("Content-Type","application/json");
	           connection.setRequestProperty("X-Gizwits-Application-Id",  SysConfigUtil.getString("gizwits.appid"));

	           if(headers!=null && headers.size()>0){
	        	   for(String key:headers.keySet()){
	        		   connection.setRequestProperty(key, headers.get(key));
	        	   }
	           }
	           connection.connect();

	           //POST请求
	           if(Consts.Method_POST.equals(method)){
	        	   out = new DataOutputStream(connection.getOutputStream());
	 	          
		           out.writeBytes(obj.toString());
	           }
	           

	           //读取响应
	           reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	           
	           String lines;
	          
	           while ((lines = reader.readLine()) != null) {
	               lines = new String(lines.getBytes(), "utf-8");
	               sb.append(lines);
	           }
	          
	       } catch (IOException e) {
	         //  e.printStackTrace();
	           logger.error("获取机智云响应json数据异常e:"+e);
	       }finally{
	    	   if(out!=null){
	    		   try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	   }
	    	   if(reader!=null){
	    		   try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	   }
	           if(connection!=null){
	        	// 断开连接
		           connection.disconnect();
	           }
	          
	           
	       }
	       return sb.toString();

	   }
	public static Header[] assemblyHeader(Map<String,String> headers){
		 Header[] allHeader= new BasicHeader[headers.size()];
		 int i = 0;
		 for (String str :headers.keySet()) {
			 allHeader[i] = new BasicHeader(str,headers.get(str));
			 i++;
		  }
		  return allHeader;
	}
	public static HttpRespObject sendGet(String uri,Map<String, String> headers){
		String rspBody = "响应超时";
		int statusCode=0;
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setConnectTimeout(30000).setSocketTimeout(4000).build(); 
		
		CloseableHttpClient client = SSLUtil.createSSLClientDefault();
		HttpGet get = new HttpGet(uri);
		get.setConfig(requestConfig);
		if(headers!=null&&headers.size()>0){
			get.setHeaders(assemblyHeader(headers));
		}
		CloseableHttpResponse response = null;
		InputStream in = null;
		try {
			response = client.execute(get);
			statusCode=response.getStatusLine().getStatusCode();
			/*HttpEntity entity = response.getEntity();
			in = entity.getContent();*/
			rspBody =EntityUtils.toString(response.getEntity(), "utf8");
			get.abort();
		}catch (IOException e) {
			logger.warn(e.getMessage(),e);
		}finally{
			try {
				EntityUtils.consume(response.getEntity());
				if(response!=null){
					response.close();
				}
				if(client!=null){
					client.close();
				}
			} catch (IOException e) {
				statusCode=-1;
				rspBody=e.getMessage();
				logger.warn(e.getMessage(),e);
			}
		}

		HttpRespObject respObject = handleRsp(statusCode, uri, rspBody, "Get");
		return respObject;
	}
	private static  HttpRespObject handleRsp(int statusCode,String uri,String content,String method){
		boolean success = false;
		switch (statusCode) {
			case 0:logger.warn("请求api："+uri+" 时响应超时["+method+"];超时时间=30000");
				break;
			case 201:
			case 202:
			case 203:
			case 200:success = true;
		   		break;
			default:logger.warn("请求api："+uri+" 时异常["+method+"];statusCode="+statusCode+";响应内容="+content);
				break;
		}
		 return new HttpRespObject(success, statusCode, content);
	 }
	/**
	 * http 请求之Post，支持http和https两种。<br>
	 * 需要传入uri 需要post的内容(json或xml?)以及headers<br>
	 * headers 为一个Map<String,String>, 如果请求中不需要header则传入null<br>
	 * 返回HttpRespObject，HttpRespObject中包含是否请求成功(success)、statusCode和请求返回内容(constant)
	 * 
	 * @param uri
	 * @param constant
	 * @param headers
	 * @return
	 */

	public static HttpRespObject sendPost(String uri,String constant,Map<String, String> headers){
		int statusCode=0;
		String rspBody = "";
		CloseableHttpClient client = SSLUtil.createSSLClientDefault();
		HttpPost post = new HttpPost(uri);
		RequestConfig requestConfig = RequestConfig.custom()  
			    .setConnectionRequestTimeout(3000).setConnectTimeout(30000)  
			    .setSocketTimeout(4000).build(); 
		post.setConfig(requestConfig);
		if(headers!=null&&headers.size()>0){
			post.setHeaders(assemblyHeader(headers));
		}
		InputStream in = null;
		CloseableHttpResponse response = null;
		try {
			StringEntity sEntity = new StringEntity(constant,"UTF-8");
			//sEntity.setContentEncoding("UTF-8");
			//sEntity.setContentType("application/json");
			post.setEntity(sEntity);
			response = client.execute(post);
			statusCode=response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			in = entity.getContent();
			rspBody = inputStream2Str(in);
			post.abort();
		} catch (IOException e) {
			logger.warn(e.getMessage(),e);
		}finally{
			try {
				if(in!=null){
					in.close();
				}
				if(response!=null){
					response.close();
				}
				if(client!=null){
					client.close();
				}
			} catch (IOException e) {
				statusCode=-1;
				rspBody=e.getMessage();
				logger.warn(e.getMessage(),e);
			}
		}
		HttpRespObject respObject = handleRsp(statusCode, uri, rspBody, "Post") ;
		return respObject;
	}
	private static String inputStream2Str(InputStream is){
		 BufferedReader in = new BufferedReader(new InputStreamReader(is));
		 StringBuffer buffer = new StringBuffer();
		 String line = "";
		 try {
			 while ((line = in.readLine()) != null){
			     buffer.append(line);
			  }
		 } catch (IOException e) {
		 }
		 return buffer.toString();
	}
	public static void main(String[] args) {
		String APPID=SysConfigUtil.getString("appid");
		String APPSECRET=SysConfigUtil.getString("secret");
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
		JSONObject obj=new JSONObject();
		//obj.element("", value)
		
		//String result=getRespJsonStr(url, "POST", obj);
		String result=getRespJsonStr2(url);
		System.out.println(result);
	}

	/**
	 * 获取庆科云响应json数据
	 * @param URL  地址
	 * @param obj  JSONObject 参数
	 * @return  jsonstr
	 */
	public static String getFogcloudRespJSON(String action,JSONObject obj,Map<String, String> headers,String method) {

		   StringBuffer sb = new StringBuffer("");
		   DataOutputStream out=null;
		   BufferedReader reader=null;
		   HttpURLConnection connection =null;
	       try {
	           //创建连接
	           URL url = new URL(action);
	           connection = (HttpURLConnection) url.openConnection();
	           connection.setDoOutput(true);
	           connection.setDoInput(true);
	           connection.setRequestMethod(method==null?"GET":method);
	           connection.setUseCaches(false);
	           connection.setInstanceFollowRedirects(true);
	         //  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	           connection.setRequestProperty("Content-Type","application/json");
	           connection.setRequestProperty("X-Application-Id",  SysConfigUtil.getString("fogcloud.app_id"));

	           if(headers!=null && headers.size()>0){
	        	   for(String key:headers.keySet()){
	        		   connection.setRequestProperty(key, headers.get(key));
	        	   }
	           }
	           connection.connect();

	           //POST请求
	           if(Consts.Method_POST.equals(method)){
	        	   out = new DataOutputStream(connection.getOutputStream());
	 	          
		           out.writeBytes(obj.toString());
	           }
	           

	           //读取响应
	           reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	           
	           String lines;
	          
	           while ((lines = reader.readLine()) != null) {
	               lines = new String(lines.getBytes(), "utf-8");
	               sb.append(lines);
	           }
	          
	       } catch (IOException e) {
	         //  e.printStackTrace();
	           logger.error("获取庆科云响应json数据异常e:"+e);
	       }finally{
	    	   if(out!=null){
	    		   try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	   }
	    	   if(reader!=null){
	    		   try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	   }
	           if(connection!=null){
	        	// 断开连接
		           connection.disconnect();
	           }
	          
	           
	       }
	       return sb.toString();

	   }
	
}
