package com.baoju.weixin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baoju.common.util.json.JsonUtil;
import com.baoju.common.util.str.StringUtils;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.service.UserManageService;

@Controller
public class UserManageController {
	private static Logger logger = Logger.getLogger(UserManageController.class);
	@Resource
	private UserManageService userManageService;
	
	@RequestMapping("showUsers")
	public String showUsers(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		//页面会传过一个参数，fogDid即寻找绑定该设备的用户列表
		//String fogDid = request.getParameter("fogDid");
		//String opendi = request.getParameter("openid");
		String fogDid = "97384e4f/c8934652a4a0";
		String openid = "o3vVJwF_QJAhlHnbxXzh1lwwYS6c";//这是当前用户的openid
		List<SysUser> userList = userManageService.queryUsersByFogDid(fogDid);
		SysUser manager = userList.remove(0);//查询出的列表中的第一个元素是第一个关注的用户，也就是管理员
		model.addAttribute("manager",manager);
		model.addAttribute("userList",userList);
		model.addAttribute("fogDid",fogDid);
		model.addAttribute("openid",openid);//当前用户的openid
		return "user/showUsers";
	}
	
	@RequestMapping("deleteBind")
	public void deleteBind(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String msg = "ok";
		
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");//要删除的用户
		
		try {
			if(StringUtils.isEmpty(fogDid,openid)) {
				throw new RuntimeException();
			}
			
			userManageService.deleteBind(openid,fogDid);
			
			logger.info("openid:" + openid);
			logger.info("fogDid:" + fogDid);
		} catch(Exception e) {
			e.printStackTrace();
			msg = "error";
		}
		JSONObject json = new JSONObject();
		json.accumulate("errmsg", msg);
		JsonUtil.writeToHtml(response, json.toString());
	}
}
