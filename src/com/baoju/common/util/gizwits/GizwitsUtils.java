package com.baoju.common.util.gizwits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.baoju.common.util.Consts;
import com.baoju.common.util.date.TimeStampConvertDate;
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.str.NumberCaseUtil;
import com.baoju.common.util.str.StringUtils;
import com.baoju.common.util.url.HttpConnUtil;
import com.baoju.weixin.model.HttpRespObject;

/**
 * 机智云请求接口
 * @author demi
 *
 */
public class GizwitsUtils {

	private static Logger logger=Logger.getLogger(GizwitsUtils.class);
//	private static Map<String,String> timeFlag = new HashMap<String,String>();
	/**
	 * 创建匿名用户
	 * @param openid  微信用户的 openid。
	 * @return
	 * { 
	    "uid": "akkdlfeiow", 
	    "token": "akdlfkad",
	    "expire_at": 13894002020
	  }
	 */
	public static JSONObject createUser(String openid){
		JSONObject obj=new JSONObject();
		obj.put("phone_id", openid);
		String action="http://api.gizwits.com/app/users";
		Map<String, String> headers=new HashMap<String, String>();
		String jsonstr=HttpConnUtil.getGizwitsRespJSON(action, obj,headers,Consts.Method_POST);
		JSONObject robj=obj.fromObject(jsonstr);
		logger.info("创建匿名用户return:"+robj);
		return robj;
	}
	
	/**
	 * 查询设备
	 * @param openid
	 * @return
	* {
	    "did": "abcada",
	    "passcode": "123456",
	}
	 * 
	 */
	public static JSONObject findDevice(String mac){
		String product_key=SysConfigUtil.getString("gizwits.product_key");
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		
		String action="http://api.gizwits.com/app/devices?product_key="+product_key+"&mac="+mac;
		
		String jsonstr=HttpConnUtil.getGizwitsRespJSON(action,obj ,headers,null);
		JSONObject robj=obj.fromObject(jsonstr);
		return robj;
	}
	/**
	 * 获取设备详细信息   (需用户绑定后才能查看)
	 * @param args
	 * {
    "product_key": "akdlfkad",
    "did": "abcada",
    "mac": "1122334455667788",
    "is_online": false,
    "passcode": "123456",
    "host": "m2m.gizwits.com",
    "port": "3128"
}
	 * 
	 */
	public static JSONObject getDeviceDetail(String gizUserToken,String did){
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Gizwits-User-token", gizUserToken);
		String action="http://api.gizwits.com/app/devices/"+did;
		
		String jsonstr=HttpConnUtil.getGizwitsRespJSON(action,obj ,headers,null);
		JSONObject robj=obj.fromObject(jsonstr);
		return robj;
	}
	
	/**
	 * 查找设备是否在线
	 * @param did  设备id
	 * @return{"prVGMRRbh52w9UCMFVTx92": false}
	 */
	public static boolean isOnline(String gzdid){
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		String action="http://site.gizwits.com/developer/debug/online/"+gzdid;
		try {
			String jsonstr=HttpConnUtil.getGizwitsRespJSON(action,obj ,headers,null);
			JSONObject robj=obj.fromObject(jsonstr);
			return (Boolean) robj.get(gzdid);
		} catch (Exception e) {
			return false;
		}
		
		
		
		
	}
	
	/**
	 * 绑定设备
	 * @param gizUserToken
	 * @param did
	 * @param passcode
	 * @return  {
        "success": ['abc', 'add'],
        "failed": ['adad', 'ee']
   }
	 */
	public static boolean bandDevice(String gizUserToken,String did,String passcode){
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Gizwits-User-token", gizUserToken);
		String action="http://api.gizwits.com/app/bindings";
		JSONArray jsarr=new JSONArray();
		JSONObject obj1=new JSONObject();
		obj1.put("did", did);
		if(StringUtils.isNotEmpty(passcode)){
			obj1.put("passcode", passcode);
			obj1.put("remark", "");
		}
		
		jsarr.add(obj1);
		obj.put("devices", jsarr);
		String jsonstr=HttpConnUtil.getGizwitsRespJSON(action,obj ,headers,Consts.Method_POST);;
		JSONObject robj=obj.fromObject(jsonstr);
		if(robj.getString("success")!=null && !"".equals( robj.getString("success"))){
			return true;
		}
		return false;
		
		
	}
	/**
	 * 控制设备
	 * @param did
	 * @param gizUserToken
	 * @param attr
	 * @param val
	 * @return
	 */
	public static boolean controlDevice(String gizdid,String gizUserToken,String attr,Integer val){
		String result="";
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		headers.put("X-Gizwits-User-token", gizUserToken);
		
		String action="http://api.gizwits.com/app/control/"+gizdid;
		obj.put("attr", attr);
		obj.put("val", val);
		HttpRespObject resp=HttpConnUtil.sendPost(action, obj.toString(), headers);
		
		
		if(resp.isSuccess()){
			result = resp.getContent().toString();
			//System.out.println("结果："+result);
			return true;
		}else{
			logger.info("访问失败："+resp.getStatusCode());
			return false;
		}
		
		
	}
	
	/**
	 * 控制设备
	 * @param did
	 * @param gizUserToken
	 * @param attr
	 * @param val
	 * @return
	 */
	public static boolean controlDevice2(String gizdid,String gizUserToken,String temps){
		String result="";
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		headers.put("X-Gizwits-User-token", gizUserToken);
		
		String action="http://api.gizwits.com/app/control/"+gizdid;
		obj.put("attrs", temps);
		HttpRespObject resp=HttpConnUtil.sendPost(action, obj.toString(), headers);
		System.out.println("obj:///"+obj.toString());
		
		if(resp.isSuccess()){
			result = resp.getContent().toString();
			//System.out.println("结果："+result);
			return true;
		}else{
			logger.info("访问失败："+resp.getStatusCode());
			return false;
		}
		
		
	}
	
	
	/**
	 * 读取信息  获取设备最近上传数据
	 * @param did 
	 * @param gizUserToken
	 * @param attr
	 * @param val
	 * @return
	 */
	public static JSONArray readDeviceData2(String token,String did,String attr,int limit){
		String result="";
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		String product_key=SysConfigUtil.getString("gizwits.product_key");
		String info="&name="+attr;
		String action="http://api.gizwits.com/app/devdata2?product_key="+product_key+"&did="+did+"&limit=20&skip=0"+info;
		
		HttpRespObject resp=HttpConnUtil.sendGet(action, headers);
		if(resp.isSuccess()){
			result = resp.getContent().toString();
		}else{
			logger.info("访问失败："+resp.getStatusCode());
		}
		logger.info("\n"+attr+"   result:"+result);
		return new JSONObject().fromObject(result).getJSONArray("data");
	}
	/**
	 * 读取信息  获取设备最近上传数据
	 * @param did 
	 * @param gizUserToken
	 * @param attr
	 * @param val
	 * @return
	 */
	public static JSONObject readDeviceData(String did){
		String result="";
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		String action="http://api.gizwits.com/app/devdata/"+did+"/latest";
	/*	try{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			String today=df.format(new Date());
			if(!timeFlag.containsKey(device.getGizDid()+"update_time")||!today.equals(timeFlag.get(device.getGizDid()+"update_time")))
			{
				
				timeFlag.put(device.getGizDid()+"update_time", today);
				 Integer curtime=NumberCaseUtil.getNowtimeToDecimal(new Date());
				 log.info("======更新设备时间 "+curtime);
					String gizUserToken=userService.getDeviceUserToken(openid, device.getGizDid(), device.getGizPasscode());
					GizwitsUtils.controlDevice(device.getGizDid(),gizUserToken,"now_time",curtime);
			}
			
		}catch(Exception e){
			
		}*/
		
		HttpRespObject resp=HttpConnUtil.sendGet(action, headers);
		if(resp.isSuccess()){
			result = resp.getContent().toString();
		}else{
			logger.info("访问失败："+resp.getStatusCode());
		}
		logger.info("\n result:"+result);
		return new JSONObject().fromObject(result).getJSONObject("attr");
	}
	
	/**
	 * 读取信息  获取设备最近上传数据
	 * @param did 
	 * @param gizUserToken
	 * @param attr
	 * @param val
	 * @return
	 */
	public static JSONObject readDeviceData1(String did){
		String result="";
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		String action="http://api.gizwits.com/app/devdata/"+did+"/latest";
		HttpRespObject resp=HttpConnUtil.sendGet(action, headers);
		if(resp.isSuccess()){
			result = resp.getContent().toString();
		}else{
			logger.info("访问失败："+resp.getStatusCode());
		}
		logger.info("\n result:"+result);
		return new JSONObject().fromObject(result);
	}
	
	/**
	 * 获取添加组件成功反馈组件编号
	 * @param gizdid
	 * @param alarmtype
	 * @return
	 */
	public static JSONObject getAddFittingsBackNum(String gizdid,int alarmtype,String cmdSts){
		JSONObject obj=null;
		Long updatedAt;
		do{
			obj=readDeviceData1(gizdid);
			updatedAt=obj.getLong("updated_at");
		}while(updatedAt>=(System.currentTimeMillis()/1000-3));//
		JSONObject attr=obj.getJSONObject("attr");
		JSONObject jsonresult=new JSONObject();
		String res=attr.getString("fittings_back");
		String cmd=attr.getString("cmd_Fittings");	
			logger.info(gizdid+"查找添加组件"+alarmtype+"反馈fittings_back："+res+"反馈cmd_Fittings："+cmd);
		 	if (("0").equals(res)|| !cmd.startsWith(cmdSts.substring(0, 4)) ||!cmd.substring(6, 12).equalsIgnoreCase(cmdSts.substring(6, 12)))
 			{
 		     return null;
 			}
		 	
			if(StringUtils.isNotEmpty(res)){
				if(res.length()>0){
					res=NumberCaseUtil.toHexString(Integer.parseInt(res));
					res=NumberCaseUtil.appendZero(res, 8);
					logger.info("转换十六进制后："+res);//01000301
					String typestr=NumberCaseUtil.appendZero(""+alarmtype, 2);
					if(res.substring(0, 2).equals("01") && res.substring(4, 6).equals(typestr)){//添加组件
						String type=res.substring(3, 4);// 0-失败  1-成功 2-重复 3-已满 4-超时
						logger.info("操作类型："+type+"(0-失败  1-成功 2-重复 3-已满 4-超时)");
						jsonresult.put("result", type);
						if("1".equals(type)||"2".equals(type)){
							Integer result=Integer.valueOf(res.substring(6,8));
							logger.info("反馈结果："+result);
							jsonresult.put("num", result);
							
						}						
						return jsonresult;
					}
				}
			}
		
		return null;
	}
	
	/**
	 * 获取添加组件成功反馈组件编号
	 * @param gizdid
	 * @param alarmtype
	 * @return
	 */
	public static boolean getDelFittings(String gizdid,int alarmtype,int num){
		JSONObject obj=readDeviceData(gizdid);
		
		String res=obj.getString("fittings_back");
		String cmd=obj.getString("cmd_Fittings");	
			logger.info(gizdid+"查找删除组件"+alarmtype+"反馈fittings_back："+res);
		 	int n=0;
		 	while ((("0").equals(res)|| !cmd.startsWith("020"+alarmtype+NumberCaseUtil.appendZero(""+num, 2))) && n<10)
			{
				 n++;
				 try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 obj=readDeviceData(gizdid);			
				 res=obj.getString("fittings_back");
				 logger.info(gizdid+"查找删除组件"+alarmtype+"反馈fittings_back："+res);
			}  
		 	if (("0").equals(res))
 			{
 		     return false;
 			}
		 	
			if(StringUtils.isNotEmpty(res)){
				if(res.length()>0){
					res=NumberCaseUtil.toHexString(Integer.parseInt(res));
					res=NumberCaseUtil.appendZero(res, 8);
					logger.info("转换十六进制后："+res);//01000301
					String typestr=NumberCaseUtil.appendZero(""+alarmtype, 2);
					if(res.substring(0, 2).equals("02") && res.substring(4, 6).equals(typestr)){//添加组件
						String type=res.substring(3, 4);// 0-失败  1-成功 2-重复 3-已满 4-超时
						logger.info("操作类型："+type+"(0-失败  1-成功 2-重复 3-已满 4-超时)");
						if("1".equals(type)){
							Integer result=Integer.valueOf(res.substring(6,8));
							logger.info("反馈结果："+result);
							return true;
						}
						else if("0".equals(type)){
							String fitting=obj.getString(Consts.ALARM_MUDULENUM_MAP.get(alarmtype));
							fitting=NumberCaseUtil.appendZero(Long.toBinaryString(Integer.parseInt(fitting)),16);
							if ("0".equals(fitting.substring(15-num, 16-num))){
								return true;
							}
						}
					}
				}
			}
		
		return false;
	}
	
	public static boolean addFinder(String gizdid,String gizUserToken,String code){
		String result="";
		//JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		headers.put("X-Gizwits-User-token", gizUserToken);
		//例如5350C000031502000000 发送：01 03 00 53 50 C0
		/*添加-组件类型A4-组件编号-组件地址1A0-地址2A1-地址3 A2*/
		String action="http://api.gizwits.com/app/control/"+gizdid;
		Integer type=Integer.parseInt(code.substring(8, 10));
		//if (type>1) type+=1;
		String res="010"+type+"00"+code.substring(0, 2)+""+code.substring(2, 4)+""+code.substring(4, 6);
		//obj.put("attr", "cmd_Fittings");
		//obj.put("val", res);
		
		String str="{\"attrs\": {\"cmd_Fittings\": \""+res+"\"}}";
		logger.info("===添加探测器:"+str);
		HttpRespObject resp=HttpConnUtil.sendPost(action, str, headers);
		
		if(resp.isSuccess()){
			//result = resp.getContent().toString();
			//logger.info("\n添加组件 result："+result);
			//logger.info("response："+result);
			return true;
		}else{
			logger.info("访问失败："+resp.getStatusCode());
			return false;
		}
	}
	
	public static boolean addIdcard(String gizdid,String gizUserToken){
		String result="";
		JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		headers.put("X-Gizwits-User-token", gizUserToken);
		String action="http://api.gizwits.com/app/control/"+gizdid;
		//obj.put("attrs", "cmd_Fittings");
		//obj.put("val", "010500000000");
		String str="{\"attrs\": {\"cmd_Fittings\": \"010600000000\"}}";
		HttpRespObject resp=HttpConnUtil.sendPost(action, str, headers);
		
		if(resp.isSuccess()){
			result = resp.getContent().toString();
			logger.info("\n添加身份卡 result："+result);
			return true;
		}else{
			logger.info("访问失败："+resp.getStatusCode());
			return false;
		}
	}
	/**
	 * 删除组件探测器和身份卡
	 * @param gizdid
	 * @param gizUserToken
	 * @param num  编号 0-15 例如：01 ，02， 03
	 * @return
	 */
	public static boolean delteFitting(String gizdid,String gizUserToken,int alarmtype,String num){
		String result="";
		//JSONObject obj=new JSONObject();
		Map<String, String> headers=new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Gizwits-Application-Id",SysConfigUtil.getString("gizwits.appid"));
		headers.put("X-Gizwits-User-token", gizUserToken);
		String action="http://api.gizwits.com/app/control/"+gizdid;
		//obj.put("attr", "cmd_Fittings");
		String res="020"+alarmtype+""+num+"000000";
		//obj.put("val",res);
		logger.info("删除指令："+res);
		String str="{\"attrs\": {\"cmd_Fittings\": \""+res+"\"}}";
		HttpRespObject resp=HttpConnUtil.sendPost(action, str, headers);
		logger.info("\n删除指令："+res+"删除组件类型："+alarmtype+" 编号："+num);
		if(resp.isSuccess()){
			result = resp.getContent().toString();
			logger.info("result:"+result);
			return true;
		}else{
			logger.info("访问失败："+resp.getStatusCode());
			return false;
		}
	}
	
	//@Test
	public void test_getDeviceDetail(){
			JSONObject jo=getDeviceDetail("bfb258d1d53744fba7bec851a0d9e21c", "LCTAC3WzXaEETFL9yQvrqL");
			System.out.println("@@@is_online:"+jo.getBoolean("is_online"));
			System.out.println(isOnline("LCTAC3WzXaEETFL9yQvrqL"));
		}
	
	//@Test
	public void test_controlDevice2(){
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = df.parse("2015-01-01 13:00:00");
			Integer time1=NumberCaseUtil.getTimeToDecimal(date1);
			Date date2=df.parse("2015-01-01 15:00:00");
			Integer time2=NumberCaseUtil.getTimeToDecimal(date2);
			String strs="{\"start_time\":"+time1+",\"end_time\":"+time2+"}";
			GizwitsUtils.controlDevice2("uvVjTCKzJ7VmCUAxVxn93m", "48b85a1b485c4eadbfca4b5eb7b6480a",strs);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test_controlDevice(){
		String str="{\"cmd_Fittings\": \"010600000000\"}";
		boolean result=GizwitsUtils.controlDevice2("uvVjTCKzJ7VmCUAxVxn93m", "e7d024e1d08840809fe4aeb620d830c0",str);
		System.out.println("result:"+result);
	}
	
	@Test
	public void test_deleteFittion(){
		String gizUserToken="01834f2a797b439fb789b5f1f34e6881";
		String did="r5acsJKxWrvmGWU4fEHzys";
		for(int i=0;i<15;i++){
			boolean result=GizwitsUtils.delteFitting(did,gizUserToken, Consts.ALARM_MUDULENUM_IDCARD, NumberCaseUtil.appendZero(String.valueOf(i), 2));
			//getDelFittings(did, 6, i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//@Test
	public void test_findDevice(){
			//String mac="accf234b0ad6";
			//String mac="ACCF234B0AD4";
			String mac="ACCF23502A96";
			String result=GizwitsUtils.findDevice(mac).toString();
			System.out.println("Giz_did======="+result);
		}
	
	//@Test
	public void test_setNowTime(){
		String gizUserToken="a1651c0908534495ad02b42dc57399c5";
		   //String did="prVGMRRbh52w9UCMFVTx92";//AZtNfazzessd7BHmfTZzwj
		   String did="DoWaGhBBrMttzVc3CTeaPs";
		   Integer curtime=NumberCaseUtil.getNowtimeToDecimal(new Date());
		   System.out.println("curtime:"+curtime);
		System.out.println("设置时间："+curtime+" :"+controlDevice(did,gizUserToken,"now_time",curtime));;
		    
	}
   //@Test
	public void test_setDevAction(){
		// //0:撤防；1：布防；2：取消警报
		String gizUserToken="3bbb36621b464e32884f783d3abc064e";
		   //String did="prVGMRRbh52w9UCMFVTx92";//AZtNfazzessd7BHmfTZzwj
		   String did="DoWaGhBBrMttzVc3CTeaPs";
		   System.out.println("=====控制:"+controlDevice(did,gizUserToken,"dev_action",0));;
		    
		   System.out.println("=======最后控制时间："+TimeStampConvertDate.curDateToTimestamp());
		  // System.out.println("=====控制自动布防:"+controlDevice(did,gizUserToken,"guardtime_en",1));
	}
	//@Test
	public void test_readData(){
		  String did="fK895C2KpW8bmjbHX9DFFM";
		/*组件类型：门磁探头1
		组件类型：周界探头2
		组件类型：室内探头3
		组件类型：特殊探头4
		组件类型：识别卡5
		组件类型：空调6
		组件类型：摄像头7*/
	   /*查询设备信息*/
	  JSONObject obj=readDeviceData(did);
	   
	  System.out.println("报警组件alarm_mudulenum:"+obj.get("alarm_mudulenum"));
	   
	  // 1：侵入警报 ；2：触发警报；3：低电量报警；4：通信终端报警
	   System.out.println("报警类型alarm_type:"+obj.get("alarm_type"));
	   
	   System.out.println("设备编号alarm_devnum:"+obj.get("alarm_devnum"));
	   
	   System.out.println("报警时间alarm_time:"+obj.get("alarm_time"));
	   
	   System.out.println("门磁配置情况door_config:"+obj.get("door_config"));
	   
	   System.out.println("周界探测器配置情况round_config:"+obj.get("round_config"));
	   
	   System.out.println("室内探测器配置情况indoor_config:"+obj.get("indoor_config"));
	   
	   System.out.println("特殊探测器配置情况special_config:"+obj.get("special_config"));
	   
	   System.out.println("fittings_back:"+obj.get("fittings_back"));
	   
	   String res=NumberCaseUtil.toHexString((Integer) obj.get("fittings_back"));
	   res=NumberCaseUtil.appendZero(res, 8);
	   
	   System.out.println("fittings_back:转换成16进制："+res);
	   
	   System.out.println("识别卡状态idcard_status:"+obj.getString("idcard_status")+" >"+NumberCaseUtil.toBinary(obj.getInt("idcard_status")));
	   
	   System.out.println("识别卡配置idcard_config:"+obj.getString("idcard_config")+" >"+NumberCaseUtil.toBinary(obj.getInt("idcard_config")));
	   
	   //0-布防   1-撤防       2015-3-27更新
	   if(obj.get("action_status")!=null){
		   String  s=obj.get("action_status").equals(1)?"撤防":"布防";
		   System.out.println("当前布撤防状态："+obj.get("action_status")+"  "+s);
	   }
	   System.out.println("开始时间:"+obj.getString("start_time"));
	   System.out.println("结束时间:"+obj.getString("end_time"));
	   System.out.println("闹铃1:"+obj.getString("alarm_clock1"));
	   System.out.println("闹铃2:"+obj.getString("alarm_clock2"));
	   System.out.println("自动布防guardtime_en:"+obj.getString("guardtime_en"));
	   System.out.println("语音音量day_volume："+obj.getString("day_volume"));
	   System.out.println("警笛alarm_volume:"+obj.getString("alarm_volume"));
	}
	//@Test
	public void test_read(){
		 /*String gizUserToken="3bbb36621b464e32884f783d3abc064e";
		 String did="DoWaGhBBrMttzVc3CTeaPs";
		 String passcode="LDULRYHIIF";*/
		 String did="LCTAC3WzXaEETFL9yQvrqL";
		 System.out.println("@@@@@@test_read:"+readDeviceData(did));
		// readDeviceData2(gizUserToken, did, "idcard_config", 20);
	}
	
	@Test
	public void test_read1(){
		 String did="zhFsHmD82DQYMs7cGzvXrU";
		 System.out.println("@@@@@@test_read:"+readDeviceData1(did));
	}
	   
	   public static void main(String[] args) {
		   
		 String gizUserToken="3bbb36621b464e32884f783d3abc064e";
		 String did="DoWaGhBBrMttzVc3CTeaPs";
		 String passcode="LDULRYHIIF";
		 Integer curtime=NumberCaseUtil.getNowtimeToDecimal(new Date());
		 String code="5350C000041502000000";
		 Integer type=Integer.parseInt(code.substring(8, 10));
			//if (type>1) type+=1;
		 String res="010"+type+"00"+code.substring(0, 2)+""+code.substring(2, 4)+""+code.substring(4, 6);
		 //System.out.println(res);
		 //System.out.println(NumberCaseUtil.appendZero(Long.toBinaryString(Integer.parseInt("3")),16).substring(15-2, 16-2));
		//  3
		// delteFitting(did, gizUserToken, Consts.ALARM_MUDULENUM_IDCARD,"10");
		// addIdcard(did, gizUserToken);
		// System.out.println("设置时间:"+controlDevice(did,gizUserToken,"alarm_clock2",0));;
		//  addFinder(did, gizUserToken, "5350C000031502000000");
		// System.out.println(getAddFittingsBackNum(did,Consts.ALARM_MUDULENUM_INDOOR));;
		}
	   
	  
}
