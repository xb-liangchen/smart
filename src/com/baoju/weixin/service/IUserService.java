package com.baoju.weixin.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.baoju.common.service.ICommonService;
import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysDeviceUser;
import com.baoju.weixin.entity.SysLight;
import com.baoju.weixin.entity.SysLog;
import com.baoju.weixin.entity.SysScene;
import com.baoju.weixin.entity.SysSceneDetail;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.entity.SysUserDeviceBind;

public interface IUserService extends ICommonService {
	public SysUser getUserInfo(String openid);

	public List<SysDevice> getDevListByOpenid(String openid);

	public void deleteUserAndBind(long uid);

	public void deleteWifiBind(Long did, String openid);

	public void updateSysDeviceName(Long did, String dname);

	public List<SysLight> getLightListByDid(Long did);

	public List<SysScene> getSysSceneForOneLight(String openid, String lightId);

	public SysLight getLightById(String lightId);

	public void deleteSceneForOneLight(Long id);

	public SysLight queryLightBySceneDetailId(Long detailId);

	public String getFogUid(String openid);

	public boolean bindDevice(String user_id, String mac, String openid);

	public List<SysLight> getLightListByFogDid(String fogDid);

}
