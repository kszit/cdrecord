package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;

import com.kszit.CDReport.cor.dao.HVConfigDataFromDao;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;

public class HVConfigDataFromDaoHiberImpl extends HibernateActionParent implements
HVConfigDataFromDao {

	public String insert(HVConfigDataFromPO table) {
		return super.insert(table);
	}

	public String update(HVConfigDataFromPO table) {
		super.update(table);
		return table.getId()+"";
	}

	public String insertOrUpdate(HVConfigDataFromPO data) {
		if(data.getId()!=null){
			//this.update(data);
			String sql = "update hvdatafrom set vbindid='"+data.getVbindid()+"',hbindid="+data.getHbindid()+",DataFromType='"+data.getDataFromType()+"' where id="+data.getId();
			super.excecleDDL(sql);
			return data.getId()+"";
		}else{
			return super.insert(data);
		}
	}

	public boolean deletesByReportIdAndHid(String reportbindid, long hbindid) {
		super.openSession();
		String sql = "delete from hvdatafrom where reportBindid='" + reportbindid + "' and hbindid="+hbindid;
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	
	public boolean deletesByReportId(String reportbindid) {
		super.openSession();
		String sql = "delete from hvdatafrom where reportBindid='" + reportbindid + "'";
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	
	public boolean deletesByReportIdAndVid(String reportbindid, long vbindid) {
		super.openSession();
		String sql = "delete from hvdatafrom where reportBindid='" + reportbindid + "' and vbindid="+vbindid;
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	public boolean deletes(String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<HVConfigDataFromPO> getListOfReportIdAndHid(String reportbindid,long hbindid) {
		super.openSession();
		List<HVConfigDataFromPO> relist = session.createQuery("from HVConfigDataFromPO as po where po.reportBindid='"+reportbindid+"' and po.hbindid="+hbindid).list();
		super.closeSession(); 
		return relist;
	}
	
	@SuppressWarnings("unchecked")
	public List<HVConfigDataFromPO> getListOfReportId(String reportbindid) {
		super.openSession();
		List<HVConfigDataFromPO> relist = session.createQuery("from HVConfigDataFromPO as po where po.reportBindid='"+reportbindid+"'").list();
		super.closeSession(); 
		return relist;
	}

	@Override
	public HVConfigDataFromPO getCellDataFromHVBindid(String reportbindid,
			long hbindid, long vbindid) {
		super.openSession();
		HVConfigDataFromPO po = (HVConfigDataFromPO) session.createQuery("from HVConfigDataFromPO as po where po.reportBindid='"+reportbindid +"' and po.hbindid="+hbindid+" and po.vbindid="+vbindid).uniqueResult();
		return po;
	}



}
