package test.weixin;

import java.util.Calendar;
import java.util.Date;

import net.sourceforge.jtds.jdbc.DateTime;

import com.baoju.common.util.Consts;
import com.baoju.common.wechat.WeiXinUtil;

public class Copy_2_of_WXMenuTest {

	public static  String str="{"+
		       " \"button\": ["+
	           " {"+
	             "   \"type\": \"view\", "+
	              "  \"name\": \"我的大狗\", "+
	              "  \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/mydog.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\","+ 
	              "  \"sub_button\": [ ]"+
	           " }, "+
				
	           "{"+
	               " \"name\": \"设备管理\", "+
	               " \"sub_button\": ["+
	                  "  {"+
	                    "    \"type\": \"view\","+ 
	                     "   \"name\": \"首次使用\", "+
	                     "   \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/firstUse.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\", "+
	                    "    \"sub_button\": [ ] "+
	                   " },"+
	                   " {"+
	                       "\"type\": \"scancode_waitmsg\", "+
	                       " \"name\": \"添加设备\", "+
	                       "\"key\": \"k_add_device\", "+

	                     //  " \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/sacnQRCode.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\","+ 
	                       " \"sub_button\": [ ]"+
	                   " },"+
	                   /*" {"+
	                    "    \"type\": \"scancode_push\", "+
	                    "    \"name\": \"添加组件\", "+
	                   "     \"key\": \"k_add_component\", "+
	                    "    \"sub_button\": [ ]"+
	                   " },"+*/
						"{"+
	                     "   \"type\": \"view\", "+
	                     "   \"name\": \"用户管理\", "+
	                     "   \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/userManage.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\", "+ 
	                     "   \"sub_button\": [ ] "+
	                  "  },"+
					"	{"+
	                    "    \"type\": \"view\", "+
	                    "    \"name\": \"设备设置\", "+
	                    "    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/setDevice.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\","+  
	                   "     \"sub_button\": [ ] "+
	                 "   }"+
	             "   ]"+
	            "}, "+
	           " {"+
	               " \"name\": \"更多\", "+
	               " \"sub_button\": ["+
	                   " {"+
	                       " \"type\": \"view\", "+
	                       " \"name\": \"设备切换\", "+
	                       " \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/switchDevice.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\","+ 
	                       " \"sub_button\": [ ]"+
	                   " }, "+
	                   " {"+
	                        " \"type\": \"view\", "+
	                "\"name\": \"使用帮助\", "+
	                "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx21a6580858fdbd1e&redirect_uri=http://chanxj1993.tunnel.mobi/securityGuards/usinghelp.html&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\", "+
	               " \"sub_button\": [ ]"+
	                   " }, "+
	                   
						"{"+
	                   " \"type\": \"view\", "+
	                       " \"name\": \"视频介绍\", "+
	                       " \"url\": \"http://v.qq.com/boke/page/c/0/j/c0159zu1w6j.html\","+ 
	                        "\"sub_button\": [ ]"+
	               "  }, "+
	                "{"+
	                     "\"type\": \"view\", "+
	                       " \"name\": \"大狗商城\", "+
	                       " \"url\": \"http://www.pbweixin.com/api/a7063d3c99/web/\","+ 
	                       " \"sub_button\": [ ]"+
	               " }"+
	                
	               " ]"+
	           " }"+
	        "]"+
	    "}";
	public static void main(String[] args) {
		String appid = Consts.APPID;
		String secret = Consts.SECRET;
 		String token = WeiXinUtil.getToken(appid, secret);
	//	String token = "ruyjdk-k-wvMZpOEAvMtVUx7xrwxHk0oPVLLEL088xmlVHXJKMh7tS4JXKJve6JuCcK_7xhZYkpB6lENVSk6gjXJbSwxLZ6wUaw1GPPd1R0";
//		  Calendar now = Calendar.getInstance();  
//		JSONObject weiXinUserInfo = WeiXinUtil.getWeiXinUserInfo(token, "oAnrxt9PzgomAgtu-vmzFomMEwCc");
//		System.out.println(weiXinUserInfo.toString());
	 	 System.out.print(WeiXinUtil.createMenu(str,token));
 		//  Calendar now = Calendar.getInstance();  
 	//	System.out.print(now.getTimeInMillis());
	}
}
