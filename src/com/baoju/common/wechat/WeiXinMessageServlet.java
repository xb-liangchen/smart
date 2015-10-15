package com.baoju.common.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WeiXinMessageServlet extends HttpServlet {
	private Logger log =LoggerFactory.getLogger(this.getClass().getName());  
 	
    private static final long serialVersionUID = 1L;  

	public WeiXinMessageServlet() {
		super();
	}
    
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	log.info("######doGet 消息");
    	log.info("######与微信平台对接");
        connect(request,response);  
    }
  
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	log.info("######doPost 消息");
    	log.info("######接收微信平台消息");
        message(request,response);
    }
       
      
    /** 
     *  
     * <p>接入连接生效验证</p> 
     */  
    private void connect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{  
        log.info("RemoteAddr: "+ request.getRemoteAddr());  
        log.info("QueryString: "+ request.getQueryString());  
         if(!accessing(request, response)){  
             log.info("对接失败......");  
             PrintWriter writer = response.getWriter();
             writer.write("service connect fail.");
             return ;  
         }  
        String echostr = WeiXinUtil.echostr;  
        if(echostr!=null && !"".equals(echostr)){  
            log.info("对接成功.........");  
            response.getWriter().print(echostr);//完成相互认证  
        }  
    }  
    /** 
     * 
     *<p>用来接收微信公众平台的验证</p>  
     */  
    private boolean accessing(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {  
        String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");  
        if( isEmpty(signature)){  
            return false;  
        }  
        if(isEmpty(timestamp)){  
            return false;  
        }  
        if(isEmpty(nonce)){  
            return false;  
        }  
        if(isEmpty(echostr)){  
            return false;  
        }  
        String[] ArrTmp = { WeiXinUtil.Token, timestamp, nonce };  
        Arrays.sort(ArrTmp);  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < ArrTmp.length; i++) {  
            sb.append(ArrTmp[i]);  
        }  
        String pwd = Encrypt(sb.toString());  
           
        log.info("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"pwd:"+pwd+"echostr:"+echostr);  
          
        if(trim(pwd).equals(trim(signature))){
        	WeiXinUtil.echostr = echostr;  
            return true;  
        }else{  
            return false;  
        }  
    }  
    private String Encrypt(String strSrc) {  
        MessageDigest md = null;  
        String strDes = null;  
  
        byte[] bt = strSrc.getBytes();  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            md.update(bt);  
            strDes = WeiXinUtil.bytes2Hex(md.digest()); //to HexString  
        } catch (NoSuchAlgorithmException e) {  
        	log.info("Invalid algorithm.");  
            return null;  
        }  
        return strDes;  
    }  
    private boolean isEmpty(String str){  
        return null ==str || "".equals(str) ? true :false;  
    }  
    private String trim(String str){  
        return null !=str  ? str.trim() : str;  
    }  
    
    /** 
     *@author 
     *@return   
     *@exception ServletException, IOException 
     *@param 
     *  
     * <p>XML组装组件</p> 
     */  
     private void message(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{  
		InputStream is = request.getInputStream();  
		// 取HTTP请求流长度  
		int size = request.getContentLength();  
		// 用于缓存每次读取的数据  
		byte[] buffer = new byte[size];  
		// 用于存放结果的数组  
		byte[] xmldataByte = new byte[size];  
		int count = 0;  
		int rbyte = 0;  
		// 循环读取  
		while (count < size) {   
		     // 每次实际读取长度存于rbyte中  
		     rbyte = is.read(buffer);   
		     for(int i=0;i<rbyte;i++) {  
		         xmldataByte[count + i] = buffer[i];  
		     }  
		     count += rbyte;  
		}
		is.close();  
		String requestStr = new String(xmldataByte, "UTF-8");  
		log.info("微信servlet.message返回requestStr="+requestStr);
		try{  
		    manageMessage(requestStr,request,response);  
		}catch(Exception e){  
		    e.printStackTrace();  
		    log.info("微信servlet.message获取requestStr失败。"+e.getMessage());
		}
              
    } 
    /** 
     * @author  
     * @return  
     * @exception ServletException, IOException 
     * @param 
     *  
     * <p>业务转发组件</p> 
     *  
     */  
    private void  manageMessage(String requestStr,HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{  
        try {  
        	String toUserName="";
        	String fromUserName="";
        	String createTime="";
        	String msgtype="";
        	String event="";
        	Element root= null;
        	Document document = null;
        	try {
     			document = DocumentHelper.parseText(requestStr);
     			root = document.getRootElement();
     	        toUserName = root.elementTextTrim("ToUserName");
     	        fromUserName = root.elementTextTrim("FromUserName");
     	        createTime = root.elementTextTrim("CreateTime");
     	        msgtype = root.elementTextTrim("MsgType");
     	        event = root.elementTextTrim("Event");
     		} catch (Exception e) {
     			log.info("解析推送事件xml出错！"+e);
     		}
             
            log.info("消息处理-----开始：");
            log.info("推送消息类型:"+msgtype);
            log.info("公众号："+toUserName+"|用户openId："+fromUserName+"|发送时间："+createTime);
            String responseMessage = "";
            if("event".equals(msgtype)){ //事件消息 
            	log.info("事件消息");
            	responseMessage = HanderWeChatEvent.hander(document, request, response);
            }else if("device_event".equals(msgtype)){ //设备事件
            	log.info("设备事件");
            	responseMessage = HanderWeChatDeviceEvent.hander(document, request, response);
            }else{ //普通消息
            	log.info("普通消息");
            	responseMessage = HanderWeChatMessage.hander(document, request, response);
            }
        	log.info("----响应消息="+responseMessage);
        	response.setCharacterEncoding("UTF-8");
 			response.getWriter().write(responseMessage);
            log.info("消息处理-----结束"); 
       } catch (Exception e) {  
            e.printStackTrace(); 
		    log.info("解析微信的推送事件失败！"+e.getMessage(),e);
       }    
    }  
}
