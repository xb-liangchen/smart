package com.baoju.common.model;

import java.util.List;

import org.apache.log4j.MDC;

import com.baoju.weixin.entity.SysUser;

public class SessionUser {

	private long userId=0l;
	
	private String userName;
	
	private String realName;
	
	private int logType=0;
	
	private int operRes=0;  //0-成功  1-失败
	
	
	private SysUser sysUser;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getLogType() {
		return logType;
	}

	
	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	
	public void setLogType(int logType) {
		this.logType = logType;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getOperRes() {
		return operRes;
	}

	public void setOperRes(int operRes) {
		this.operRes = operRes;
	}

	
	
	
}
