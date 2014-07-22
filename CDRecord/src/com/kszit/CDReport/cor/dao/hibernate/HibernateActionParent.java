package com.kszit.CDReport.cor.dao.hibernate;


import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.kszit.CDReport.cor.dao.hibernate.exception.MyHibernateException;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.util.HibernateUtil2;

public class HibernateActionParent {
	
	
	protected Session session = null;

    public void openSession(){
    	
    	//session = HibernateUtil.getSessionFactory().getCurrentSession();
        //session.beginTransaction();
    	
		try {
			session = HibernateUtil2.currentSession();
		} catch (MyHibernateException e) {
			e.printStackTrace();
		}
    }
    
    public void closeSession(){
//    	if(session != null && session.isOpen()){
//        	session.getTransaction().commit();
//        	session.close();
//    	}
    	
    }
    
    public void beginTransaction(){
		try {
			HibernateUtil2.beginTransaction();
		} catch (MyHibernateException e) {
			e.printStackTrace();
		}
    }
    
    public void commitTransaction(){
		try {
			HibernateUtil2.commitTransaction();
		} catch (MyHibernateException e) {
			e.printStackTrace();
		}
    }
    
    
    /**
     *      1,3,5==>id=1 or id=3 or id=5
     * @param ids
     * @return
     */
	public String deletesCondition(String id,String ids){
		String[] arrayIds = ids.split("\\,");
		String hql = "";
		for(int i=0;i<arrayIds.length;i++) {
			if(i==0) {
				hql = id+"="+arrayIds[i];
			} else {
				hql =hql + " or "+id+"="+arrayIds[i];
			}
		}   
		return hql;
	}
    
	/**
	 * 通过id删除记录
	 * @param objName 
	 * @param ids   2,3,5,3
	 */
	public void deleteByIds(String objName,String ids){
		this.openSession();
		this.beginTransaction();
		String hql= "delete "+objName+" where "+deletesCondition("id",ids);
		session.createQuery(hql).executeUpdate();
	}
        /**
         * 通过bindid删除记录
         * @param objName
         * @param ids 
         */
        public void deleteByBindids2(String objName,String ids){
		this.openSession();
		this.beginTransaction();
		String hql= "delete "+objName+" where "+deletesCondition("bindid",ids);
		session.createQuery(hql).executeUpdate();
	}
    
	public String saveOrUpdate(Object obj){
		this.openSession();
		this.beginTransaction();
		session.flush();//防止hibernate session中有相同的对象。         
		session.clear();   
		session.saveOrUpdate(obj);
		this.commitTransaction();
		return "";
	}
	
	public String insert(Object obj){
		this.openSession();
		this.beginTransaction();
		Object insertObj = session.save(obj);
		return insertObj.toString();
	}
	
	public void update(Object obj){
		this.openSession();
		this.beginTransaction();
		session.merge(obj);
	}
	
	public void excecleDDL(String sql){
		this.openSession();
		this.beginTransaction();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public Object getByBindIdCommon(String POName,long bindid){
		this.openSession();
		Object o = session.createQuery("from "+POName+" as po where po.bindId="+bindid).uniqueResult();
		return o;
	}
	
}
