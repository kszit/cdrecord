package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;

import com.kszit.CDReport.cor.dao.DataReportDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.ReportAppareModel;
import com.kszit.CDReport.util.StaticVaribles;

public class DataReportDaoHiberImpl extends HibernateActionParent implements DataReportDao {

	public String saveOrUpdate(DataReportPO dataReport) {
		if(dataReport.getId()!=null){
			super.update(dataReport);
			return dataReport.getId()+"";
		}else{
			return super.insert(dataReport);
		}
	}

	public String insert(DataReportPO dataReport) {
		return super.insert(dataReport);
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}


	public boolean deletes(String ids) {
		super.deleteByIds("DataReportPO", ids);
		return true;
	}
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<DataReportPO> getDataReports(DataReportPO po) {
		super.openSession();
		String sql = "select * from datareport "
				+ " where 1=1 " + getSqlWhere(po);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(DataReportPO.class);
		List<DataReportPO> poList = query.list();
		return poList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DataReportPO> getDataReports() {
		super.openSession();
		String sql = "select * from datareport "
				+ " where 1=1 order by mademandate desc";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(DataReportPO.class);
		List<DataReportPO> poList = query.list();
		return poList;
	}

	public DataReportPO getDataReportById(long id) {
		super.openSession();
		DataReportPO po = (DataReportPO) session.get(DataReportPO.class,new Long(id));
		return po;
	}

	public DataReportPO getDataReportByBindid(long bindid) {
		super.openSession();
		Object o = session.createQuery("from DataReportPO as po where po.bindId="+bindid).uniqueResult();
		DataReportPO po = (DataReportPO)o ;
		return po;
	}
	
	public String getSqlWhere(DataReportPO po){
		if(po==null){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		if(po.getVerify()!=-1){
			if(po.getVerify()==StaticVaribles.DataReportFlowState_weaveAndverify){//编制和审核中的报表
				sb.append(" and (verify="+StaticVaribles.DataReportFlowState_weave+" or verify="+StaticVaribles.DataReportFlowState_verify +")");
			}else if(po.getVerify()==StaticVaribles.DataReportFlowState_verifyAndpublish){//审核中和已经上报的报表
				sb.append(" and (verify="+StaticVaribles.DataReportFlowState_publish+" or verify="+StaticVaribles.DataReportFlowState_verify +")");
			}else{
				sb.append(" and verify="+po.getVerify());
			}
		}
		if(po.getRepotBindid()!=null){
			sb.append(" and repotBindid='"+po.getRepotBindid()+"'");
		}
		if(po.getMadeManName()!=null){
			sb.append(" and madeManName='"+po.getMadeManName()+"'");
		}
		if(po.getCreateDepBindid()!=null){
			sb.append(" and createDepBindid='"+po.getCreateDepBindid()+"'");
		}
		if(po.getPeriods()!=null){
			sb.append(" and periods='"+po.getPeriods()+"'");
		}
		if(po.getYear()!=null){
			sb.append(" and year='"+po.getYear()+"'");
		}
		return sb.toString();
	}

	public List<Object> getAppearReport(ReportAppareModel reportAppareModel) {
		super.openSession();
		//@SuppressWarnings("unchecked")
		//List<Object> relist = session.createQuery("from ReportConfigPO as po left join DataReportPO as datapo on po.reportLink=datapo.reportBindid").list();
		//String sql = "select config.*,report.* from reportconfig config left join datareport report on config.reportlink=report.repotbindid order by report.mademandate desc";
		String sql = "select config.*,report.* from reportconfig config, datareport report where config.reportlink=report.repotbindid order by report.mademandate desc";
		if(reportAppareModel!=null && reportAppareModel.getReportConfigModel()!=null && reportAppareModel.getReportConfigModel().getType()!=null){
			//sql = "select config.*,report.* from reportconfig config, datareport report where config.reportlink=report.repotbindid and config.type='"+reportAppareModel.getReportConfigModel().getType()+"' order by report.mademandate desc";
			sql = "select report.* from reportconfig config, datareport report where config.reportlink=report.repotbindid and config.type='"+reportAppareModel.getReportConfigModel().getType()+"' order by report.mademandate desc";
		}
		SQLQuery query = session.createSQLQuery(sql);
		//query.addEntity(ReportConfigPO.class);
		query.addEntity(DataReportPO.class);
		List<Object> relist = query.list();
		
		return relist;
	}

}
