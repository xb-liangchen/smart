package com.baoju.common.util.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

public class JsonUtil {
	public static Logger logger=Logger.getLogger(JsonUtil.class);
	/**
	 * List转换成json数组
	 * @param obj
	 * @return
	 */
	public static JSONArray fromObjToJsonArray(Object obj){
		JSONArray jsonarray=new JSONArray();
		jsonarray=JSONArray.fromObject(obj);
		return jsonarray;
	}
	
	
	public static void writeToHtml(HttpServletResponse resp,String content){
		PrintWriter out=null;
		try {
			out= resp.getWriter();
			resp.setContentType("text/html;charset=UTF-8");
			out.write(content);
		} catch (IOException e) {
			//e.printStackTrace();
			logger.error(e);
		}finally{
			out.flush();
			out.close();
		}
	}
}
