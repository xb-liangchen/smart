package com.baoju.common.wechat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baoju.common.util.Consts;
import com.baoju.common.util.SysConfigurationRead;
import com.baoju.common.util.SystemCache;
import com.baoju.common.util.properties.SysConfigUtil;



public class WeiXinUtil {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	
	private static SysConfigurationRead ucr = SysConfigurationRead.getInstance();
	public static String appid = ucr.getConfigItem("appid");
    public static String secret = ucr.getConfigItem("secret");
    public static String Token = SysConfigUtil.getString("token");
    public static String echostr;
    /**
     * 部署项目的IP地址或域名
     */
    public static String serviceIP = ucr.getConfigItem("ip");
    
    public static String getToken(){
    	long time = (new Date().getTime()/1000);
    	if(time - SystemCache.access_token_time > 7100){ //是否已超7200秒（2小时）
    		log.info("####重新获取accessToken");
    		SystemCache.access_token = getToken(appid,secret);
    		SystemCache.access_token_time = time;  //秒
    	}
    	log.info("####缓存中获取accessToken，accessToken未失效");
    	return SystemCache.access_token;
    }
    
	public static String getToken(String appid,String secret){
		log.info("####getToken");
		String action = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		log.info("action:"+action);
		try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           JSONObject demoJson = JSONObject.fromObject(message);
           String access_token = demoJson.getString("access_token");
           return access_token;
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        return "access_token 失败";
        
	}
	
	public static String getMenus(String token){
		log.info("####getMenus");
		String action = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+token;
		log.info("action:"+action);
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           return message;
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        return "getMenus 失败";
	}
	
	public static String createMenu(String menu,String token) {
		log.info("####createMenu");
        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
        log.info("action:"+action);
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("POST");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
           http.connect();
           OutputStream os= http.getOutputStream();   
           os.write(menu.getBytes("UTF-8"));//传入参数   
           os.flush();
           os.close();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           return "返回信息"+message;
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        return "createMenu 失败";
	}
	
	
	/**
	 * 获取用户信息
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 */
	public static JSONObject getUserInfo(String appid,String secret,String code) {
		log.info("#####getUserInfo");
		String action = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		log.info("#####action="+action);
		JSONObject userObject = null;
        JSONObject oneJsonObject = null;
        String access_token = "";
        String openid = "";
		try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           oneJsonObject = JSONObject.fromObject(message);
           access_token = oneJsonObject.getString("access_token");
           openid = oneJsonObject.getString("openid");
        } catch (MalformedURLException e) {
        	if(oneJsonObject!=null&&!oneJsonObject.has("openid"))
        		log.info("从微信获取openid出现异常!");
           e.printStackTrace();
        } catch (IOException e) {
        	if(oneJsonObject!=null&&!oneJsonObject.has("openid"))
        		log.info("从微信获取openid出现异常!");
           e.printStackTrace();
        }   
        
        
        try {
        	String action2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
           URL url = new URL(action2);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           userObject = JSONObject.fromObject(message);
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        
		return userObject;
	}
	
	
	/**
	 * 网页授权步骤一：通过code换取网页授权access_token
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 */
	public static JSONObject getTokenForPage(String appid,String secret,String code){
		log.info("####getTokenForPage");
		String action = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		log.info("#####action="+action);
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           log.info(message);
           JSONObject demoJson = JSONObject.fromObject(message);
           
           return demoJson;
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        return null;
	}
	
	/**
	 * 网页授权步骤二：刷新access_token（如果需要）
	 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
	 * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后，需要用户重新授权。 
	 * @param appid
	 * @param refresh_token
	 * @return
	 */
	public static JSONObject getRefreshToken(String appid,String refresh_token){
		log.info("####getTokenForPage");
		String action = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+appid+"&grant_type=refresh_token&refresh_token="+refresh_token;
		log.info("#####action="+action);
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           JSONObject demoJson = JSONObject.fromObject(message);
           return demoJson;
        } catch (MalformedURLException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }   
        return null;
        
	}
	/**
	 * 网页授权步骤三：拉取用户信息
	 * @param access_token 基础支持的accessToken
	 * @param openid
	 * @return
	 */
	public static JSONObject getWeiXinUserInfo(String access_token,String openid){
		log.info("####getWeiXinUserInfo");
		String action = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		log.info("#####action="+action);
		try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("GET");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
           http.connect();
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           JSONObject demoJson = JSONObject.fromObject(message);
           return demoJson;
           
        } catch (MalformedURLException e) {
        	log.info("获取用户信息失败1111："+e);
           e.printStackTrace();
        } catch (IOException e) {
        	log.info("获取用户信息失败2："+e);
           e.printStackTrace();
        }   
        return null;
        
	}
	/**
	 * 获取二维码的ticket
	 * @param content 内容
	 * @param accessToken 
	 * @return
	 */
	public static String getTicket(String content,String accessToken) {
        log.info("#####getTicket");
        String action = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
		log.info("#####action="+action);
		String ticket = "";
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("POST");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
           http.connect();
           OutputStream os= http.getOutputStream();   
           os.write(content.getBytes("UTF-8"));//传入参数   
           os.flush();
           os.close();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           log.info("message:"+message);
           JSONObject demoJson = JSONObject.fromObject(message);
           ticket = demoJson.getString("ticket");
           log.info("ticket:"+ticket);
        } catch (MalformedURLException e) {
        	log.info("获取ticket出错！MalformedURLException:"+e);
           e.printStackTrace();
        } catch (IOException e) {
        	log.info("获取ticket出错！IOException:"+e);
           e.printStackTrace();
        }   
        return ticket;
	}
	/**
	 * 
	 * @param strUrl
	 * @param path
	 */
	public static void downloadPic(String strUrl,String path){
	       URL url = null;
	       try {
	              url = new URL(strUrl);
	       } catch (MalformedURLException e2) {
	             e2.printStackTrace();
	             return;
	       }

	       InputStream is = null;
	        try {
	            is = url.openStream();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	            return;
	        }

	        OutputStream os = null;
	        try{
	            os = new FileOutputStream(path);
	            int bytesRead = 0;
	            byte[] buffer = new byte[8192];
	            while((bytesRead = is.read(buffer,0,8192))!=-1){
	            os.write(buffer,0,bytesRead);
	       }
	       }catch(FileNotFoundException e){
	           e.printStackTrace();
	           return;
	       } catch (IOException e) {
	           e.printStackTrace();
	           return;
	       }
	    }
	/**
	 * 推送消息给用户
	 * @param content
	 * @param accessToken
	 */
	public static String pushDataToUser(String content,String accessToken) {
		log.info("#####pushDataToUser");
        String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
		log.info("#####action="+action);
		String msg = "";
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("POST");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
           http.connect();
           OutputStream os= http.getOutputStream();   
           os.write(content.getBytes("UTF-8"));//传入参数   
           os.flush();
           os.close();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           log.info("message:"+message);
           JSONObject demoJson = JSONObject.fromObject(message);
           msg = demoJson.getString("errmsg");
           log.info("msg:"+msg);
        } catch (MalformedURLException e) {
        	log.info("获取ticket出错！MalformedURLException:"+e);
           e.printStackTrace();
        } catch (IOException e) {
        	log.info("获取ticket出错！IOException:"+e);
           e.printStackTrace();
        }   
        return msg;
	}
	
	
	/**
	 * 推送模板消息给用户
	 * @param content
	 * @param accessToken
	 */
	public static String pushTemplateDataToUser(String content,String accessToken) {
		String msg = "";
        String action = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();   
             
           http.setRequestMethod("POST");       
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");   
           http.setDoOutput(true);       
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
           http.connect();
           OutputStream os= http.getOutputStream();   
           os.write(content.getBytes("UTF-8"));//传入参数   
           os.flush();
           os.close();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           log.info("message:"+message);
           JSONObject demoJson = JSONObject.fromObject(message);
           msg = demoJson.getString("errmsg");
           log.info("msg:"+msg);
        } catch (MalformedURLException e) {
        	log.info("获取ticket出错！MalformedURLException:"+e);
           e.printStackTrace();
        } catch (IOException e) {
        	log.info("获取ticket出错！IOException:"+e);
           e.printStackTrace();
        }   
        return msg;
	}
	
	/**
	 * SHA1加密,  Encrypt(str,"SHA-1");
	 * @param strSrc 需要加密的字符串
	 * @param encName 加密算法
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName) {
		// parameter strSrc is a string will be encrypted,
		// parameter encName is the algorithm name will be used.
		// encName dafault to "MD5"
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			log.info("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	/**
	 * 获取六位随机数
	 * @return
	 */
	public static int getSixRandom(){
		Random r = new Random();
		 int x = r.nextInt(999999); 
		 while (x<100000) {
			 x = r.nextInt(999999); 
		 }
		 return x;
	}

	
	public static void main(String[] args) {
		
		/**
		 * 我的智慧家庭
		 */
		String appid = Consts.APPID;
		String secret = Consts.SECRET;
//		String token = WeiXinUtil.getToken(appid, secret);
		String token = "1S4wRxnzKJcgTF7QGxT4_r0kDUSHB6lsNKq2dU3bmb_evv9t-uAE4jr4GLN920HJv6EmdqIrKtaSxa5rg0t8yJoHQ5j2-gsXxYYi9AUz9E4";
//		
		JSONObject weiXinUserInfo = WeiXinUtil.getWeiXinUserInfo(token, "oAnrxt9PzgomAgtu-vmzFomMEwCc");
//		System.out.println(weiXinUserInfo.toString());
		System.out.println("#####token="+token);
//		String menus = "{\"button\":[" +
//				"{\"type\":\"view\",\"name\":\"玩家电\",\"url\":\"http://210.51.17.141/weixinAir/haierservice\"}," +
//				"{\"type\":\"view\",\"name\":\"微商城\",\"url\":\"http://210.51.17.141/weixinAir/columnx\"}," +
//				"{\"name\":\"我的\",\"sub_button\":[" +
//					//"{\"type\": \"view\", \"name\": \"找店面\", \"url\": \"http://210.51.17.141/weixinAir/lbs/search.jsp\"}," +
////					"{\"type\": \"view\", \"name\": \"净化器UI\", \"url\": \"http://210.51.17.141/AirClean/wechat/main.do\"}," +
////					"{\"type\": \"view\", \"name\": \"水友会141\", \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WeiXinUtil.appid+"&redirect_uri=http://210.51.17.141/Haier/bbs/bbs.do&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect\"}," +
//					"{\"type\":\"view\",\"name\":\"维权\",\"url\":\"https://mp.weixin.qq.com/payfb/payfeedbackindex?appid=wx8f1e7fb3f0076616#wechat_webview_type=1&wechat_redirect\"}," +
//					"{\"type\":\"view\",\"name\":\"微社区\",\"url\":\"http://210.51.17.141/weixinAir/message/baseinfo?appid=oOSNhTykGTYsZ0\"}," +
//					"{\"type\":\"view\",\"name\":\"管理家电\",\"url\":\"http://210.51.17.141/weixinAir/haierservice?page=mydevices\"}," +
//					"{\"type\":\"view\",\"name\":\"排行榜\",\"url\":\"http://210.51.17.141/weixinAir/rankinglist\"}," +
//					"{\"type\":\"view\",\"name\":\"凑份子\",\"url\":\"http://210.51.17.141/usdkwash/active/pages/movie.jsp\"}" +
//				"]}" +
//				"]}";
	//	String createMenu = WeiXinUtil.createMenu(menus,token);
//		System.out.println("#####token="+createMenu);
		
		
		String menus2 = WeiXinUtil.getMenus(token);
		System.out.println(menus2);
		
		/**
		 * 测试：获取二维码的ticket
		 */
//		String content = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 213}}}";
//		String accessToken = "P_SwTCUReJdxpSH0pOeunHSZ3T-YMnpVKAxt3j_LZO0bz53R4743uSbIdBkRintjbY7wULGGCsDTXHogsAV_bw";
//		String ticket = getTicket(content, accessToken);
		
		/**
		 * 测试：下载二维码图片
		 */
//		String strUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQH18DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL28zVWRObjNsOTBPTzRxbXJHMWs5AAIEj2H4UwMEAAAAAA==";
//		String path = "E:/image/1212.jpg";
//		downloadPic(strUrl, path);
		
//		getTokenForPage(appid,secret,"0279744c2a97303c63141ec641adf69f");
		
		/**
		 * 获取微信用户信息
		 */
//		String accesstiken = "QmtSBaPEle1HY3T0EL6pQsWQgWQ1_ervi587IKPqQFvIfoX4n_uVBHpATu9gsYc9Ki49AiAIAJPvOh7G14jJQg";
//		String openid="o-S3EjnZ4iyoRoJEziumWxnohmbE";
//		getWeiXinUserInfo(accesstiken,openid);
		
	}
}
