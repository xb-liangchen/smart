package com.baoju.weixin.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baoju.common.service.ICommonService;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysLight;
import com.baoju.weixin.entity.SysScene;
import com.baoju.weixin.entity.SysSceneDetail;
import com.baoju.weixin.entity.SysUser;

public interface MultiSceneService extends ICommonService {

	List<SysScene> queryMultiScenes(String openid, String fogDid);

	void deleteSceneByIds(Long[] ids);

	SysUser getUserInfo(String openid);

	SysDevice getSysDeviceByFogDid(String fogDid);

	Serializable addScene(SysScene scene);

	SysScene getSceneById(Long sceneId);

	List<SysSceneDetail> querySceneDetailListByScene(SysScene scene);

	List<SysLight> queryLightListByScene(SysScene scene);

	List<SysLight> filterLightListByScene(String fogDid, String sceneId);

	SysLight getLightById(String lightId);

	SysSceneDetail getSceneDetailById(Long detailId);

	void updateSceneDetail(SysSceneDetail detail);

	Serializable saveSceneDetail(SysSceneDetail detail);
	
}
