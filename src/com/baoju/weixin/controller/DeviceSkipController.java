package com.baoju.weixin.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baoju.weixin.entity.SysDeviceUser;
import com.baoju.common.util.date.DateUtils;
import com.baoju.common.util.gizwits.GizwitsUtils;
import com.baoju.common.util.str.NumberCaseUtil;
import com.baoju.weixin.service.IUserService;
/**
 * 
 * @author demi
 *
 */
@Controller
public class DeviceSkipController {
	
	private Logger log=Logger.getLogger(DeviceSkipController.class);
	
	@Resource(name="userService")
	private IUserService userService;
	
	
	@RequestMapping(value="/doPost.htm")
	public String doPost(HttpServletRequest req,HttpServletResponse resp,ModelMap modelMap){
		log.info("正在跳转页面....");
		String flag = req.getParameter("flag");
		 if("alarmset".equals(flag)){//闹钟跳转页面1
			return "set/alarmset";
		}else if("detecorshow".equals(flag)){//探测器页面
			return "set/detecorshow";
		}else if("userManage".equals(flag)){//用户管理页面
			return "user/userManage";
		}else if("addId".equals(flag)){//添加身份卡页面
			return "user/addId";
		}else if("addidcar".equals(flag)){//添加身份后学习识别器页面
			return "user/addidcar";
		}else if("home".equals(flag)){//添加身份后学习识别器页面
			return "control/home";
		}else if("autoset".equals(flag)){//自动布防设置页面
			return "set/autoset";
		}else if("setDevice".equals(flag)){//设备设置页面
			return "set/setDevice";
		}else if("setVoice".equals(flag)){//设备设置页面
			return "set/setVoice";
		}
		else{
			return "";
		}

	}
	
	
	/**
	 * 设备设置
	 * @param req
	 * @param resp
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/setAlarm2.htm")
	public String setDevice(HttpServletRequest req,HttpServletResponse resp,ModelMap modelMap){
		log.info("进入设置设备方法....");
		
		String proberName = req.getParameter("proberName");//探测器名字
		String proberFlag = req.getParameter("proberFlag");//探测器设置标识
		
		String personVoice = req.getParameter("personVoice");//语言音量0-8
		String policeVoice = req.getParameter("policeVoice");//警笛音量1-4
		String attrAlarmVolume = req.getParameter("alarm_volume");
		String attrDayVolume = req.getParameter("alarm_volume");
		String voiceFlag = req.getParameter("voiceFlag");//音量设置标识voiceFlag
		
//		String did = req.getParameter("did");
		String openid = req.getParameter("openid");
//		String attr = req.getParameter("attr");
		
		String gizUserToken="67e2b7d41b9c4fcca13b79b86967c17c";
		String did="prVGMRRbh52w9UCMFVTx92";
		String attr = "alarm_clock1";
//		voiceFlag = "voiceFlag";
		
		if( voiceFlag == "voiceFlag" ){ 
			
			//音量设置
			attrAlarmVolume ="alarm_volume";
			attrDayVolume = "day_volume";
			Integer val = Integer.parseInt("3");//语言音量
			boolean flag  = GizwitsUtils.controlDevice(did, gizUserToken, attrAlarmVolume, val);
			
			Integer val2 = Integer.parseInt("2");//警笛音量
			boolean flag2  = GizwitsUtils.controlDevice(did, gizUserToken, attrDayVolume, val2);
			System.out.println("????>>>>"+flag);
			
			
			return "";
			
		}else if(proberFlag == "proberFlag"){
			//探测器设置
			
			
			
			
			return "set/alarmset";
		}else{
			
			//闹钟设置
			String res = req.getParameter("val");//20150327183000时间戳
//			Integer val = Integer.parseInt(value);
			Date time = DateUtils.formatDate("20150327183000", 6);
			Integer val = NumberCaseUtil.getTimeToDecimal(time);
			attr = "alarm_clock";
//			SysDeviceUser sysDeviceUser = userService.getDeviceUser(openid);
//			String gizUserToken = sysDeviceUser.getToken();
//			log.info("alarmTime="+alarmTime+",did="+did+",openid="+openid+",attr="+attr+",val="+val+",gizUserToken="+gizUserToken+",res="+res);
			boolean flag  = GizwitsUtils.controlDevice(did, gizUserToken, attr, val);
			if(flag){
				modelMap.put("flag", flag);
			}else{
				modelMap.put("flag", flag);
			}
			System.out.println("end="+flag);
			return "set/alarmset";
			
			
		}
		
	}
	
}
