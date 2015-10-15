package com.baoju.common.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;




import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baoju.common.dao.ICommonDao;
import com.baoju.common.model.Page;

@Repository
public class CommonDao implements ICommonDao{

	
	@Resource(name="sessionFactory")
	private SessionFactory  sessionFactory;
	
	public SessionFactory getSessionFactory() {
		
		
		return sessionFactory;
	}
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public List queryForList(String hql){
		Query query=getSession().createQuery(hql);
		return query.list();
	}
	public List queryForListBySql(String sql){
		Query query=getSession().createSQLQuery(sql);
		return query.list();
	}
	public List queryForListBySql(String sql,Class cla){
		Query query=getSession().createSQLQuery(sql).addEntity(cla);
		return query.list();
	}
	public List queryForListBySql2(String sql,Class cla){
		Session session=getSession();
		Query query=session.createSQLQuery(sql).addEntity(cla);
		session.clear();
		session.flush();
		return query.list();
	}
	public List queryForList(String hql,int showCount){
		Query query=getSession().createQuery(hql);
		query.setMaxResults(showCount);
		return query.list();
	}
	public Page queryForPage(int startindex,int pagesize,String hql){
		
		Query query=getSession().createQuery(hql);
		
		int totalCounts = query.list().size();
		
		/*if(curpage<1){
			curpage=1;
		}
		int index=0;
		
		if(curpage==1){
			index=pagesize;
		}else{
			index=curpage*pagesize-pagesize;
		}*/
		query.setFirstResult(startindex);
		query.setMaxResults(pagesize);
		
		
		List list=query.list();
		
		Page page=new Page();
		page.setEntityList(list);
		page.setRows(totalCounts);
		page.setPages((totalCounts+pagesize-1)/pagesize);
		
		return page;
	}
	/**
	 * 分页查找
	 * @param firstResult
	 * @param maxResults
	 * @param hql
	 * @param map 从1开始
	 * @return
	 */
	public Page queryForPage(int startindex,int pagesize,String hql,Map<Serializable, Serializable> map){
													
		Query query=getSession().createQuery(hql);
		if(map!=null){
			for (Serializable key:map.keySet()) {
				query.setParameter((String)key, map.get(key));
			}
		}
		int totalCounts = query.list().size();
		query.setFirstResult(startindex);
		query.setMaxResults(pagesize);
		List list=query.list();
		
		Page page=new Page();
		page.setEntityList(list);
		page.setRows(totalCounts);
		
		return page;
	}
	public Object findObjByHql(String hql){
		Query query=getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.uniqueResult();
	}
	
	public Object executeSql(String sql){
		Query query=getSession().createSQLQuery(sql);
		Object obj=query.list().size()>0?query.list().get(0):null;
		return obj;
	}

	public Object getObj(Class obj,Serializable id){
		return getSession().get(obj, id);
	}
	
	public Object getLoadObj(Class obj,Serializable id){
		return getSession().load(obj, id);
	}
	public boolean isNotExits(String hql){
		int size=getSession().createQuery(hql).list().size();
		return size>0?false:true;
	}

	public Serializable addObj(Object obj){
		Session session=getSession();
		Serializable id=session.save(obj);
		session.flush();// flush后hibernate会清理缓存
		return id;
	}
	
	public Serializable addObj2(Object obj){
		Session session=getSession();
		Serializable id=session.save(obj);
		session.flush();// flush后hibernate会清理缓存
		session.clear();
		return id;
	}
	
	public void updateObj(Object obj){
		Session session=getSession();
		session.clear();
		session.update(obj);//session.merge(obj);
		session.flush();// flush后hibernate会清理缓存
		session.evict(obj);//将对象从session中逐出,即session的EntityEntries属性中逐出
	}
	public void saveOrUpdateObj(Object obj){
		Session session=getSession();
		session.merge(obj);
		session.flush();
	}
	
	public boolean executeHql(String hql){
		try {
			getSession().createQuery(hql).executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean executeUpdateSql(String sql){
		try {
			Session session=getSession();
			session.createSQLQuery(sql).executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean deleteObj(Class obj,long id){
		String hql="delete from "+obj.getSimpleName()+" where id=?";
		Query query=getSession().createQuery(hql);
		query.setLong(0, id);
		return query.executeUpdate()>0;
	}
	
	public boolean deleteByIdStr(Class obj,String inIdstr){
		String hql="delete from "+obj.getSimpleName()+" where id in(?) ";
		Query query=getSession().createQuery(hql);
		query.setString(0, inIdstr);
		return query.executeUpdate()>0;
	}	
	
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
