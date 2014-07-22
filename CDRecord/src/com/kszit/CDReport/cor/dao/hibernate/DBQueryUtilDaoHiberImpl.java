package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;


import com.kszit.CDReport.cor.dao.DBQueryUtilDao;

public class DBQueryUtilDaoHiberImpl extends HibernateActionParent implements
DBQueryUtilDao {
	@SuppressWarnings("unchecked")
	public List<Object[]> exceleSql(String sql) {
		super.openSession();
		SQLQuery query = session.createSQLQuery(sql);
		List<Object[]> l = query.list();
		return l;
	}
}
