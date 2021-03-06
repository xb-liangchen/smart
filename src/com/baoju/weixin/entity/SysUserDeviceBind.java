package com.baoju.weixin.entity;

// Generated 2015-9-22 9:29:15 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysUserDeviceBind generated by hbm2java
 */
@Entity
@Table(name = "sys_user_device_bind", catalog = "smartlight_dev")
public class SysUserDeviceBind implements java.io.Serializable {

	private Long id;
	private Long userId;
	private String openid;
	private Long did;
	private Date createTime;
	private int isCurrent;
	private String fogDid;

	public SysUserDeviceBind() {
	}

	public SysUserDeviceBind(Date createTime, int isCurrent) {
		this.createTime = createTime;
		this.isCurrent = isCurrent;
	}

	public SysUserDeviceBind(Long userId, String openid, Long did,
			Date createTime, int isCurrent) {
		this.userId = userId;
		this.openid = openid;
		this.did = did;
		this.createTime = createTime;
		this.isCurrent = isCurrent;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "openid", length = 200)
	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "did")
	public Long getDid() {
		return this.did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_current", nullable = false)
	public int getIsCurrent() {
		return this.isCurrent;
	}

	public void setIsCurrent(int isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	@Column(name = "fog_did")
	public String getFogDid() {
		return fogDid;
	}

	public void setFogDid(String fogDid) {
		this.fogDid = fogDid;
	}
	

}
