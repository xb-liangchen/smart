package com.baoju.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.baoju.common.model.Page;
import com.baoju.weixin.entity.SysLog;

public interface ICommonService {

	
	public Session getSession();
	
	public List queryForList(String hql);
	
	public List queryForList(String hql,int showCount);
	
	public Page queryForPage(int curpage,int pagesize,String hql);
	
	public Page queryForPage(int pagesize,String hql,Integer startPageIndex);
	
	//public Page queryForPage(int curpage,int pagesize,String hql,Map<Serializable, Serializable> map);
	
	public boolean isNotExits(String hql);
	
	public boolean executeUpdateSql(String sql);
	
	public Object findObjByHql(String hql);
	
	public Serializable addObj(Object obj);
	
	public Serializable addObj2(Object obj);
	
	public void saveOrUpdateObj(Object obj);
	
	public boolean deleteObj(Class obj,long kid);
	
	public Object getObjByHql(String hql);
	
	public Object getLoadObj(Class obj,Serializable id);
	
	public Object getObj(Class obj,Serializable id);
	
	public boolean executeHql(String hql);
	
	public void updateObj(Object obj);
	
	public Object executeSql(String sql);
	
	public boolean deleteByIdStr(Class obj,String inIdstr);
	
	public void addLog(HttpServletRequest req,SysLog log);
	
	public List queryForListBySql(String sql);
	
	public List queryForListBySql(String sql,Class cla);
	
	public List queryForListBySql2(String sql,Class cla);
}
