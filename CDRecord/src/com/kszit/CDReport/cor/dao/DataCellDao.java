package com.kszit.CDReport.cor.dao;

import java.util.List;


import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;

public interface DataCellDao extends DaoParent{

	public void createTable(String tablename);
	
	public void dropTable(String tablename);
	
	public String insert(DataCellPO data);
	
	public String inserts(List<DataCellPO> datapos);
	
	public String update(DataCellPO table);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public boolean deletesByDataBindid(String dataBindid);
	
	public List<DataCellPO> getListOfReportId(int dataid);
	
	public List<DataCellPO> getListOfVid(int dataid,int vid);
	
	public List<DataCellPO> getListOfHid(int dataid,int hid);
	
	public DataCellPO getOneById(long id);
	
	public DataCellPO getOneHidAndVid(long bindid,long hid,long vid);
	
}
