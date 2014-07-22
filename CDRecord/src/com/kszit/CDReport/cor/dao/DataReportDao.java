package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.model.ReportAppareModel;

public interface DataReportDao extends DaoParent{
	
	public String saveOrUpdate(DataReportPO dataReport);
	
	public String insert(DataReportPO dataReport);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public List<DataReportPO> getDataReports(DataReportPO po);
	
	public List<DataReportPO> getDataReports();
	
	public DataReportPO getDataReportById(long id);
	
	public DataReportPO getDataReportByBindid(long bindid);

	public List<Object> getAppearReport(ReportAppareModel reportAppareModel);
	
}
