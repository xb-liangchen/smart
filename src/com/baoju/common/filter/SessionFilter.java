package com.baoju.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/*import test.Log4jTest;*/

import com.baoju.common.model.SessionUser;
import com.baoju.common.util.Consts;

public class SessionFilter implements Filter{

	private final Logger log=Logger.getLogger(SessionFilter.class);
	
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		HttpSession session=request.getSession();
		
		//System.out.println("客户端："+request.getHeader("USER-AGENT").toLowerCase());
		//String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		
		String uri=request.getRequestURL().toString();
		if(request.getQueryString()==null){
			
		}else{
			uri=uri+"?"+request.getQueryString();
		}
		
		//log.info("正在访问链接:"+uri);
		log.info("+++正在访问链接:"+uri);
	    chain.doFilter(req, resp);
		
	}
	public void init(FilterConfig arg0) throws ServletException {
		log.setLevel(Level.INFO);
	}

	
}
