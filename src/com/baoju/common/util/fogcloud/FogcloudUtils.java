package com.baoju.common.util.fogcloud;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.baoju.common.util.Consts;
import com.baoju.common.util.MD5;
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.url.HttpConnUtil;

public class FogcloudUtils {
	private static Logger logger=Logger.getLogger(FogcloudUtils.class);
	
	public static JSONObject createUser(String openid){
		JSONObject obj=new JSONObject();
		obj.put("login_id", openid);
		obj.put("password", SysConfigUtil.getString("fogcloud.user_default_password"));
		String action="http://api.easylink.io/v1/user/join";
		Map<String, String> headers=new HashMap<String, String>();
//		long timestamp = System.currentTimeMillis()/1000;
//		headers.put("Authorization", SysConfigUtil.getString("fogcloud.user_key"));
//		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.app_secret_key")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("创建用户return:"+robj);
		return robj;
	}
	/**
	 * 用户登录
	 * @param login_id
	 * @return  { "user_token": "用户访问凭证",  "user_id": "用户ID" }
	 */
	public static JSONObject login(String login_id,String token){
		JSONObject obj=new JSONObject();
		obj.put("username", login_id);
		obj.put("password", SysConfigUtil.getString("fogcloud.user_default_password"));
		String action="http://api.easylink.io/v2/users/login";
		Map<String, String> headers=new HashMap<String, String>();
		long timestamp = System.currentTimeMillis()/1000;
		headers.put("Authorization", token);
		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.app_secret_key")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("用户登录return:"+robj);
		return robj;
	}
	
	/**
	 * 注册设备
	 * @param mac
	 * @return  {"device_id": "设备ID", "device_token": "设备访问接口的凭证" }
	 */
	public static JSONObject  regDevice(String mac){
		JSONObject obj=new JSONObject();
		obj.put("product_id", SysConfigUtil.getString("fogcloud.product_id"));
		obj.put("MAC", mac);
		String action="http://api.easylink.io/v2/devices";
		Map<String, String> headers=new HashMap<String, String>();
		long timestamp = System.currentTimeMillis()/1000;
//		headers.put("Authorization", token);
//		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.app_secret_key")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("设备注册return:"+robj);
		return robj;
	}
	/**
	 * 绑定设备
	 * @param user_id
	 * @param mac
	 * @return  {"result":"success" }
	 */
	public static boolean  bindDevice(String user_id,String mac){
		JSONObject obj=new JSONObject();
		obj.put("product_id", SysConfigUtil.getString("fogcloud.product_id"));
		obj.put("user_id", user_id);
		obj.put("MAC", mac);
		String secret_key = MD5.encode(mac+SysConfigUtil.getString("fogcloud.product_secret"));
		obj.put("secret_key", secret_key);
		obj.put("bind_mode",3);
		logger.info("obj:"+obj);
		String action="http://api.easylink.io/v2/devices/bind";
		Map<String, String> headers=new HashMap<String, String>();
		long timestamp = System.currentTimeMillis()/1000;
//		headers.put("Authorization", token);
		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.app_secret_key")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("用户设备绑定return:"+robj);
		if(robj.getString("result")!=null && !"".equals( robj.getString("result"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param user_id
	 * @param mac
	 * @return  {"result":"success" }
	 */
	public static String  getDid(String mac,String deviceToken){
		JSONObject obj=new JSONObject();
		obj.put("bssid", mac);
		logger.info("obj:"+obj);
		String action="http://api.easylink.io/v1/device/queryBss";
		Map<String, String> headers=new HashMap<String, String>();
		long timestamp = System.currentTimeMillis()/1000;
		headers.put("Authorization", deviceToken);
		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.product_secret")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("Did  return:"+robj);
		return robj.getString("device_id");
	}
	
	public static JSONObject getToken(String login_id){
		JSONObject obj=new JSONObject();
		obj.put("login_id", login_id);
		obj.put("password", SysConfigUtil.getString("fogcloud.user_default_password"));
		logger.info("obj:"+obj);
		String action="http://api.easylink.io/v1/key/get";
		Map<String, String> headers=new HashMap<String, String>();
		long timestamp = System.currentTimeMillis()/1000;
//			headers.put("Authorization", token);
		headers.put("X-Request-Sign", MD5.encode(SysConfigUtil.getString("fogcloud.product_secret")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		String jsonstr=HttpConnUtil.getFogcloudRespJSON(action, obj,headers,Consts.Method_POST);
		System.out.println(jsonstr);
		JSONArray ja = new JSONArray();
		ja = ja.fromObject(jsonstr);
		String userToken = ja.getJSONObject(0).getString("id");
		String deviceToken = ja.getJSONObject(1).getString("id");
		JSONObject robj = new JSONObject();
		robj.accumulate("userToken", userToken);
		robj.accumulate("deviceToken", deviceToken);
		logger.info("Did  return:"+robj);
		return robj;
	}
	
	
	
	public static void main(String[] args) {
//		JSONObject o = createUser("test007");
//		System.out.println(o.getString("token"));
//		System.out.println(o.getString("message"));
//		System.out.println(MD5.encode("41fbf2c7485f1c4d4b61f8de2eb65fd4"+"1440754176"));
//				
//		JSONObject o2 =login("test007", "8893f9aa-0dd7-4d23-b0db-ba7d64e611e0");
//		String token = o2.getString("user_token");
//		System.out.println(token);
//		String id = o2.getString("user_id");
//		System.out.println(id);
		
//		bindDevice("9ab496f0-bbab-4724-ab69-4df1a002742e", "c8934652a4a0");
		getToken("oDCnpwpSetAYyqJK5FjaDiBFPIwg");
		String did = getDid("c8934652a4a0","de352c4a-02c2-47a6-b073-129626bd9fef");
		System.out.println("did:"+did);
		
	//	JSONObject o3= regDevice("14f9e42d","12-23-34-56",token);

	/*	System.out.println(MD5.encode("c8934652a4a0"+SysConfigUtil.getString("fogcloud.product_secret")));
		
		long timestamp = System.currentTimeMillis()/1000;
		System.out.println(MD5.encode(SysConfigUtil.getString("fogcloud.app_secret_key")+String.valueOf(timestamp))+","+String.valueOf(timestamp));
		*/
	}
	

}
