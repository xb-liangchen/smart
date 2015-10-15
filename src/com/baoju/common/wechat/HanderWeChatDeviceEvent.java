package com.baoju.common.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baoju.common.util.Consts;
import com.baoju.weixin.service.IUserService;

/**
 * 绑定/解绑设备
 * @author dengxp
 *
 */
public class HanderWeChatDeviceEvent {
	private static Logger log =LoggerFactory.getLogger(HanderWeChatDeviceEvent.class.getName());  
	
	private static StringBuffer sb = new StringBuffer();
	
	public static String hander(Document document,HttpServletRequest request, HttpServletResponse response){
		sb.setLength(0); 
		
		Element root = document.getRootElement();
        String toUserName = root.elementTextTrim("ToUserName");	//接收方(公众号)
        String fromUserName = root.elementTextTrim("FromUserName"); //发送方（微信用户）
        String createTime = root.elementTextTrim("CreateTime");//消息发送时间
        String msgtype = root.elementTextTrim("MsgType"); //消息类型device_event
        String event = root.elementTextTrim("Event");//事件类型，取值为bind/unbind bind：绑定设备 unbind：解除绑定 
        String eventkey =root.elementTextTrim("DeviceType");  //设备类型，目前为“公众账号原始ID” 
		String content =root.elementTextTrim("Content"); //
		String sessionID =root.elementTextTrim("SessionID"); //
		String openID =root.elementTextTrim("OpenID"); //
		
		
	 	IUserService userService=(IUserService) Consts.appcontext.getBean("userService");
	 	
		if("bind".equals(event)){
			log.info("设备绑定。。。。。。。");
			
		}else if("unbind".equals(event)){
			log.info("解除绑定。。。。。。。");
		}
		
		/*提示微信用户绑定消息*/
		
		sb.append(WeChatReplyUtils.createRevertDeviceBind(root, content));
		
		return sb.toString();
	}
	
}
