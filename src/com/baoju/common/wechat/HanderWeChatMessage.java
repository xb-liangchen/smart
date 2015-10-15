package com.baoju.common.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信消息处理类（消息）
 * @author terrence
 *
 */
public class HanderWeChatMessage {
	private static Logger log =LoggerFactory.getLogger(HanderWeChatMessage.class.getName());  
	
	private static StringBuffer sb = new StringBuffer();
	
	public static String hander(Document document,HttpServletRequest request, HttpServletResponse response){
		Element root = document.getRootElement();
        String toUserName = root.elementTextTrim("ToUserName");	//接收方
        String fromUserName = root.elementTextTrim("FromUserName"); //发送方
        String createTime = root.elementTextTrim("CreateTime");//消息发送时间
        String msgtype = root.elementTextTrim("MsgType"); //消息类型
   	 	String eventkey =root.elementTextTrim("EventKey");  //事件KEY值
   	 
		String msgId =root.elementTextTrim("MsgId"); //消息id，64位整型 
		
		sb.setLength(0); //清空内容
		 
		if("text".equals(msgtype)){	//文本消息
			log.info("文本消息----消息");
			String content = root.elementTextTrim("Content"); //文本消息内容
			log.info("content="+content);
			//String responseStr = WeChatReplyUtils.creatRevertText(root,"已接收到文本消息");//创建XML
		   // sb.append(responseStr);
		}else if("image".equals(msgtype)){	//图片消息
			log.info("图片消息----消息");
			String content = root.elementTextTrim("PicUrl");	//图片链接
			String mediaId = root.elementTextTrim("MediaId");	//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。 
			
		}else if("voice".equals(msgtype)){	//语音消息
			log.info("语音消息----消息");
			String mediaId = root.elementTextTrim("MediaId");	//语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
			String format = root.elementTextTrim("Format"); 	//语音格式，如amr，speex等 
			 
		}else if("video".equals(msgtype)){	//视频消息
			log.info("视频消息----消息");
			String mediaId = root.elementTextTrim("MediaId");	//视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
			String thumbMediaId = root.elementTextTrim("ThumbMediaId"); //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 
		 
		}else if("location".equals(msgtype)){	//地理位置消息
			log.info("地理位置----消息");
			String location_X = root.elementTextTrim("Location_X"); 	//地理位置维度
			String location_Y = root.elementTextTrim("Location_Y"); 	//地理位置经度
			String scale = root.elementTextTrim("Scale"); 	//地图缩放大小
			String label = root.elementTextTrim("Label"); 	//地理位置信息 
			log.info("上报用户地理位置="+location_X+","+location_Y);
		}else if("link".equals(msgtype)){	//链接消息
			log.info("链接消息----消息");
			String title = root.elementTextTrim("Title"); 	//消息标题
			String description = root.elementTextTrim("Description"); 	//消息描述
			String url = root.elementTextTrim("Url"); 	//消息链接 
			 
		}
		return sb.toString();
	}
	
}
