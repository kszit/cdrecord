package com.kszit.CDReport.cor.dao.hibernate.exception;

public class MyHibernateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4653639729259599404L;

	public MyHibernateException() {  
		super();  
	}  
	public MyHibernateException(String msg) {  
		super(msg);  
	}  
	public MyHibernateException(String msg, Throwable cause) {  
		super(msg, cause);  
	}  
	public MyHibernateException(Throwable cause) {  
		super(cause);  
	}
}
