package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;

public interface ReportConfigDao extends DaoParent{
	
	public String saveOrUpdate(ReportConfigPO reportConfig);
	
	public String insert(ReportConfigPO reportConfig);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public List<ReportConfigPO> getReportConfigs();
	
	public List<ReportConfigPO> getReportConfigsByYear(String year);
	
	public ReportConfigPO getReportConfigById(long id);
	
	public int getNewVersion(long bindid);
	
	public ReportConfigPO getReportConfigByBindid(String bindid,String version);
	
	public List<ReportConfigPO> getReportConfigByBindid(String bindid);
	
	public List<ReportConfigPO> getReportConfigByLike(ReportConfigPO config);
	
}
