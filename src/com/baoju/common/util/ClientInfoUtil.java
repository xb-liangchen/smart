package com.baoju.common.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.servlet.http.HttpServletRequest;

public class ClientInfoUtil {

	public static String getScreenSize(){
		Toolkit tool=Toolkit.getDefaultToolkit();
		Dimension dim= tool.getScreenSize();
		return dim.width+"*"+dim.height;
	}
	public static String getUserAgentPlatform(HttpServletRequest request){
		String userAgentInfo=request.getHeader("USER-AGENT").toLowerCase();
		String [] Agents ={"android","ipod", "iphone","symbianos", "windows phone","windows"};
		for(int i=0;i<Agents.length;i++){
			if(userAgentInfo.indexOf(Agents[i])>0){
				return Agents[i];
			}
		}
		return "other";
	}
	public static String getUserAgentBrowser(HttpServletRequest request){
		String userAgentInfo=request.getHeader("USER-AGENT").toLowerCase();
		String [] Agents ={"firefox","chrome","ucbrowser","micromessenger", "msie 6","msie 8","msie 9","msie 10", "msie 11","msie 12"};
		for(int i=0;i<Agents.length;i++){
			if(userAgentInfo.indexOf(Agents[i])>0){
				return Agents[i];
			}
		}
		
		return "other";
	}
	
	
	public static void main(String[] args) {
		System.out.println(getScreenSize());
	}
}
