package com.kszit.CDReport.cor.dao;

import java.util.List;


import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;

public interface HVConfigUIModelDao extends DaoParent{

	public String insert(HVConfigUIModelPO data);
	
	public String update(HVConfigUIModelPO data);
	
	public String insertOrUpdate(HVConfigUIModelPO data);
	
	public boolean deletesByReportIdAndHid(String reportbindid,long hbindid);
	
	public boolean deletesByReportIdAndVid(String reportbindid,long vbindid);
	
	public boolean deletesByReportId(String reportbindid);
	
	public boolean deletes(String ids);
	
	public List<HVConfigUIModelPO> getListOfReportIdAndHid(String reportbindid,long hbindid);
	
	public List<HVConfigUIModelPO> getListOfReportId(String reportbindid);

}
