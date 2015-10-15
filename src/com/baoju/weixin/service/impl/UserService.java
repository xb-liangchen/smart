package com.baoju.weixin.service.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.baoju.common.service.impl.CommonService;
import com.baoju.common.sql.SqlFormat;
import com.baoju.common.util.fogcloud.FogcloudUtils;
import com.baoju.common.util.str.StringUtils;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysDeviceUser;
import com.baoju.weixin.entity.SysLight;
import com.baoju.weixin.entity.SysScene;
import com.baoju.weixin.entity.SysSceneDetail;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.entity.SysUserDeviceBind;
import com.baoju.weixin.service.IUserService;

@Service("userService")
public class UserService extends CommonService implements IUserService {

	private final Logger log = Logger.getLogger(this.getClass().getName());

	@Override
	public SysUser getUserInfo(String openid) {
		String hql = "from SysUser where openid=''{0}''";
		hql = SqlFormat.geteFormatString(hql, openid);
		SysUser user = (SysUser) getObjByHql(hql);
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysDevice> getDevListByOpenid(String openid) {
		String hql="from SysDevice sd where sd.fogDid in (select sudb.fogDid from SysUserDeviceBind sudb where sudb.openid='"+openid+"') order by sd.isOnline desc";
	     return queryForList(hql);
	}

	@Override
	public void deleteUserAndBind(long uid) {
		SysUser user = (SysUser) getObj(SysUser.class, uid);
		if (user != null && StringUtils.isNotEmpty(user.getOpenid())) {
			executeUpdateSql("delete from sys_device_user where openid='"
					+ user.getOpenid() + "'");
		}
		executeUpdateSql("delete from sys_user_device_bind where user_id="
				+ uid);
		deleteObj(SysUser.class, uid);
	}

	@Override
	public void deleteWifiBind(Long did, String openid) {
		executeUpdateSql("delete from sys_user_device_bind where openid='"
				+ openid + "' and did=" + did);
	}

	@Override
	public void updateSysDeviceName(Long did, String dname) {
		executeUpdateSql("update Sys_Device set name='" + dname
				+ "'  where id=" + did);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysLight> getLightListByDid(Long did) {
		String hql = "from SysLight sl where sl.id in (select sdlb.lightId from SysDeviceLightBind sdlb where sdlb.DId="
				+ did + ") order by sl.isOnline desc";
		return queryForList(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysScene> getSysSceneForOneLight(String openid, String lightId) {
		String hql = "from SysScene ss where ss.sceneType = 0 and ss.openid = '"+openid+"' and ss.lightId ='"+ lightId+"'";
		return queryForList(hql);
	}

	@Override
	public SysLight getLightById(String lightId) {
		String hql = "from SysLight sl where sl.id = '"+lightId+"'";
		SysLight sl = (SysLight)getObjByHql(hql);
		return sl;
	}

	@Override
	public void deleteSceneForOneLight(Long id) {
		deleteObj(SysScene.class, id);
		executeUpdateSql("delete from sys_scene_detail where scene_id=" + id);
	}


	@Override
	public SysLight queryLightBySceneDetailId(Long detailId) {
		String hql = "from SysLight sl where sl.id = (select ssd.lightId from SysSceneDetail ssd where ssd.id="
				+ detailId + ")";
		SysLight light = (SysLight) getObjByHql(hql);
		return light;
	}

	@Override
	public String getFogUid(String openid) {
		String hql = "from SysDeviceUser where openid=''{0}''";
		hql=SqlFormat.geteFormatString(hql, openid);
		SysDeviceUser user=(SysDeviceUser)getObjByHql(hql);
		if(user==null){
			SysDeviceUser sdu = new SysDeviceUser();
			JSONObject o = FogcloudUtils.createUser(openid);
			String token = o.getString("token");
			JSONObject jo =  FogcloudUtils.login(openid,token);
			String uid = jo.getString("user_id"); 
			sdu.setOpenid(openid);
			sdu.setToken(token);
			sdu.setUid(uid);
			addObj(sdu);
			return uid;
		}else{
			return user.getUid();
		}
	}

	@Override
	public boolean bindDevice(String user_id, String mac, String openid) {
		boolean flag = FogcloudUtils.bindDevice(user_id, mac);
		if(flag){
			SysUserDeviceBind sudb = new SysUserDeviceBind();
			sudb.setCreateTime(new Date());
			sudb.setOpenid(openid);
	//		sudb.setUserId(user_id);
//			sudb.setDid(did);
			addObj(sudb);
			return true;
		}
		return false;
	}

	@Override
	public List<SysLight> getLightListByFogDid(String fogDid) {
		String hql="from SysLight sl where sl.id in (select sdlb.lightId from SysDeviceLightBind sdlb where sdlb.fogDid='"+fogDid+"') order by sl.isOnline desc";
		return queryForList(hql);
	}
}
