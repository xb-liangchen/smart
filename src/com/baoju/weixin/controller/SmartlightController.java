//package com.baoju.weixin.controller;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.fusesource.mqtt.client.BlockingConnection;
//import org.fusesource.mqtt.client.MQTT;
//import org.fusesource.mqtt.client.Message;
//import org.fusesource.mqtt.client.QoS;
//import org.fusesource.mqtt.client.Topic;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.baoju.common.util.Consts;
//import com.baoju.common.util.MD5;
//import com.baoju.common.util.gizwits.GizwitsUtils;
//import com.baoju.common.util.json.JsonUtil;
//import com.baoju.common.util.properties.MessConfigUtil;
//import com.baoju.common.util.properties.SysConfigUtil;
//import com.baoju.common.util.str.StringUtils;
//import com.baoju.common.util.wechat.InitiativeSendUtil;
//import com.baoju.common.util.wechat.Sign;
//import com.baoju.weixin.entity.SysDevice;
//import com.baoju.weixin.entity.SysDeviceLightBind;
//import com.baoju.weixin.entity.SysDeviceUser;
//import com.baoju.weixin.entity.SysLight;
//import com.baoju.weixin.entity.SysLog;
//import com.baoju.weixin.entity.SysScene;
//import com.baoju.weixin.entity.SysSceneDetail;
//import com.baoju.weixin.entity.SysUser;
//import com.baoju.weixin.entity.SysUserDeviceBind;
//import com.baoju.weixin.service.IUserService;
//
//@Controller
//public class SmartlightController {
//	private static Logger logger=Logger.getLogger(SmartlightController.class);
//	
//	@Resource(name="userService")
//	private IUserService userService;
//	
//	@RequestMapping(value="/firstUse.htm")
//	public String firstUse(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String code=req.getParameter("code");
//		JSONObject json=InitiativeSendUtil.getWapAccessTKAndOpenid(code);
//		if(!json.has("openid"))
//		{
//			try {
//				resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+SysConfigUtil.getString("appid")+"&redirect_uri=http://"+SysConfigUtil.getString("ip")+"/smartlight/firstUse.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
//			return null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String openid=json.getString("openid");
//		logger.info("openid:"+openid);
//		String url = req.getRequestURL().toString()+"?"+req.getQueryString();
//		long timestamp = System.currentTimeMillis()/1000;
//		String nonceStr = StringUtils.getRandomString(10);
//		String signature = Sign.sign(Consts.JSAPI_TICKET, url, nonceStr, String.valueOf(timestamp));
//		model.addAttribute("timestamp", timestamp);
//		model.addAttribute("nonceStr", nonceStr);
//		model.addAttribute("signature", signature);
//		model.addAttribute("appId",Consts.APPID);
//		model.addAttribute("openid",openid);
//		return "wifi/connwf";
//	}
//	
//
//	@RequestMapping(value="/airkiss.htm")
//	public void airkiss(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		
//		String openid=req.getParameter("openid");
//		logger.info("======配置成功发送消息给 openid:"+openid);
//		boolean b=InitiativeSendUtil.sendNewsMessageToUser(openid,MessConfigUtil.getString("mess.connwf"), MessConfigUtil.getString("oper.success"), "", null);
//		if(b){
//			JsonUtil.writeToHtml(resp, "{\"errcode\":\"0\",\"errmsg\" : \"ok\"}");
//		}else{
//			JsonUtil.writeToHtml(resp, "{\"errcode\":\"1\",\"errmsg\" : \"error\"}");
//		}
//	
//	}
//	
//	@RequestMapping(value="/deviceList.htm")
//	public String deviceList(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String code=req.getParameter("code");
//		JSONObject json=InitiativeSendUtil.getWapAccessTKAndOpenid(code);
//		if(!json.has("openid"))
//		{
//			try {
//				resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+SysConfigUtil.getString("appid")+"&redirect_uri=http://"+SysConfigUtil.getString("ip")+"/smartlight/firstUse.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
//			return null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String openid=json.getString("openid");
//		System.out.println("微信号为："+openid);
//		List<SysDevice> sd = userService.getDevListByOpenid(openid);
////		if(sd.size()==0){
////			return "forward:addDevice.htm";
////		}
//		SysUser su = userService.getUserInfo(openid);
//		Long uid = su.getId();
//		model.addAttribute("sd",sd);
//		model.addAttribute("openid",openid);
//		return "device/deviceList";
//	}
//	
//	@RequestMapping(value="/addDevice.htm")
//	public String addDevice(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String code=req.getParameter("code");
//		JSONObject json=InitiativeSendUtil.getWapAccessTKAndOpenid(code);
//		if(!json.has("openid"))
//		{
//			try {
//				resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+SysConfigUtil.getString("appid")+"&redirect_uri=http://"+SysConfigUtil.getString("ip")+"/smartlight/addDevice.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
//				return null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String openid=json.getString("openid");
//		logger.info("========json.openid:"+openid);
//		long timestamp = System.currentTimeMillis()/1000;
//		String nonceStr = StringUtils.getRandomString(10);
//		String url = req.getRequestURL().toString()+"?"+req.getQueryString();
//		logger.info("========url:"+url);
//		String signature = Sign.sign(Consts.JSAPI_TICKET, url, nonceStr, String.valueOf(timestamp));
//		
//		model.addAttribute("timestamp", timestamp);
//		model.addAttribute("nonceStr", nonceStr);
//		model.addAttribute("signature", signature);
//		model.addAttribute("appId",Consts.APPID);
//		model.addAttribute("openid",openid);
//		return "control/scan";
//	}
//	
//	
//	@RequestMapping(value="/modifyWifiName.htm")
//	public void modifyDevice(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String msg = "ok";
//		try{
//			Long did=Long.parseLong(req.getParameter("did"));
//			String dname=req.getParameter("dname");
//			logger.info("did:"+did);
//			logger.info("dname:"+dname);
//			userService.updateSysDeviceName(did,dname);
//		}catch(Exception e){
//			e.printStackTrace();
//			msg = "error";
//		}
//		JSONObject jso = new JSONObject();
//		jso.accumulate("errmsg", msg);
//		JsonUtil.writeToHtml(resp, jso.toString());
//		
//	}
//	
//	@RequestMapping(value="/deleteWifi.htm")
//	public void deleteWifi(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String msg = "ok";
//		try{
//			Long did=Long.parseLong(req.getParameter("did"));
//			String openid=req.getParameter("openid");
//			logger.info("did:"+did);
//			logger.info("openid:"+openid);
//			userService.deleteWifiBind(did,openid);
//		}catch(Exception e){
//			e.printStackTrace();
//			msg = "error";
//		}
//		JSONObject jso = new JSONObject();
//		jso.accumulate("errmsg", msg);
//		JsonUtil.writeToHtml(resp, jso.toString());
//	}
//	
//	@RequestMapping(value="/lightList.htm")
//	public String lightList(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String fogDid = req.getParameter("fogDid");
//		String openid=req.getParameter("openid");
//		logger.info("fogDid:"+fogDid);
//		logger.info("openid:"+openid);
//		List<SysLight> lightList = userService.getLightListByFogDid(fogDid);
//		model.addAttribute("lightList",lightList);
//		model.addAttribute("openid",openid);
//		model.addAttribute("fogDid",fogDid);
//		return "device/lightList";
//	}
//	
//	@RequestMapping(value="/addLight.htm")
//	public void addLight(SysDeviceLightBind sdlu,HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		logger.info("start addLigth");	
//		String msg = "ok";
//		if(sdlu!=null){
//			String id = sdlu.getLightId(); 
//			int type = sdlu.getLightType();
//			SysLight slc = userService.getLightById(id);
//			if(slc==null){
//				userService.addObj(sdlu);
//				SysLight sl = new SysLight();
//				sl.setId(id);
//				sl.setLightType(type);
//				if(type ==2){
//					sl.setName("调光调色温LED灯");
//				}else{
//					sl.setName("RGB+调光调色温LED灯");
//				}
//				sl.setIsOnline(1);
//				userService.addObj(sl);
//			}
//		}else{
//			msg = "error";
//		}
//		JSONObject jso = new JSONObject();
//		jso.accumulate("errmsg", msg);
//		JsonUtil.writeToHtml(resp, jso.toString());
//		logger.info("end addLigth");
//	}
//	
//	
//	@RequestMapping(value="/oneLightControle.htm")
//	public String oneLightControle(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		String openid=req.getParameter("openid");
//		String lightId=req.getParameter("lightId");
//		String fogDid=req.getParameter("fogDid");
//		int lightType=Integer.parseInt(req.getParameter("lightType"));
//		logger.info("openid:"+openid);
//		model.addAttribute("openid",openid);
//		model.addAttribute("lightType",lightType);
//		//根据lightid找到light表的参数，到页面初始化
//		SysLight sysLight = userService.getLightById(lightId);
//		//根据openid lightid 获取 场景参数
//		List<SysScene> sysScenes = userService.getSysSceneForOneLight(openid,lightId);
//		model.addAttribute("sysScenes",sysScenes);
//		model.addAttribute("sysLight",sysLight);
//		model.addAttribute("fogDid",fogDid);
//		String nextUrl = "device/oneLightControleRGB";
//		if(lightType == 2){
//			nextUrl = "device/oneLightControle";
//		}
//		return nextUrl;
//	}
//	
//	@RequestMapping(value="/addSceneForOneLight.htm")
//	public void addSceneForOneLight(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		Long did=Long.parseLong(req.getParameter("did"));
//		String openid=req.getParameter("openid");
//		Long lightId=Long.parseLong(req.getParameter("lightId"));
//		int lightType=Integer.parseInt(req.getParameter("lightType"));
//		String name = req.getParameter("name");
//		Long brightness=Long.parseLong(req.getParameter("brightness"));
//		int colorBrightness=Integer.parseInt(req.getParameter("colorBrightness"));
//		int onOff=Integer.parseInt(req.getParameter("onOff"));
//		Long rgb=Long.parseLong(req.getParameter("rgb"));
//		int swithLight=Integer.parseInt(req.getParameter("swithLight"));
//		Long temperature=Long.parseLong(req.getParameter("temperature"));
//		int whiteBrightness=Integer.parseInt(req.getParameter("whiteBrightness"));
//		String msg = "ok";
//		SysScene ss = new SysScene();
//		ss.setName(name);
//		ss.setOpenid(openid);
//		ss.setDid(did);
//	//	ss.setLightId(lightId);
//		ss.setSceneType(0);
////		ss.setCreateTime(new Date());
//		SysSceneDetail ssd = new SysSceneDetail();
//	//	ssd.setLightId(lightId);
//		ssd.setBrightness(brightness);
//		ssd.setColorBrightness(colorBrightness);
//		ssd.setOnOff(onOff);
//		ssd.setRgb(rgb);
//		ssd.setSwithLight(swithLight);
//		ssd.setTemperature(temperature);
//		ssd.setWhiteBrightness(whiteBrightness);
////		ssd.setCreateTime(new Date());
//		Long sceneId = null;
//		
//		try{
//			sceneId = (Long)userService.addObj2(ss);
//			ssd.setSceneId(sceneId);
//			userService.addObj2(ssd);
//		}catch(Exception e){
//			logger.info(e);
//			msg = "error";
//			
//		}
//		
//		JSONObject jso = new JSONObject();
//		jso.accumulate("errmsg", msg);
//		jso.accumulate("id", sceneId);
//		JsonUtil.writeToHtml(resp, jso.toString());
//	}
//	
//	@RequestMapping(value="/deleteSceneForOneLight.htm")
//	public void deleteSceneForOneLight(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
//		Long id=Long.parseLong(req.getParameter("id"));
//		String msg = "ok";
//		try{
//			userService.deleteSceneForOneLight(id);
//		}catch(Exception e){
//			logger.info(e);
//			msg = "error";
//		}
//		JSONObject jso = new JSONObject();
//		jso.accumulate("errmsg", msg);
//		JsonUtil.writeToHtml(resp, jso.toString());
//		
//	}
//	
//	
//
//}
