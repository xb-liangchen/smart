package com.baoju.weixin.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baoju.common.service.impl.CommonService;
import com.baoju.common.sql.SqlFormat;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysLight;
import com.baoju.weixin.entity.SysScene;
import com.baoju.weixin.entity.SysSceneDetail;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.service.MultiSceneService;

@SuppressWarnings("unchecked")
@Service("multiSceneService")
public class MultiSceneServiceImpl extends CommonService implements MultiSceneService {

	@Override
	public List<SysScene> queryMultiScenes(String openid, String fogDid) {// 只查当前did下的多灯场景的。
		String hql1 = "from SysDevice s where s.fogDid=''{0}''";
		hql1 = SqlFormat.geteFormatString(hql1, fogDid);
		SysDevice device = (SysDevice) getObjByHql(hql1);
		
		String hql2 = "from SysScene s where s.openid=''{0}'' and s.did=''{1}'' and s.sceneType = 1";
		hql2 = SqlFormat.geteFormatString(hql2, openid, device.getId());
		return queryForList(hql2);
	}

	@Override
	public void deleteSceneByIds(Long[] ids) {
		StringBuffer hql1 = new StringBuffer(
				"DELETE FROM SysSceneDetail s WHERE s.sceneId in(");
		StringBuffer hql2 = new StringBuffer(
				"DELETE FROM SysScene s WHERE s.id in(");
		for (int i = 0; i < ids.length; i++) {
			hql1.append(ids[i]);
			hql2.append(ids[i]);
			if (i < ids.length - 1) {
				hql1.append(",");
				hql2.append(",");
			} else {
				hql1.append(")");
				hql2.append(")");
			}
		}
		executeHql(hql1.toString());// 删除sceneDetail
		executeHql(hql2.toString());// 删除scene
	}

	@Override
	public SysUser getUserInfo(String openid) {
		String hql = "from SysUser where openid=''{0}''";
		hql = SqlFormat.geteFormatString(hql, openid);
		SysUser user = (SysUser) getObjByHql(hql);
		return user;
	}

	@Override
	public SysDevice getSysDeviceByFogDid(String fogDid) {
		String hql = "from SysDevice where fogDid=''{0}''";
		hql = SqlFormat.geteFormatString(hql, fogDid);
		SysDevice device = (SysDevice)getObjByHql(hql);
		return device;
	}

	@Override
	public Serializable addScene(SysScene scene) {
		return addObj(scene);
	}

	@Override
	public SysScene getSceneById(Long sceneId) {
		return (SysScene) getObj(SysScene.class, sceneId);
	}
	
	@Override
	public List<SysSceneDetail> querySceneDetailListByScene(SysScene scene) {
		String hql = "FROM SysSceneDetail s WHERE s.sceneId=''{0}''";
		hql = SqlFormat.geteFormatString(hql, scene.getId());
		return queryForList(hql);
	}
	
	@Override
	public List<SysLight> queryLightListByScene(SysScene scene) {
		String hql = "from SysLight sl where sl.id in(select ssd.lightId from SysSceneDetail ssd where ssd.sceneId="
				+ scene.getId() + ")";
		return queryForList(hql);
	}
	
	@Override
	// 只查当前场景没有添加进来的灯
	public List<SysLight> filterLightListByScene(String fogDid, String sceneId) {
//		String hql = "from SysLight sl where sl.id in (select sdlb.lightId from SysDeviceLightBind sdlb where sdlb.DId="
//				+ did
//				+ " and sdlb.lightId not in(select ssd.lightId from SysSceneDetail ssd where ssd.sceneId="
//				+ sceneId + ")) order by sl.isOnline desc";
		String hql = "FROM SysLight sl where sl.fogDid =''{0}'' AND sl.id not in (SELECT ssd.lightId FROM SysSceneDetail ssd where ssd.sceneId = ''{1}'')";
		hql = SqlFormat.geteFormatString(hql, fogDid,sceneId);
		return queryForList(hql);
	}

	@Override
	public SysLight getLightById(String lightId) {
		return (SysLight) getObj(SysLight.class, lightId);
	}

	@Override
	public SysSceneDetail getSceneDetailById(Long detailId) {
		return (SysSceneDetail) getObj(SysSceneDetail.class, detailId);
	}

	@Override
	public void updateSceneDetail(SysSceneDetail detail) {
		updateObj(detail);
	}

	@Override
	public Serializable saveSceneDetail(SysSceneDetail detail) {
		return addObj(detail);
	}
}
