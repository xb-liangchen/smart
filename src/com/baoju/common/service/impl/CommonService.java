package com.baoju.common.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoju.common.dao.ICommonDao;
import com.baoju.common.model.Page;
import com.baoju.common.model.SessionUser;
import com.baoju.common.service.ICommonService;
import com.baoju.common.util.Consts;
import com.baoju.common.util.IpUtil;
import com.baoju.common.util.str.StringUtils;
import com.baoju.weixin.entity.SysLog;

@Service("commonService")
public class CommonService implements ICommonService{

	@Resource
	protected ICommonDao commonDao;
	
	
	public Session getSession(){
		return commonDao.getSession();
	}

	public List queryForList(String hql){
		return commonDao.queryForList(hql);
	}
	
	public List queryForList(String hql,int showCount){
		return commonDao.queryForList(hql,showCount);
	}
	
	public Page queryForPage(int curpage,int pagesize,String hql){
		int startindex=0;
		
		startindex=(curpage-1)*pagesize;
		
		return commonDao.queryForPage(startindex, pagesize, hql);
		
	}
	public Object findObjByHql(String hql){
		return commonDao.findObjByHql(hql);
	}
	/**提供startPageIndex开始行序号查询*/
	public Page queryForPage(int pagesize,String hql,Integer startPageIndex){
		
		return commonDao.queryForPage(startPageIndex, pagesize, hql);
		
	}
	
	public boolean isNotExits(String hql){
		return commonDao.isNotExits(hql);
	}
	
	public Object getLoadObj(Class obj,Serializable id){
		return commonDao.getLoadObj(obj, id);
	}
	
	public Object getObj(Class obj,Serializable id){
		return commonDao.getObj(obj, id);
	}
	public Object getObjByHql(String hql){
		return commonDao.findObjByHql(hql);
	}
	
	
	public boolean executeHql(String hql){
		return commonDao.executeHql(hql);
	}
	public boolean deleteObj(Class obj, long kid) {
		
		return commonDao.deleteObj(obj, kid);
	}
	public Serializable addObj(Object obj) {
		 return commonDao.addObj(obj);
	}
	
	public Serializable addObj2(Object obj) {
		 return commonDao.addObj2(obj);
	}
	
	public void updateObj(Object obj) {
		 commonDao.updateObj(obj);
	}
	public void saveOrUpdateObj(Object obj) {
		 commonDao.saveOrUpdateObj(obj);
	}
	
	public boolean deleteByIdStr(Class obj, String inIdstr) {
		return commonDao.deleteByIdStr(obj, inIdstr);
	}
	
	public List queryForListBySql(String sql){
		return commonDao.queryForListBySql(sql);
	}
	
	public Object executeSql(String sql){
		return commonDao.executeSql(sql);
	}
	
	public boolean executeUpdateSql(String sql){
		return commonDao.executeUpdateSql(sql);
	}
	
	public List queryForListBySql(String sql,Class cla){
		return commonDao.queryForListBySql(sql, cla);
	}
	
	public List queryForListBySql2(String sql,Class cla){
		return commonDao.queryForListBySql2(sql, cla);
	}
	
	public void addLog(HttpServletRequest req,SysLog log){
		
		//SessionUser user=(SessionUser) req.getSession().getAttribute(Consts.SESSION_USER);
		log.setCreateTime(new Date());
		log.setIpAddr(req!=null?IpUtil.getIpAddr(req):null);
		commonDao.addObj(log);
	}
	
}
