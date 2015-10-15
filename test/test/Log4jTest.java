package test;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;


public class Log4jTest {

	 private final static double DEFAULT_USERID= Math.random()*100000.0;    
		
		public static void main(String[] args) {
			Logger logger=Logger.getLogger(Log4jTest.class);
			
			MDC.put("userId", DEFAULT_USERID);
			MDC.put("logType", 0);
			MDC.put("operRes", 0);
			MDC.put("ip", "127.0.0.1");
			MDC.put("ipAddr", "本机");
			MDC.put("broswer", "无");
			MDC.put("pixel", "1366*768");
			//      
			logger.info("==测试test logger info1");  
			logger.debug("==test logger debug");  
			logger.error("==test logger error");
			logger.fatal("==test logger fatal");
			logger.warn("==test logger warn");
		}
}
