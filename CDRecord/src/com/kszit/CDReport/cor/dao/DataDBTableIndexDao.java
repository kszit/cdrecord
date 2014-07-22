package com.kszit.CDReport.cor.dao;

import java.util.List;



import com.kszit.CDReport.cor.dao.hibernate.po.DataDBTableIndexPO;

public interface DataDBTableIndexDao extends DaoParent{

	public String insert(DataDBTableIndexPO table);
	
	public String update(DataDBTableIndexPO table);
	
	public String saveOrUpdate(DataDBTableIndexPO table);
	
	public boolean delete(long id);
	
	public boolean deleteByReportId(int reportid);
	
	public boolean deleteByReportIds(String reportids);
	
	public boolean deletes(String ids);
	
	public List<DataDBTableIndexPO> getList();
	
	public DataDBTableIndexPO getOneById(long id);
	
	public DataDBTableIndexPO getOneByReportid(long reportId);
	
	public int getMaxId();
	
}
