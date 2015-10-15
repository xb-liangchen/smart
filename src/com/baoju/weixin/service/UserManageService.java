package com.baoju.weixin.service;

import java.util.List;

import com.baoju.common.service.ICommonService;
import com.baoju.weixin.entity.SysUser;

public interface UserManageService extends ICommonService{

	List<SysUser> queryUsersByFogDid(String fogDid);

	void deleteBind(String openid, String fogDid);

}
