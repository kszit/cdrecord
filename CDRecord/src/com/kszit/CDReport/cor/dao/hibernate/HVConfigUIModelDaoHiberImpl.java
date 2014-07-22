package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;


import org.hibernate.SQLQuery;

import com.kszit.CDReport.cor.dao.HVConfigUIModelDao;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;

public class HVConfigUIModelDaoHiberImpl extends HibernateActionParent implements
HVConfigUIModelDao {

	public String insert(HVConfigUIModelPO table) {
		return super.insert(table);
	}

	public String update(HVConfigUIModelPO table) {
		super.update(table);
		return table.getId()+"";
	}

	public String insertOrUpdate(HVConfigUIModelPO data) {
		if(data.getId()!=null){
			return this.update(data);
		}else{
			return super.insert(data);
		}
	}

	public boolean deletesByReportIdAndHid(String reportbindid, long hbindid) {
		super.openSession();
		String sql = "delete from hvuimodel where reportBindid='" + reportbindid + "' and hbindid="+hbindid;
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	public boolean deletesByReportIdAndVid(String reportbindid, long vbindid) {
		super.openSession();
		String sql = "delete from hvuimodel where reportBindid='" + reportbindid + "' and vbindid="+vbindid;
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	
	public boolean deletesByReportId(String reportbindid) {
		super.openSession();
		String sql = "delete from hvuimodel where reportBindid='" + reportbindid + "'";
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		return true;
	}
	public boolean deletes(String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<HVConfigUIModelPO> getListOfReportIdAndHid(String reportbindid,long hbindid) {
		super.openSession();
		List<HVConfigUIModelPO> relist = session.createQuery("from HVConfigUIModelPO as po where po.reportBindid='"+reportbindid+"' and po.hbindid="+hbindid).list();
		super.closeSession(); 
		return relist;
	}

	@SuppressWarnings("unchecked")
	public List<HVConfigUIModelPO> getListOfReportId(String reportbindid) {
		super.openSession();
		List<HVConfigUIModelPO> relist = session.createQuery("from HVConfigUIModelPO as po where po.reportBindid='"+reportbindid+"'").list();
		super.closeSession(); 
		return relist;
	}



}
