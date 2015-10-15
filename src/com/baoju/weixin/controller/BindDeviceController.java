package com.baoju.weixin.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baoju.common.util.Consts;
import com.baoju.common.util.MD5;
import com.baoju.common.util.fogcloud.FogcloudUtils;
import com.baoju.common.util.gizwits.GizwitsUtils;
import com.baoju.common.util.json.JsonUtil;
import com.baoju.common.util.properties.MessConfigUtil;
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.str.CharName;
import com.baoju.common.util.str.StringUtils;
import com.baoju.common.util.wechat.InitiativeSendUtil;
import com.baoju.common.util.wechat.Sign;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysDeviceUser;
import com.baoju.weixin.entity.SysLog;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.entity.SysUserDeviceBind;
import com.baoju.weixin.service.IUserService;

/**
 * 绑定主机-探测器
 * @author demi
 *
 */
@Controller
@RequestMapping("/bindDevice.htm")
public class BindDeviceController {

	private final static Logger logger=Logger.getLogger(BindDeviceController.class);
	//private static Logger log=Logger.getLogger(BindDeviceController.class);
	
	@Resource(name="userService")
	private IUserService userService;
	
	public static void addUser(SysUser sysuser, String fromUserName,IUserService userService2) {
		logger.info("添加用户===sysuser:"+sysuser+" fromUserName:"+fromUserName);
		Long uid=null;
		if(sysuser==null){
       		JSONObject user=InitiativeSendUtil.getUserinfo(fromUserName);
       		logger.info("根据openid获取到：userJson:"+user);
       		SysUser obj=new SysUser();
       		obj.setSex(user.getInt("sex"));//1男  2女
       		obj.setOpenid(fromUserName);
       		obj.setHeadimgurl(user.getString("headimgurl"));
       		obj.setNickName(user.getString("nickname"));
       		obj.setCreateTime(new Date());
       		uid=(Long) userService2.addObj(obj);
       	}
       	
       	InitiativeSendUtil.sendTextMessageToUser(MessConfigUtil.getString("mess.guanzhu"), fromUserName);
       	
       	SysLog log=new SysLog();
		log.setLogContent(MessConfigUtil.getString("mess.guanzhu"));
		log.setOperRes(0);//0成功 1失败
		log.setUserId(uid);
		log.setIsPush(Consts.IS_PUSH_YES);
		log.setOperType(Consts.LG_OPER_TYPE_GUANZHU);
		log.setOpenid(fromUserName);
		userService2.addLog(null,log);
	}

	@RequestMapping(params="scanQRCode")
	public void scanQRCode(HttpServletRequest req,HttpServletResponse resp,ModelMap model){
		logger.info("start sacnQRCode");
		String openid=req.getParameter("openid");
		logger.info("===json.openid:"+openid);		
		String mac=req.getParameter("mac");
        logger.info("mac:"+mac);		
		SysUser user=userService.getUserInfo(openid);
		JSONObject result_json=new JSONObject();
		String url="";
		//是否有fog账号,没有就创建,获取fog uid
		String user_id = userService.getFogUid(openid);
		//绑定用户和wifi
		boolean flag = userService.bindDevice(user_id,mac,openid);
	//	boolean flag = FogcloudUtils.bindDevice(user_id, mac);
		
		if(flag)
			JsonUtil.writeToHtml(resp, "{\"errcode\":\"0\",\"errmsg\" : \"ok\",\"url\": \""+url+"\"}");		
		else
			JsonUtil.writeToHtml(resp, "{\"errcode\":\"1\",\"errmsg\" : \"error\",\"url\": \""+url+"\"}");
		logger.info("end sacnQRCode");
	}
	
}
