package com.baoju.weixin.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.baoju.common.util.Consts;
import com.baoju.common.util.wechat.InitiativeSendUtil;
import com.baoju.weixin.service.IUserService;


public class RefreshAccessToken  implements ServletContextListener{

	private final Logger log=Logger.getLogger(RefreshAccessToken.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		ApplicationContext appcontext =WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext());
		Consts.appcontext=appcontext;
		log.info("appcontext:"+appcontext);
		//ICommonService commonService=(ICommonService) ctx.getBean("commonService");
		Consts.WAR_NAME=sc.getServletContext().getContextPath();//ctx========/securityGuards
		//log.info("=======WAR_NAME:"+Consts.WAR_NAME);
		Timer time=new Timer();
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				try{
				Consts.ACCESS_TOKEN=InitiativeSendUtil.getAccessToken();//
				Consts.JSAPI_TICKET=InitiativeSendUtil.getJsapiTicket();//必须先获取ACCESS_TOKEN才能获取JSAPI_TICKET
				log.info("==全局ACCESS_TOKEN:"+Consts.ACCESS_TOKEN);
				log.info("==全局JSAPI_TICKET:"+Consts.JSAPI_TICKET);
			}
			catch(Exception e){
				log.info("timer1异常");
			}
			}
		};
		time.schedule(task, 3000, 1000*60*110);//1小时 50分
		
		
	/*	
		
		Timer timer2=new Timer();
		TimerTask task2=new TimerTask() {
			
			
			@Override
			public void run() {
				//定时查询设备是否在线
				try{
				IUserService userService=(IUserService) Consts.appcontext.getBean("userService");
//				AlarmMessage.deviceNetworkOff(userService);
				}
				catch(Exception e){
					log.info("timer2异常");
				}
				
			}
		};
		
		timer2.schedule(task2, 0, 1000*30);//60秒刷新一次
		
		Timer timer3=new Timer();
		TimerTask task3=new TimerTask() {
			
			
			@Override
			public void run() {
				try{
				//TODO 身份卡离家情况 
				IUserService userService=(IUserService) Consts.appcontext.getBean("userService");
				AlarmMessage.IdCardStatus(userService);
			}
			catch(Exception e){
				log.info("timer3异常");
			}
			}
		};
		
		 timer3.schedule(task3, 10000, 1000*30);//30秒刷新一次
		
		 
		Timer timer4=new Timer();
		TimerTask task4=new TimerTask() {
			
			
			@Override
			public void run() {
				try{
				//定时查询设备报警消息
				IUserService userService=(IUserService) Consts.appcontext.getBean("userService");
			AlarmMessage.deviceAlertInfo(userService);
			}
			catch(Exception e){
				log.info("timer4异常");
			}
			}
		};
		
		timer4.schedule(task4, 10000, 1000*10);//30秒刷新一次
	*/		
	}
	

	
}
