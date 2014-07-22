package com.kszit.CDReport.cor.dao;

import java.util.List;


import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;

public interface HVConfigDataFromDao extends DaoParent{

	public String insert(HVConfigDataFromPO data);
	
	public String update(HVConfigDataFromPO data);
	
	public String insertOrUpdate(HVConfigDataFromPO data);
	
	public boolean deletesByReportIdAndHid(String reportbindid,long hbindid);
	
	public boolean deletesByReportId(String reportbindid);
	
	public boolean deletesByReportIdAndVid(String reportbindid,long vbindid);
	
	public boolean deletes(String ids);
	
	public List<HVConfigDataFromPO> getListOfReportIdAndHid(String reportbindid,long hbindid);
	
	public HVConfigDataFromPO getCellDataFromHVBindid(String reportbindid,long hbindid,long vbindid);
	
	public List<HVConfigDataFromPO> getListOfReportId(String reportbindid);

}
