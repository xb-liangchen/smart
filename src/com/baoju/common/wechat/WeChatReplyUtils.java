package com.baoju.common.wechat;

import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baoju.common.util.SysConfigurationRead;
import com.baoju.common.wechat.pojo.ImageText;


public class WeChatReplyUtils {
	static Logger logger = LoggerFactory.getLogger(WeChatReplyUtils.class);

	private static SysConfigurationRead ucr = SysConfigurationRead.getInstance();
	
	public static String createRevertDeviceBind(Element root,String content){
		StringBuffer sb=new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+root.elementTextTrim("FromUserName")+"]]></ToUserName>");  
		sb.append("<FromUserName><![CDATA["+root.elementTextTrim("ToUserName")+"]]></FromUserName>");  
		sb.append("<CreateTime>"+root.elementTextTrim("CreateTime")+"</CreateTime>");  
		sb.append("<MsgType><![CDATA["+root.elementTextTrim("MsgType")+"]]></MsgType>");
		
		sb.append("<Event><![CDATA["+root.elementTextTrim("Event")+"]]></Event>");
		sb.append("<DeviceType><![CDATA["+root.elementTextTrim("DeviceType")+"]]></DeviceType>");
		sb.append("<DeviceID><![CDATA["+root.elementTextTrim("DeviceID")+"]]></DeviceID>");
		sb.append("<SessionID><![CDATA["+root.elementTextTrim("SessionID")+"]]></SessionID>");  
		sb.append("<Content><![CDATA["+root.elementTextTrim("Content")+"]]></Content>");  
		sb.append("</xml>");  
	    return sb.toString();  
	}
	/**
	 * 回复文字消息
	 * @param root
	 * @param content
	 * @return
	 */
	public static String creatRevertText(Element root,String content){  
        StringBuffer revert =new StringBuffer();  
        
        //回复文本消息
        revert.append("<xml>");  
        revert.append("<ToUserName><![CDATA["+root.elementTextTrim("FromUserName")+"]]></ToUserName>");  
        revert.append("<FromUserName><![CDATA["+root.elementTextTrim("ToUserName")+"]]></FromUserName>");  
        revert.append("<CreateTime>"+root.elementTextTrim("CreateTime")+"</CreateTime>");  
        revert.append("<MsgType><![CDATA[text]]></MsgType>");  
        revert.append("<Content><![CDATA["+content+"]]></Content>");  
        revert.append("</xml>");  
        return revert.toString();  
    }
    
	/**
	 * 回复图文消息
	 * @param jsonObject
	 * @param replyContent
	 * @return
	 */
	public static String creatRevertImageText(Element root,List<ImageText> list){
        StringBuffer revert =new StringBuffer();  
        
        //回复文本消息
        revert.append("<xml>");  
        revert.append("<ToUserName><![CDATA["+root.elementTextTrim("FromUserName")+"]]></ToUserName>");  
        revert.append("<FromUserName><![CDATA["+root.elementTextTrim("ToUserName")+"]]></FromUserName>");  
        revert.append("<CreateTime>"+root.elementTextTrim("CreateTime")+"</CreateTime>");  
        revert.append("<MsgType><![CDATA[news]]></MsgType>");
        revert.append("<ArticleCount>"+list.size()+"</ArticleCount>");  
        revert.append("<Articles>");
        for (int i = 0; i < list.size(); i++) {
        	ImageText message = list.get(i);
        	revert.append("<item>"); 
 	        revert.append("<Title><![CDATA["+message.getTitle()+"]]></Title>");
 	        revert.append("<Description><![CDATA["+message.getDescription()+"]]></Description>"); 
 	        revert.append("<PicUrl><![CDATA["+message.getPicUrl()+"]]></PicUrl>"); 
 	        revert.append("<Url><![CDATA["+message.getUrl()+"]]></Url>"); 
 	        revert.append("</item>");
		}
        revert.append("</Articles>");  
        revert.append("</xml>");  
        return revert.toString();  
    }
	
	/**
	 * 转发到多客服
	 * @param toUser (微信用户)
	 * @param fromUser (公众号)
	 * @param sb
	 */
	public static void send_DKF(String toUser,String fromUser, StringBuffer sb) {
		sb.append("<xml><ToUserName><![CDATA[")
		.append(toUser)
		.append("]]></ToUserName><FromUserName><![CDATA[")
		.append(fromUser)
		.append("]]></FromUserName><CreateTime>")
		.append(System.currentTimeMillis())
		.append("</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType></xml>");
	}
	
	/**
	 * 转发到多客服（指定客服号）
	 * @param toUser (微信用户)
	 * @param fromUser (公众号)
	 * @param sb
	 * @param dkfAccount (客服号)
	 */
	public static void send_DKF_single(String toUser,String fromUser, StringBuffer sb,String dkfAccount) {
		sb.append("<xml><ToUserName><![CDATA[")
		.append(toUser)
		.append("]]></ToUserName><FromUserName><![CDATA[")
		.append(fromUser)
		.append("]]></FromUserName><CreateTime>")
		.append(System.currentTimeMillis())
		.append("</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType>")
		.append("<TransInfo><KfAccount>![CDATA[")
		.append(dkfAccount) //test1@test
		.append("]]</KfAccount></TransInfo></xml>");
	
	}
	
	/**
	 * 推送微信模板消息（上门取衣预约通知）
	 * @param str[0] first
	 * @param str[1] 订单号
	 * @param str[2] 会员卡号
	 * @param str[3] 取衣日期
	 * @param str[4] 衣物件数
	 * @param str[5] 取衣地址
	 * @param str[6] remark
	 */
	public static void send_template(String toUser,String templateUrl,String[] str) {
		String content = "{\"touser\":\""+toUser+"\",\"template_id\":\"BbfTOkx00nlRDGiYBnKO4vHg2eq5CrMgqWrYLmnpeMs\",\"url\":\""+templateUrl+"\",\"topcolor\":\"#FF0000\"," +
						"\"data\":{" +
							"\"first\": {\"value\":\""+str[0]+"\",\"color\":\"#0A0A0A\"}," +
							"\"keyword1\": {\"value\":\""+str[1]+"\",\"color\":\"#0A0A0A\"}," +
							"\"keyword2\": {\"value\":\""+str[2]+"\",\"color\":\"#0A0A0A\"}," +
							"\"keyword3\": {\"value\":\""+str[3]+"\",\"color\":\"#0A0A0A\"}," +
							"\"keyword4\": {\"value\":\""+str[4]+"\",\"color\":\"#0A0A0A\"}," +
							"\"keyword5\": {\"value\":\""+str[5]+"\",\"color\":\"#0A0A0A\"}," +
							"\"remark\": {\"value\":\""+str[6]+"\",\"color\":\"#0A0A0A\"}" +
						"}}";
		logger.info("@@@@@@@----content:"+content);
		String msg = WeiXinUtil.pushTemplateDataToUser(content, WeiXinUtil.getToken());
	
	}

}
