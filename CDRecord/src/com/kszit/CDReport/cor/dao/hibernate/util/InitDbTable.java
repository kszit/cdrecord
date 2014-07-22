package com.kszit.CDReport.cor.dao.hibernate.util;

import org.hibernate.Session;

public class InitDbTable {

	public static void main(String[] a){
		System.out.print("init db");
		Session session = SingletonSessionFactory.getSessionFactory().getCurrentSession();
		
	}
}
