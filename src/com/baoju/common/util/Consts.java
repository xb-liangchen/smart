package com.baoju.common.util;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;

import com.baoju.common.util.properties.SysConfigUtil;

public class Consts {
	
	public static ApplicationContext appcontext = null;
	
	public static SimpleDateFormat DF_TIME=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat TIME_NOYEAR=new SimpleDateFormat("MM-dd HH:mm:ss");
	
	public static SimpleDateFormat TIME=new SimpleDateFormat("HH:mm:ss");
	
	public static SimpleDateFormat DF_DATE1=new SimpleDateFormat("yyyy/MM/dd");
	
	public static String PATH_PREFIX="http://"+SysConfigUtil.getString("ip"); //部署ip
	
	public static String WAR_NAME;//项目名
	
	//public static final String SESSION_USER="sessionUser"; //SessionUser对象 不要随便修改
	
	public static final String OPENID="openid";
	
	//String tempId="SeaPCT222dnrKrQ_qcBXxRDt-m_cDsyQwDvbiS07JtQ";
	//String tempId="uBQgjQCswdGJ-12ZiynpcFqBjbQHlz2iuxJRdvP1TxY";
	/**模板消息ID*/
	public static final String TEMPID="5Tdb2dB4J5pKOeeoMnOTYod_tPQ1COPHnj8IMOXR958";
	
	/**日志：是否推送到微信消息表示*/
	public static final int IS_PUSH_YES=0;
	
	public static final int IS_PUSH_NO=1;
	
	/**主机设备、身份卡 在线1*/
	public static final Integer IS_ONLINE_YES=1;
	
	/**主机设备、身份卡 不在线0*/
	public static final Integer IS_ONLINE_NO=0;

	/**用户绑定设备 未知0*/
	public static final int USERDDEVICE_BIND_ISCURRENT_NONE=0;
	
	/**用户绑定设备 当前使用设备中 1 */
	public static final int USERDDEVICE_BIND_ISCURRENT_Y=1;
	
	/**用户绑定设备 当前未使用设备 2 */
	public static final int USERDDEVICE_BIND_ISCURRENT_N=2;
	
	
	/**有身份卡 */
	public static final int USER_HASCARD_YES=1;
	
	/**无身份卡*/
	public static final int USER_HASCARD_NO=0;
	
	
	public static final String METHOD_GET="GET";
	
	public static final String Method_POST="POST";
	
	public static final String APPID=SysConfigUtil.getString("appid");
	
	public static final String SECRET=SysConfigUtil.getString("secret");
	
	public static String ACCESS_TOKEN;//
	
	public static String JSAPI_TICKET;
	
	////////////////日志类型///////////////////
	public static String LG_OPER_TYPE_CONNWF="connwf";//联网
	
	public static String LG_OPER_TYPE_GUANZHU="guanzhu";//关注
	
	public static String LG_OPER_TYPE_QXGUANZHU="qxguanzhu";//取消关注
	
	public static String LG_OPER_TYPE_BIND="bind";//绑定
	
	public static String LG_OPER_TYPE_UNBIND="unbind";//解除绑定
	
	public static String LG_OPER_TYPE_LOCK="lock";//布防
	
	public static String LG_OPER_TYPE_UNLOCK="unlock";//撤防
	
	public static String LG_OPER_TYPE_NETWORKOFF_ALARM="networkAlarm";//通信中断报警
	
	public static String LG_OPER_TYPE_INSERT="insert";//插入
	
	public static String LG_OPER_TYPE_UPDATE="update";//修改
	
	public static String LG_OPER_TYPE_DELETE="delete";//删除
	
	
	///////////////////用户 常量
	public static final int USER_HASIDCARD_NO=0;//无身份卡
	public static final int USER_HASIDCARD_YES=1;//有身份卡
	
	public static final int USER_FLAG_WX=0;//微信用户
	public static final int USER_FLAG_NOTWX=1;//无微信用户
	public static final int USER_FLAG_EMERG=2;//紧急联系人
	
	
	public static final int USER_HASWEIXIN_NO=0;//无微信账号
	public static final int USER_HASWEIXIN_YES=1;//有微信账号
	
	
	////////////////控制指令//////////////
	/**0:撤防；1：布防；2：取消警报*/
	public static final Integer CONTL_DEV_ACTION_CF=0;
	public static final Integer CONTL_DEV_ACTION_BF=1;
	public static final Integer CONTL_DEV_ACTION_QX=2;
	
	/*报警组件类型*/
	/**报警组件类型-门磁探头1*/
	public static final Integer ALARM_MUDULENUM_DOOR=1;
	
	/**报警组件类型-周界探头2*/
	public static final Integer ALARM_MUDULENUM_ROUND=3;
	
	/**报警组件类型-室内探头3*/
	public static final Integer ALARM_MUDULENUM_INDOOR=4;
	
	/**报警组件类型-特殊探头4*/
	public static final Integer ALARM_MUDULENUM_SPECIAL=5;
	
	/**报警组件类型-识别卡5*/
	public static final Integer ALARM_MUDULENUM_IDCARD=6;
	
	/**报警组件类型-空调6*/
	public static final Integer ALARM_MUDULENUM_AIR=7;
	
	/**报警组件类型-摄像头7*/
	public static final Integer ALARM_MUDULENUM_CAMERA=8;
	
	/////////////报警类型////////////
	/**报警类型-侵入报警1*/
	public static final Integer ALARM_TYPE_INPUT=1;
	
	/**报警类型-触发报警2*/
	public static final Integer ALARM_TYPE_TRIGGER=2;
	
	/**报警类型-低电量报警3*/
	public static final Integer ALARM_TYPE_LOWBATTERY=3;
	
	/**报警类型-通信中断报警4*/
	 public static final Integer  ALARM_TYPE_BREAK=4;
	 public static final Map<Integer, String> ALARM_MUDULENUM_MAP = createALARMMUDULENUMMap();

	    private static Map<Integer, String> createALARMMUDULENUMMap() {
	        Map<Integer, String> ALARM = new HashMap<Integer, String>();
	        /**报警组件类型-门磁探头1*/
	        ALARM.put(ALARM_MUDULENUM_DOOR, "door_config");
	    	/**报警组件类型-周界探头2*/
	    	 ALARM.put(ALARM_MUDULENUM_ROUND, "round_config");
	    	/**报警组件类型-室内探头3*/
	    	 ALARM.put(ALARM_MUDULENUM_INDOOR, "indoor_config");
	    	/**报警组件类型-特殊探头4*/
	    	 ALARM.put(ALARM_MUDULENUM_SPECIAL, "special_config");
	    	/**报警组件类型-识别卡5*/
	    	 ALARM.put(ALARM_MUDULENUM_IDCARD, "idcard_config");
	    	/**报警组件类型-空调6*/
	    	 ALARM.put(ALARM_MUDULENUM_AIR, "air_config");
	    	/**报警组件类型-摄像头7*/
	    	 ALARM.put(ALARM_MUDULENUM_CAMERA, "camera_config");
	        return Collections.unmodifiableMap(ALARM);
	    }
	    public static final Map<Integer, String> ALARM_MUDULENUMStr_MAP = createALARMMUDULStrENUMMap();

	    private static Map<Integer, String> createALARMMUDULStrENUMMap() {
	        Map<Integer, String> ALARM = new HashMap<Integer, String>();
	        /**报警组件类型-门磁探头1*/
	        ALARM.put(ALARM_MUDULENUM_DOOR, "入户门侵入报警");
	    	/**报警组件类型-周界探头2*/
	    	 ALARM.put(ALARM_MUDULENUM_ROUND, "周界侵入报警");
	    	/**报警组件类型-室内探头3*/
	    	 ALARM.put(ALARM_MUDULENUM_INDOOR, "室内侵入报警");
	    	/**报警组件类型-特殊探头4*/
	    	 ALARM.put(ALARM_MUDULENUM_SPECIAL, "特殊情况侵入报警");
	        return Collections.unmodifiableMap(ALARM);
	    }
	public static String getUrl(){
		return Consts.PATH_PREFIX+Consts.WAR_NAME;
	}
	
	public static String getReqUrl(HttpServletRequest req){
		String uri=req.getRequestURL().toString();
		if(req.getQueryString()==null){
		}else{
			uri=uri+"?"+req.getQueryString();
		}
		return uri;
	}
}
