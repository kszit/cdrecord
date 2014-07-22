package com.kszit.CDReport.cor.dao.hibernate.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kszit.CDReport.cor.dao.hibernate.exception.MyHibernateException;

public class HibernateUtil2 implements Serializable
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//创建线程局部变量　tLocalsess 
    public static final ThreadLocal tLocalsess = new ThreadLocal();
    //创建线程局部变量　tLocaltx
    public static final ThreadLocal tLocaltx = new ThreadLocal();
    //取得session
    public static Session currentSession() throws MyHibernateException{
         //从线程变量tLocalsess中，取得当前session
    	Session session = (Session) tLocalsess.get();
		//判断session是否为空，如果为空，将创建一个session,并付给线程变量tLocalsess
		try{
			if (session == null){
				session = openSession();
				//System.out.println("open session");
				tLocalsess.set(session);
			}
		}catch (HibernateException e){
			throw new MyHibernateException(e);
		}
		//System.out.println("get session");
		return session;
    }
    //关闭当前session
    public static void closeSession(){

         //从线程变量tLocalsess中，取得当前session
		Session session = (Session) tLocalsess.get();
		 //设置线程变量tLocalsess为空
		tLocalsess.set(null);
		try{
		//关闭session
		if (session != null && session.isOpen()){
			session.close();
			//System.out.println("close session in filter");
		}
		}catch (HibernateException e){
			//throw new MyHibernateException(e);
		}
    }
    //事物处理
    public static void beginTransaction() throws MyHibernateException{
    	//从线程变量tLocaltx中取得事物管理对象Transaction
        Transaction tx = (Transaction) tLocaltx.get();
		try{
			//如果为空就从session中创建一个tx
			if (tx == null){
				tx = currentSession().beginTransaction();
				//System.out.println("begin Transaction");
				tLocaltx.set(tx);
			}
		}catch (HibernateException e){
			throw new MyHibernateException(e);
		}
	}
    //提交事物
    public static void commitTransaction() throws MyHibernateException{
      //取得事物
		Transaction tx = (Transaction) tLocaltx.get();
		try{
		            //如果不为空就提交
		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
			tx.commit();
			//System.out.println("close Transaction");
			tLocaltx.set(null);
		}catch (HibernateException e){
			throw new MyHibernateException(e);
		}
	}
    //事物回滚    
    public static void rollbackTransaction(){
         //取得tx事物
		Transaction tx = (Transaction) tLocaltx.get();
		try{
		    //将变量清空
			tLocaltx.set(null);
			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()){
			    //事物回滚
				tx.rollback();
			}
		}catch (HibernateException e){
			//throw new MyHibernateException(e);
		}
	}

   //取得session
	private static Session openSession() throws HibernateException{
		return getSessionFactory().openSession();
	}

   //取得sessionFactory
	private static SessionFactory getSessionFactory() throws HibernateException{
		return SingletonSessionFactory.getSessionFactory();
	}
}

