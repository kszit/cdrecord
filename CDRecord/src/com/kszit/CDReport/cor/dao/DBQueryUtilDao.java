package com.kszit.CDReport.cor.dao;

import java.util.List;

public interface DBQueryUtilDao extends DaoParent{

	public List<Object[]> exceleSql(String sql);
	
}
