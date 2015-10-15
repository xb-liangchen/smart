package com.baoju.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.baoju.common.model.Page;

public interface ICommonDao {

	public SessionFactory getSessionFactory();
	
	public Session getSession() ;
	
	public Object executeSql(String sql);
	
	public Serializable addObj(Object obj);
	
	public Serializable addObj2(Object obj);
	
	public void saveOrUpdateObj(Object obj);
	
	public Object getObj(Class obj,Serializable id);
	
	public Object findObjByHql(String hql);
	
	public Object getLoadObj(Class obj,Serializable id);

	public boolean isNotExits(String hql);
	
	public void updateObj(Object obj);
	
	public boolean executeHql(String hql);
	
	public boolean deleteObj(Class obj,long id);
	
	public boolean deleteByIdStr(Class obj,String inIdstr);
	
	public List queryForList(String hql);
	
	public List queryForListBySql(String sql);
	
	public List queryForListBySql(String sql,Class cla);
	
	public List queryForListBySql2(String sql,Class cla);
	
	public List queryForList(String hql,int showCount);
	
	public Page queryForPage(int startindex,int pagesize,String hql);
	
	public boolean executeUpdateSql(String sql);
	
	public Page queryForPage(int startindex,int pagesize,String hql,Map<Serializable, Serializable> map);
}
