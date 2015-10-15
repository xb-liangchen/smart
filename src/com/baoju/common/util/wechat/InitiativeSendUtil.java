package com.baoju.common.util.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baoju.common.util.Consts;
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.str.ThrowableToStr;
import com.baoju.common.util.url.HttpConnUtil;
/**
 * 主动发送请求
 * @author demi
 *
 */
public class InitiativeSendUtil {

	private static  final Logger log=Logger.getLogger(InitiativeSendUtil.class);
	
	/**
	 * 主动发送消息给设备主人的微信终端，并最终送达设备。
	 * @param ACCESS_TOKEN
	 * @param device_id
	 * @param open_id
	 * @param content  BASE64编码   （例如：绑定成功》  57uR5a6a5oiQ5Yqf44CC44CC44CC44CC44CC44CC）
	 * @return
	 */
	public static boolean sendDeviceMess(String ACCESS_TOKEN,String device_id,String open_id,String content){
		String URL="https://api.weixin.qq.com/device/transmsg?access_token="+ACCESS_TOKEN;
		JSONObject obj=new JSONObject();
		obj.element("device_type", SysConfigUtil.getString("weixin_old_code"));
		obj.element("device_id", device_id);
		obj.element("open_id", open_id);
		obj.element("content",content);
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.Method_POST, obj);
		
		JSONObject resobj=JSONObject.fromObject(res);
		if(resobj.get("errcode")==null){
			//发送成功
			return true;
		}else{
			//发送失败
			log.error("主动发送消息给设备 失败："+resobj.get("errmsg"));
			return false;
		}
	}
	/**
	 * 获得qrcode  有效期1800秒
	 * @param ACCESS_TOKEN 
	 * @return
	 */
	public static String getQrcode(){
		String ACCESS_TOKEN=Consts.ACCESS_TOKEN;
		String URL="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+ACCESS_TOKEN;
		JSONObject obj=new JSONObject();
		String str="{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.Method_POST, obj.fromObject(str));
		
		return res;
	}
	/**
	 * 获得access_token有效期7200秒
	 * @param ACCESS_TOKEN 
	 * @return
	 */
	public static String getAccessToken(){
		String APPID=Consts.APPID;
		String APPSECRET=Consts.SECRET;
		String URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
		JSONObject obj=new JSONObject();
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.METHOD_GET, obj);
		
		JSONObject resobj=JSONObject.fromObject(res);
		if(resobj.get("errmsg")==null){
			//发送成功
			return resobj.get("access_token").toString();
		}
		return null;
	}
	/**
	 * 获得jsapi_ticket有效期7200秒
	 * @param ACCESS_TOKEN 
	 * @return
	 */
	public static String getJsapiTicket(){
		String ACCESS_TOKEN=Consts.ACCESS_TOKEN;
		String URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+ACCESS_TOKEN;
		JSONObject obj=new JSONObject();
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.METHOD_GET, obj);
		JSONObject resobj=JSONObject.fromObject(res);
		
		if(resobj.get("errmsg").equals("ok")){
			//发送成功
			return resobj.get("ticket").toString();
		}
		return "";
	}
	/**
	 * 获取用户基本信息
	 * @param openid ACCESS_TOKEN 
	 * @return
	 */
	public static JSONObject getUserinfo(String openid){
		String URL="https://api.weixin.qq.com/cgi-bin/user/info?openid="+openid+"&lang=zh_CN&access_token="+Consts.ACCESS_TOKEN;
		JSONObject obj=new JSONObject();
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.METHOD_GET, obj);
		
		JSONObject resobj=JSONObject.fromObject(res);
		
		return resobj;
	}
	/**
	 * 获取用户基本信息  (需scope为 snsapi_userinfo)
	 * @param openid 
	 * @param ACCESS_TOKEN  网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 
	 * @return
	 */
	public static JSONObject getUserinfoBysnsapi(String openid,String ACCESS_TOKEN){
		String URL="https://api.weixin.qq.com/sns/userinfo?openid="+openid+"&lang=zh_CN&access_token="+ACCESS_TOKEN;
		JSONObject obj=new JSONObject();
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.METHOD_GET, obj);
		
		JSONObject resobj=JSONObject.fromObject(res);
		if(resobj.get("errcode")==null){
			//发送成功
			return resobj;
		}
		return null;
	}
	
	/**
	 * 第二步 ：通过code换取网页授权access_token,openid
	 * @param openid
	 * @param secret
	 * @param code  第一步获取的code参数 
	 * @return
	 */
	public static JSONObject getWapAccessTKAndOpenid(String code){
		String appid=Consts.APPID;
		String secret=Consts.SECRET;
		String URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		JSONObject obj=new JSONObject();
		String res=HttpConnUtil.getRespJsonStr2(URL);
		
		JSONObject resobj=JSONObject.fromObject(res);
		
		return resobj;
	}
	
	
	
	
	
	
	/**
	 * 设备授权
	 * @param access_token
	 * @param deviceId
	 * @return
	 */
	public static boolean deviceAuth(String deviceId,String mac){
		
		String URL = "https://api.weixin.qq.com/device/authorize_device?access_token="+Consts.ACCESS_TOKEN;
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("device_num", 1);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", deviceId);
		jsonObj.put("mac",mac);
		jsonObj.put("connect_protocol", "4");//wifi
		jsonObj.put("auth_key", "");
		jsonObj.put("close_strategy", "3");
		jsonObj.put("conn_strategy", "8");
		jsonObj.put("crypt_method", "0");
		jsonObj.put("auth_ver", "0");
		jsonObj.put("manu_mac_pos", "-2");
		jsonObj.put("ser_mac_pos", "-2");
		jsonArray.add(jsonObj);
		jsonObject.put("device_list", jsonArray);
		jsonObject.put("op_type", "1");
		
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.Method_POST, jsonObject);
		
		JSONObject resobj=JSONObject.fromObject(res);
		if(resobj.get("errcode")==null){
			//操作成功
			return true;
		}else{
			//操作失败
			log.error("设备授权 失败："+resobj.get("errmsg"));
			return false;
		}
	}
	
	
	/**
	 * 设备绑定
	 * @param access_token
	 * @param jsapiTicket
	 * @param deviceId
	 * @param openid
	 * @return
	 */
	public static boolean deviceBind(String jsapiTicket,String deviceId,String openid){
		
		String URL = "https://api.weixin.qq.com/device/bind?access_token="+Consts.ACCESS_TOKEN;
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("ticket", jsapiTicket);
		jsonObj.put("device_id",deviceId);
		jsonObj.put("openid", openid);
		
		
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.Method_POST, jsonObj);
		
		JSONObject resobj=JSONObject.fromObject(res);
		JSONObject obj=JSONObject.fromObject(resobj.get("base_resp"));
		if(obj.get("errmsg").equals("ok")){
			//操作成功
			return true;
		}else{
			//操作失败
			log.error("设备绑定 失败："+resobj.get("errmsg"));
			return false;
		}
	}
	
	
    /**

     * 微信公共账号发送给账号

     * @param content 文本内容

     * @param toUser 微信用户  

     * @return

     */

    public static boolean sendTextMessageToUser(String content,String toUser){

       //String json = "{\"touser\": \""+toUser+"\",\"msgtype\": \"text\", \"text\": {\"content\": \""+content+"\"}}";

       JSONObject json=new JSONObject();
       json.put("touser", toUser);
       json.put("msgtype", "text");
       
       JSONObject json2=new JSONObject();
       json2.put("content", content);
       
       json.put("text",json2);
       
       
       //获取access_token

       String accessToken =Consts.ACCESS_TOKEN;

       //获取请求路径

       String action = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;

       System.out.println("json:"+json);

       try {

          String mes= HttpConnUtil.getRespJsonStr(action, "POST", json);
          JSONObject resobj=JSONObject.fromObject(mes);
          if(resobj.getInt("errcode")==0){
        	  return true;
          }

       } catch (Exception e) {

           e.printStackTrace();

       }
       
       return false;

   }
    /**
     * 微信公共账号发送给账号
     * @param toUserOpenid  接收消息者openid
     * @param title  信息标题
     * @param description  信息内容
     * @param pageURL  详情地址
     * @param picurl  图片地址
     * @return  true-推送消息成功
     */ 

    public static boolean sendNewsMessageToUser(String toUserOpenid,String title,String description,String pageURL,String picurl){

       
       //获取access_token

       String accessToken =Consts.ACCESS_TOKEN;
       
       String action="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
		
       
       JSONObject json=new JSONObject();
       json.put("touser", toUserOpenid);
       json.put("msgtype", "news");
      
       JSONObject json2=new JSONObject();
       JSONArray arr=new JSONArray();
       JSONObject json3=new JSONObject();
       json3.put("title", title);
       if(description!=null&&description.length()>0)
       json3.put("description", description);
       if(pageURL!=null&&pageURL.length()>0)
       json3.put("url", pageURL);
       if(picurl!=null&&picurl.length()>0)
       json3.put("picurl", picurl==null?"":picurl);
       arr.add(json3);
       json2.put("articles", arr);
      
       json.put("news",json2);

       try {

    	   String mes=HttpConnUtil.getRespJsonStr(action, "POST", json);
    	   JSONObject res=JSONObject.fromObject(mes);
           if(res.getInt("errcode")==0){
        	   return true;
           }
       } catch (Exception e) {

           e.printStackTrace();

       }

       return false;
   }
    /**
     * 发送模板消息
     * @param toUserOpenid
     * @param tempId
     * @param url
     * @param firstData
     * @param keynote1
     * @param keynote2
     * @param keynote3
     * @param remarkData
     * @return
     */
    public static boolean sendTemplateMessage(String toUserOpenid,String tempId,String url,String firstData,String keynote1,String keynote2,String keynote3,String remarkData){

        
        //获取access_token

        String accessToken =Consts.ACCESS_TOKEN;
        
        String action="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accessToken;
 		
        String str="{"+
           "\"touser\":\""+toUserOpenid+"\","+
           "\"template_id\":\""+tempId+"\","+
           "\"url\":\""+url+"\","+
           "\"topcolor\":\"#FF0000\","+
           "\"data\":{"+
                   "\"first\": {"+
                       "\"value\":\""+firstData+"\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"keyword1\":{"+
                       "\"value\":\""+keynote1+"\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"keyword2\": {"+
                       "\"value\":\""+keynote2+"\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"keyword3\": {"+
                       "\"value\":\""+keynote3+"\","+
                       "\"color\":\"#173177\""+
                   "},"+
                   "\"remark\":{"+
                       "\"value\":\""+remarkData+"\","+
                       "\"color\":\"#173177\""+
                   "}"+
           "}"+
       "}";
        try {
           JSONObject json=JSONObject.fromObject(str);
     	   String mes=HttpConnUtil.getRespJsonStr(action, "POST", json);
     	   JSONObject res=JSONObject.fromObject(mes);
            if(res.getInt("errcode")==0){
         	   return true;
            }
        } catch (Exception e) {

           log.error("发送模板消息异常："+ThrowableToStr.getTrace(e));

        }

        return false;
    }
	
	public static void main(String[] args) {
		/*String str="{\"resp\":[{\"base_info\":{\"device_type\":\"your_devcie_type\",\"device_id\":\"id\"},\"errcode\":0,\"errmsg\":\"ok\"}]}";
		JSONObject resobj=JSONObject.fromObject(str);
		
		JSONArray arr=JSONArray.fromObject(resobj.get("resp"));
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj=(JSONObject) arr.get(i);
			System.out.println(obj.get("errmsg"));
			
			
		}*/
		
		String accessToken="LZX9WlMMRG-8VBlGgDNpdGKTQCI0-iYw_8iGun1P_R5WkEcg4PXjhNWG3sD8nqtZO6rRZ7lpG5d7-GB1Sal2bOIQ0xWkmajBZcV7N38t3P4";
		//String openid="o7GoOuM3gKsgDa3DcsHbDACUyrbM";
		
	    Consts.ACCESS_TOKEN=accessToken;
	   
        //System.out.println(sendNewsMessageToUser(openid, "嘿嘿", "踩踩踩", "", null));
	   
	    //System.out.println(getQrcode());
	    
	    System.out.println(getAccessToken());
	    
	    System.out.println(sendTemplateMessage("oQIdrwcMSIsbrQ4u1ZOjbZquaX98", "Ppl_kufNOXFN36K9IZSl3IKGfdWL_5Mw-xhv5LjnK7Q", "http://chanxj1993.tunnel.mobi/securityGuards/alarm01.htm?did=23&time=1920120303&devnum=0&mudulenum=3&finderName=中国人", "尊敬的用户，你的大狗发生警报", "入侵报警", "中国人", "2015年08月25日", "请及时处理"));
	   
	}
}
