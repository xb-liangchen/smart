package com.baoju.weixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baoju.common.service.impl.CommonService;
import com.baoju.common.sql.SqlFormat;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.entity.SysUserDeviceBind;
import com.baoju.weixin.service.UserManageService;

@Service("userManageService")
public class UserManageServiceImpl extends CommonService implements UserManageService {

	@Override
	public List<SysUser> queryUsersByFogDid(String fogDid) {
		String hql = "FROM SysUser u WHERE u.openid in (SELECT sudb.openid FROM SysUserDeviceBind sudb WHERE sudb.fogDid = ''{0}'') ORDER BY u.createTime ASC";
		hql = SqlFormat.geteFormatString(hql, fogDid);
		return queryForList(hql);
	}

	@Override
	public void deleteBind(String openid, String fogDid) {
		String hql = "FROM SysUserDeviceBind sudb WHERE sudb.openid = ''{0}'' AND sudb.fogDid = ''{1}''";
		hql = SqlFormat.geteFormatString(hql, openid,fogDid);
		SysUserDeviceBind sudb = (SysUserDeviceBind) this.getObjByHql(hql);
		this.deleteObj(SysUserDeviceBind.class, sudb.getId());
	}
}
