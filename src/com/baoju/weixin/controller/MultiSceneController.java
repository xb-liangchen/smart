package com.baoju.weixin.controller;

import java.io.IOException;
import java.io.Serializable;
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
import com.baoju.common.util.properties.SysConfigUtil;
import com.baoju.common.util.str.StringUtils;
import com.baoju.common.util.wechat.InitiativeSendUtil;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysLight;
import com.baoju.weixin.entity.SysScene;
import com.baoju.weixin.entity.SysSceneDetail;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.service.MultiSceneService;

/**
 * 该控制器只控制多灯场景
 * 
 * @author xinbing
 *
 */
@Controller
public class MultiSceneController {
	private static Logger logger = Logger.getLogger(MultiSceneController.class);
	@Resource
	private MultiSceneService multiSceneService;

	/**
	 * 本方法是查询多灯情景
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("showMultiScenes")
	// 待定
	public String showMultiScenes(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		String openid = getOpenid(request, response);// 获取页面传过来的openid
		/*
		 * 如果openid为空，那么说明不是从微信访问的，直接返回；在getOpenid(request,response)方法中
		 * 是如果openid为空，那么直接response.sendRedirect(); 所以这里什么都不用做，直接return null即可。
		 */
		if (StringUtils.isEmpty(openid)) {
			return null;
		}
		
		// TODO 2015-09-25也只应当查询当前wifi设备下的情景,目前假定页面传过来了一个参数did,现在默认查询did为227的设备
		// String didStr = request.getParameter("did");
		//Long did = Long.parseLong("227");
		
		//TODO 2015-10-09 页面中存在一个fog_did的参数，即当前用户所选定的设备。
		//String fogDid = request.getParameter("fogDid");
		String fogDid = "97384e4f/c8934652a4a0";//页面默认传该值过来
		List<SysScene> sceneList = multiSceneService.queryMultiScenes(openid, fogDid);
		
		System.out.println("showScenes微信号：" + openid);
		
		model.put("sceneList", sceneList);
		//model.put("did", did);// 将did传给页面，因为添加的情景的时候要用。2015-10-09改为传fogDid
		model.put("fogDid",fogDid);//将fogDid传给页面
		model.put("openid", openid);
		return "scene/showMultiScenes";
	}

	/**
	 * 删除多灯情景 当前业务逻辑，根据页面传过来的sceneId将该scene及该sceneDetail删除；
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteMultiScene")
	// 暂时不用修改
	public void deleteMultiScene(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		String msg = "ok";
		try {
			// TODO
			// 留坑待填,jquery.ajax传数组的解码问题:sceneIds%5B%5D=486&sceneIds%5B%5D=488
			// %5B%5D就是代表[]
			String[] sceneIds = request.getParameterValues("sceneIds[]");
			String openid = request.getParameter("openid");
			// TODO openid就先不验证了。
			Long[] ids = StringUtils.changeToLongArray(sceneIds);// 将数组转换为long类型的数组
			multiSceneService.deleteSceneByIds(ids);// 该方法就会连同sceneDetail一起被删除
		} catch (Exception e) {
			e.printStackTrace();
			msg = "error";
		}
		JSONObject json = new JSONObject();
		json.accumulate("errmsg", msg);
		JsonUtil.writeToHtml(response, json.toString());
	}

	/**
	 * 添加多灯场景 暂时的业务逻辑： 在情景列表里点击添加场景后，弹出对话框，输入相应信息，保存
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addMultiScene")
	// 暂时不用修改
	public void addMultiScene(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		/**
		 * 根据当前9-25的数据库设计， 页面需要传过来的数据有： 情景名(输入框),设备id,openid,而openid微信端可以直接给我们，
		 * 所以页面只需要传送参数情景name,设备id。
		 */
		// TODO
		// 微信的重定向链接真坑，一次只能传一个参数。导致又要重写。and页面都用这种模型，ajax进行访问，然后链接都是页面的js来进行的？
		String msg = "ok";
		Serializable sceneId = null;
		String openid = request.getParameter("openid");//
		String name = request.getParameter("name");// 情景名称
		//String didStr = request.getParameter("did"); // 设备ID 2015-10-09
		String fogDid = request.getParameter("fogDid");
		
		System.out.println(name + "-----" + fogDid);
		try {
//			Long did = Long.parseLong(didStr);//2015-10-09
			
			// 如果页面输入的name，openid,fogDid为空，那么抛出异常，外层的catch会负责捕捉它。
			if (StringUtils.isEmpty(name, openid,fogDid)) {
				throw new RuntimeException();
			}
			// 访问数据库，根据openid将用户查找到，如果没有查找到，说明openid非法，抛出异常
			SysUser user = multiSceneService.getUserInfo(openid);
			SysDevice device = multiSceneService.getSysDeviceByFogDid(fogDid);
			if (user == null || device == null) {
				throw new RuntimeException();
			}
			// 如果上述代码段都没有抛出异常，那么说明数据正常，后台数据校验完毕。
			SysScene scene = new SysScene(name,device.getId(), 1, openid, user.getId());
			
			sceneId = multiSceneService.addScene(scene);// 将scene加入数据库。底层调用Hibernate的save()方法，返回刚插入对象的id
	
			logger.info("openid:" + openid);
			logger.info("sceneName:" + name);
		} catch (Exception e) {
			msg = "error";
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.accumulate("errmsg", msg);
		json.accumulate("sceneId", sceneId);
		JsonUtil.writeToHtml(response, json.toString());
	}

	@RequestMapping("showMultiSceneDetail")// 暂时不用修改
	public String showMultiSceneDetail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		try {
			Long sceneId = Long.parseLong(request.getParameter("sceneId"));
			String fogDid = request.getParameter("fogDid");
			String openid = request.getParameter("openid");
System.out.println(sceneId+"--"+fogDid+"--"+openid);
			SysScene scene = multiSceneService.getSceneById(sceneId);
			List<SysSceneDetail> sceneDetailList = multiSceneService.querySceneDetailListByScene(scene);
			List<SysLight> lightList = multiSceneService.queryLightListByScene(scene);
			model.put("fogDid", fogDid);
			model.put("openid", openid);
			model.put("scene", scene);
			model.put("sceneDetailList", sceneDetailList);
			model.put("lightList", lightList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "scene/showMultiSceneDetail";
	}

	/**
	 * 业务逻辑，只显示当前场景下没有的灯列表。
	 * 
	 * @param req
	 * @param resp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/lightListInMultiScene.htm")
	public String lightListInMultiScene(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		// TODO 要不要加严格的数据验证？暂时先不加
		/*
		 * 页面需要传送过来的参数有：did,sceneId(过滤灯列表),openid
		 */
		//Long did = Long.parseLong(request.getParameter("did"));2015-10-09
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		String sceneId = request.getParameter("sceneId");

		logger.info("fogDid:" + fogDid);
		logger.info("openid:" + openid);
		// 因为只显示当前设备下、当前场景没有添加进来的灯，所以需要传一个did和sceneId
		SysDevice device = multiSceneService.getSysDeviceByFogDid(fogDid);
		List<SysLight> lightList = multiSceneService.filterLightListByScene(device.getFogDid(),
				sceneId);
		
		model.addAttribute("lightList", lightList);
		model.addAttribute("sceneId", sceneId);
		model.addAttribute("openid", openid);
		model.addAttribute("fogDid", fogDid);
		return "scene/lightListInMultiScene";
	}

	@RequestMapping("preAddMultiSceneDetail")
	public String preAddMultiSceneDetail(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		// TODO 暂时不做数据验证
		// 需要传递的参数有lightId,sceneId,fogDid
		String lightId = request.getParameter("lightId");
		Long sceneId = Long.parseLong(request.getParameter("sceneId"));
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		SysLight light = multiSceneService.getLightById(lightId);
		
		model.addAttribute("openid",openid);
		model.addAttribute("fogDid",fogDid);
		model.addAttribute("light", light);
		model.addAttribute("sceneId", sceneId);
		// 根据灯的类别的不同，从而跳转到不同的页面,1为RGB,2为调光调色
		String url = null;
		 if(light.getLightType() == 2) {
			 url = "scene/multiLightControle";
		 }
		 if(light.getLightType() == 12) {
		 	 url = "scene/multiLightControleRGB";
		 }
		 return url;
	}
	
	@RequestMapping("addMultiSceneDetail")
	public String addMultiSceneDetail(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		//页面传送过来的参数应该会有：lightId,sceneId,temp,bright,onoff
		//TODO 异常处理暂时不加
		String lightId = request.getParameter("lightId");
		Long sceneId = Long.parseLong(request.getParameter("sceneId"));
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		
		Long temp = Long.parseLong(request.getParameter("temp"));//色温
		Long bright = Long.parseLong(request.getParameter("bright"));
		int onoff = Integer.parseInt(request.getParameter("onoff"));
		Long rgb = null;//rgb值
		int swith_light = 0;//灯源
		int rgb_bright = 0;//rgb亮度
		int rgb_shade =0;//渐变
		
		SysUser user = multiSceneService.getUserInfo(openid);//获取user
		SysLight light = multiSceneService.getLightById(lightId);//获取灯
		if(light.getLightType() == 12) {//rgb灯
			rgb = Long.parseLong(request.getParameter("rgb"));
			swith_light = Integer.parseInt(request.getParameter("switch_light"));
			rgb_bright = Integer.parseInt(request.getParameter("rgb_bright"));
			rgb_shade = Integer.parseInt(request.getParameter("rgb_shade"));
		}
		
		SysSceneDetail detail = new SysSceneDetail();
		detail.setLightId(lightId);//灯
		detail.setSceneId(sceneId);//场景
		detail.setCreateBy(user.getId());//set创建人
		
		detail.setTemperature(temp);//色温
		detail.setBrightness(bright);//白光亮度
		detail.setOnOff(onoff);//set开关
		
		if(light.getLightType() == 2) {//说明是普通灯
			//无特殊处理
		} else if(light.getLightType() == 12) {//说明是rgb灯
			detail.setRgb(rgb);//rgb
			detail.setSwithLight(swith_light);//光源
			detail.setColorBrightness(rgb_bright);//rgb亮度
			detail.setRgbshade(rgb_shade == 255?255:254);//渐变
		}
		multiSceneService.saveSceneDetail(detail);//存入数据库
		
		return "redirect:showMultiSceneDetail.htm?openid="+openid+"&fogDid="+fogDid+"&sceneId="+sceneId;
	}
	
	@RequestMapping("preEditMultiSceneDetail")
	public String preEditMultiSceneDetail(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		// TODO 暂时先不做数据校验
		// 需要传递的参数需sceneDetailId,fogDid
		Long detailId = Long.parseLong(request.getParameter("detailId"));
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		
		SysSceneDetail detail = (SysSceneDetail) multiSceneService.getSceneDetailById(detailId);
		SysLight light = multiSceneService.getLightById(detail.getLightId());

System.out.println("pre:"+fogDid+"-------------------"+openid);
		model.addAttribute("openid",openid);
		model.addAttribute("fogDid",fogDid);
		model.addAttribute("detail",detail);
		model.addAttribute("light",light);

		// 根据灯的类别的不同，从而跳转到不同的页面,1为RGB,2为调光调色
		String url = null;
		 if(light.getLightType() == 2) {
			 url = "scene/multiLightControle";
		 }
		 if(light.getLightType() == 12) {
		 	url = "scene/multiLightControleRGB";
		 }
		 return url;
	}

	@RequestMapping("editMultiSceneDetail")
	public String editMultiSceneDetail(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		//页面传过来的参数应该会有:sceneDetailId,fogDid,temp,bright,onoff
		//TODO 异常处理暂时不加
		Long detailId = Long.parseLong(request.getParameter("detailId"));
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		
		Long temp = Long.parseLong(request.getParameter("temp"));
		Long bright = Long.parseLong(request.getParameter("bright"));
		int onoff = Integer.parseInt(request.getParameter("onoff"));
		
		SysSceneDetail detail = multiSceneService.getSceneDetailById(detailId);
		
		detail.setTemperature(temp);
		detail.setBrightness(bright);
		detail.setOnOff(onoff);
		multiSceneService.updateSceneDetail(detail);
		
		return "redirect:showMultiSceneDetail.htm?openid="+openid+"&fogDid="+fogDid+"&sceneId="+detail.getSceneId();
	}
	
	@RequestMapping("editMultiSceneDetailRGB")
	public String editMultiSceneDetailRGB(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		//页面传过来的参数应该会有:sceneDetailId,fogDid,temp,bright,onoff
		//TODO 异常处理暂时不加
		Long detailId = Long.parseLong(request.getParameter("detailId"));
		String fogDid = request.getParameter("fogDid");
		String openid = request.getParameter("openid");
		
		Long temp = Long.parseLong(request.getParameter("temp"));
		Long bright = Long.parseLong(request.getParameter("bright"));
		int onoff = Integer.parseInt(request.getParameter("onoff"));
		
		Long rgb = Long.parseLong(request.getParameter("rgb"));
		int swith_light = Integer.parseInt(request.getParameter("switch_light"));
		int rgb_bright = Integer.parseInt(request.getParameter("rgb_bright"));
		int rgb_shade = Integer.parseInt(request.getParameter("rgb_shade"));
		
		SysSceneDetail detail = multiSceneService.getSceneDetailById(detailId);
		
		detail.setTemperature(temp);
		detail.setBrightness(bright);
		detail.setOnOff(onoff);
		detail.setRgb(rgb);
		detail.setSwithLight(swith_light);
		detail.setColorBrightness(rgb_bright);
		detail.setRgbshade(rgb_shade);
		
		multiSceneService.updateSceneDetail(detail);
		
		return "redirect:showMultiSceneDetail.htm?openid="+openid+"&fogDid="+fogDid+"&sceneId="+detail.getSceneId();
	}
	
	@RequestMapping("doMultiScene")
	public void doMultiScene(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		//页面只需要传送一个参数,sceneId即可。
		//TODO 暂时
		Long sceneId = Long.parseLong(request.getParameter("sceneId"));
		SysScene scene = new SysScene();
		scene.setId(sceneId);
		List<SysSceneDetail> sceneDetailList = multiSceneService.querySceneDetailListByScene(scene);
	}
	
	@RequestMapping("deleteSceneDetail")// 此版本不做删除。
	public void deleteSceneDetail(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception {
		// 传输sceneDetailId即可
		Long detailId = Long.parseLong(request.getParameter("detailId"));
		//multiSceneService.deleteSceneDetailById(detailId);//TODO 如果为健壮考虑，不应当如此草率的删除
	}

	@RequestMapping("turnOnInSceneDetail")//暂时无需修改
	public void turnOnInSceneDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String msg = "ok";
		//页面穿过来的数据只需要场景明细id 而在场景明细中，灯的状态只有两种，0为关闭，1位启用。
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			SysSceneDetail detail = multiSceneService.getSceneDetailById(id);
			int onOff = detail.getOnOff() == 0 ? 1 : 0;// 如果灯之前是启用状态1,那么现在变成关闭状态0;
			detail.setOnOff(onOff);
			//TODO 是否应该要把灯关闭？
			multiSceneService.updateSceneDetail(detail);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "error";
		}
		JSONObject jso = new JSONObject();
		jso.accumulate("errmsg", msg);
		JsonUtil.writeToHtml(response, jso.toString());
	}

	// 判断是否从微信端访问 -2015-09-25
	//现在加上另一段逻辑,如果从code中没有获取到，那么就从request.getParameter中获取。因为页面还没作整合2015-10-15
	private String getOpenid(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		JSONObject json = InitiativeSendUtil.getWapAccessTKAndOpenid(code);
		String openid = null;
		if (json.has("openid")) {//如果有openid，那么就从openid中取
			openid = json.getString("openid");
		} else {
			openid = request.getParameter("openid");
		}
		if(StringUtils.isEmpty(openid)) {
			try {
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="
						+ SysConfigUtil.getString("appid")
						+ "&redirect_uri=http://"
						+ SysConfigUtil.getString("ip")
						+ "/smartlight/firstUse.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return openid;
	}
}
