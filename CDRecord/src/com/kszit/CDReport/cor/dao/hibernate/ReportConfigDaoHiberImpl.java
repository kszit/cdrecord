package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;


import com.kszit.CDReport.cor.dao.ReportConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;

public class ReportConfigDaoHiberImpl extends HibernateActionParent implements
ReportConfigDao {

	public String saveOrUpdate(ReportConfigPO reportConfig) {
		if(reportConfig.getId()!=null){
			super.update(reportConfig);
			return reportConfig.getId()+"";
		}else{
			return super.insert(reportConfig);
		}
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}

	public boolean deletes(String ids) {
		super.deleteByIds("ReportConfigPO", ids);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<ReportConfigPO> getReportConfigs() {
		super.openSession();
		List<ReportConfigPO> relist = session.createQuery("from ReportConfigPO as po").list();
		return relist;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportConfigPO> getReportConfigsByYear(String year) {
		super.openSession();
		List<ReportConfigPO> relist = session.createQuery("from ReportConfigPO as po where rtyear='"+year+"'").list();
		return relist;
	}

	public ReportConfigPO getReportConfigById(long id) {
		super.openSession();
		ReportConfigPO po = (ReportConfigPO) session.get(ReportConfigPO.class,new Long(id));
		return po;
	}

	public String insert(ReportConfigPO reportConfig) {
		return super.insert(reportConfig);
	}

	public ReportConfigPO getReportConfigByBindid(String bindid,String version) {
		super.openSession();
		ReportConfigPO po = (ReportConfigPO) session.createQuery("from ReportConfigPO as po where po.bindId="+bindid +" and po.rtversion2="+version).uniqueResult();
		return po;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportConfigPO> getReportConfigByBindid(String bindid) {
		super.openSession();
		List<ReportConfigPO> relist = session.createQuery("from ReportConfigPO as po where po.bindId="+bindid).list();
		return relist;
	}

	public int getNewVersion(long bindid) {
		super.openSession();
		Integer maxVersion = (Integer)session.createQuery("select max(po.rtversion2) from ReportConfigPO po where po.bindId="+bindid ).uniqueResult();

		return maxVersion.intValue()+1;
	}

	@SuppressWarnings("unchecked")
	public List<ReportConfigPO> getReportConfigByLike(ReportConfigPO config) {
		super.openSession();
		String sql = "select * from reportconfig "
				+ " where 1=1 " + getSqlWhere(config);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(ReportConfigPO.class);
		List<ReportConfigPO> poList = query.list();
		return poList;
	}
	
	private String getSqlWhere(ReportConfigPO config){
		StringBuffer sb = new StringBuffer();
		if(config.getReportState()!=null && config.getReportState()!=0){
			sb.append(" and reportState="+config.getReportState());
		}
		if(config.getRttype()!=null){
			sb.append(" and rttype="+config.getRttype());
		}
		if(config.getRtyear()!=null){
			sb.append(" and rtyear="+config.getRtyear());
		}
		if(config.getReportName()!=null){
			sb.append(" and reportName like '%"+config.getReportName()+"%'");
		}
		if(config.getAppearDepartment()!=null){
			sb.append(" and appearDepartment like '%"+config.getAppearDepartment()+"%'");
		}

		return sb.toString();
	}

}
