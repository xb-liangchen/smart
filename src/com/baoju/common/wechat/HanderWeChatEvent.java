package com.baoju.common.wechat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.baoju.common.util.Consts;
import com.baoju.common.util.SystemCache;
import com.baoju.common.util.gizwits.GizwitsUtils;
import com.baoju.common.util.properties.MessConfigUtil;
import com.baoju.common.util.str.CharName;
import com.baoju.common.util.str.StringUtils;
import com.baoju.common.util.wechat.InitiativeSendUtil;
import com.baoju.common.wechat.pojo.ImageText;
import com.baoju.weixin.controller.BindDeviceController;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysDeviceUser;
import com.baoju.weixin.entity.SysLog;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.entity.SysUserDeviceBind;
import com.baoju.weixin.service.IUserService;


/**
 * 微信消息处理类（事件）
 * @author terrence
 *
 */
public class HanderWeChatEvent {
	
	private static Logger log =LoggerFactory.getLogger(HanderWeChatEvent.class.getName());  
	
	private static StringBuffer sb = new StringBuffer();
	
	
	public static String hander(Document document,HttpServletRequest request, HttpServletResponse response){
		
		Element root = document.getRootElement();
		final String fromUserName = root.elementTextTrim("FromUserName"); //发送方
        String toUserName = root.elementTextTrim("ToUserName");	//接收方
        String createTime = root.elementTextTrim("CreateTime");//消息发送时间
        String msgtype = root.elementTextTrim("MsgType"); //message类型
		String event = root.elementTextTrim("Event");	//事件类型
   	 	String eventkey =root.elementTextTrim("EventKey");  //事件KEY值
   	 	
   	 	sb.setLength(0); //清空内容
   	 	
   	 	final IUserService userService=(IUserService) Consts.appcontext.getBean("userService");
   	 	final SysUser sysuser=userService.getUserInfo(fromUserName);
   	 	Long uid=null;
   	  
   	 	if(sysuser!=null){
    		uid=sysuser.getId();
    	}
        if("CLICK".equals(event)){	//点击菜单拉取消息时的事件推送 
	       	log.info("点击"+eventkey+"菜单");
	       	
        }else if("VIEW".equals(event)){	//点击菜单跳转链接时的事件推送 
	       	//EventKey 事件KEY值，设置的跳转URL 
	       	log.info("点击view菜单，跳转："+eventkey);
        }else if("subscribe".equals(event)){	//阅读事件
	        //用户扫描带场景值二维码时，可能推送以下两种事件：1.如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
	       	if(eventkey.indexOf("qrscene_")>=0){ //扫二维码关注
	       		log.info("扫描二维码关注，场景一########二维码的参数值："+eventkey);
	       		//EventKey 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	       		String ticket =root.elementTextTrim("Ticket");//二维码的ticket，可用来换取二维码图片 
	       		
	       		log.info("===关注者："+fromUserName+(sysuser==null?"数据库不存在该用户":"数据库已存在该用户"));
	       		BindDeviceController.addUser(sysuser, fromUserName, userService);
	       		
	       	}else{
	       		//常规关注
		       	log.info("常规途径关注："+eventkey);
		       	log.info("===关注者："+fromUserName+(sysuser==null?"数据库不存在该用户":"数据库已存在该用户"));
		       	BindDeviceController.addUser(sysuser, fromUserName, userService);
	       	}
	       	
        }else if("unsubscribe".equals(event)){	//取消阅读事件
	       	//取消关注事件
	       	log.info("点击取消关注");
       	 
	    	SysLog log=new SysLog();
			log.setLogContent("取消关注");
			log.setOperRes(0);//0成功 1失败
			log.setUserId(uid);
			log.setIsPush(Consts.IS_PUSH_NO);
			log.setOperType(Consts.LG_OPER_TYPE_QXGUANZHU);
			log.setOpenid(fromUserName);
			userService.addLog(request,log);
			
			if(uid!=null){
				//删除用户和绑定设备  、探测器、身份卡
				userService.deleteUserAndBind(uid);
			}
        }else if("SCAN".equals(event)){	//二维码事件
	       	//用户扫描带场景值二维码时，可能推送以下两种事件：2.如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
	       	log.info("扫描二维码关注，场景二########二维码scene_id："+eventkey);
	       	//EventKey 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	       	String ticket =  root.elementTextTrim("Ticket");//二维码的ticket，可用来换取二维码图片
       	    log.info("ticket:"+ticket);
       	    
       	    log.info("===关注者："+fromUserName+(sysuser==null?"数据库不存在该用户":"数据库已存在该用户"));
       	    BindDeviceController.addUser(sysuser, fromUserName, userService);
       	    
        }else if("scancode_push".equals(event)){
        	log.info("扫码推事件的事件推送");
        	String creatRevertText = WeChatReplyUtils.creatRevertText(root, "");
        	sb.append(creatRevertText);
        	
        	 if("k_add_device".equals(eventkey)){
             	log.info("----添加设备扫描----");
             	String scanResult=null;
             	
             	if(StringUtils.isNotEmpty(root.element("ScanCodeInfo"))){
             		scanResult=root.element("ScanCodeInfo").elementTextTrim("ScanResult");
             	}
             	log.info("最终扫描结果ScanResult:"+scanResult);
             	
         	   if(scanResult!=null){
         		  final String fscanResult = scanResult;
         		  log.info("启动线程，fscanResult="+fscanResult);
	         		new Thread(new Runnable() {
	      				@Override
	      				public void run() {
	      					if(fscanResult.length()==12){
	                     		log.info("begin mac地址");
//	                     		BindDeviceController.scanBindHostDevice(fromUserName, sysuser, fscanResult, userService);
	                     		log.info("end 绑定mac地址");
	                     	}else if(fscanResult.length()==20){
	                     		log.info("begin 绑定探测器");
//	                     		BindDeviceController.sacnBindFinder(fromUserName, sysuser, fscanResult, userService);
	                     		log.info("end 绑定探测器");
	                     	}
	      				}
	      			}).start();
				}
				
             }
        }else if("scancode_waitmsg".equals(event)){
        	log.info("扫码推事件的事件推送");
        	String creatRevertText = WeChatReplyUtils.creatRevertText(root, "");
        	sb.append(creatRevertText);
        	
        	 if("k_add_device".equals(eventkey)){
             	log.info("----添加设备扫描----");
             	String scanResult=null;
             	
             	if(StringUtils.isNotEmpty(root.element("ScanCodeInfo"))){
             		scanResult=root.element("ScanCodeInfo").elementTextTrim("ScanResult");
             	}
             	log.info("最终扫描结果ScanResult:"+scanResult);
             	
         	   if(scanResult!=null){
         		  final String fscanResult = scanResult;
         		  log.info("启动线程，fscanResult="+fscanResult);
	         		new Thread(new Runnable() {
	      				@Override
	      				public void run() {
	      					if(fscanResult.length()==12){
	                     		log.info("begin mac地址");
//	                     		BindDeviceController.scanBindHostDevice(fromUserName, sysuser, fscanResult, userService);
	                     		log.info("end 绑定mac地址");
	                     	}else if(fscanResult.length()==20 && !fscanResult.substring(9, 10).equals("6")){
	                     		log.info("begin 绑定探测器");
//	                     		BindDeviceController.sacnBindFinder(fromUserName, sysuser, fscanResult, userService);
	                     		log.info("end 绑定探测器");
	                     	}
	      				}
	      			}).start();
				}
				
             }
        } else if("LOCATION".equals(event)){	//地理位置事件
        	log.info("地理位置----事件");
	       	String latitude =root.elementTextTrim("Latitude");//地理位置纬度 
	       	String longitude =root.elementTextTrim("Longitude");//地理位置经度
	       	String precision =root.elementTextTrim("Precision");//地理位置精度
	       	log.info("上报地理位置,纬度="+latitude+",经度="+longitude+",精度="+precision);
	       	SystemCache cache = SystemCache.getInstance();
	    	log.info("缓存经纬度:"+latitude+","+longitude);
	       	cache.addSystemCache(fromUserName, latitude+","+longitude);
	       	Object systemCache = cache.getSystemCache(fromUserName);
	    	log.info(fromUserName);
	       	log.info(systemCache.toString());
        }else{
        	log.info("事件event："+event+"菜单 事件KEY值:"+eventkey);
        	
           
        }
		return sb.toString();
	}
	

}
