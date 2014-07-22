package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;

import com.kszit.CDReport.cor.dao.HVConfigCellDValueDao;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigCellDValuePO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;

public class HVConfigCellDValueDaoHiberImpl extends HibernateActionParent implements
HVConfigCellDValueDao {

	public String insert(HVConfigCellDValuePO table) {
		return super.insert(table);
	}

	public String update(HVConfigCellDValuePO table) {
		super.update(table);
		return table.getId()+"";
	}

	public String insertOrUpdate(HVConfigCellDValuePO data) {
		if(data.getId()!=null){
			//this.update(data);
			String sql = "update hvCellDValue set vbindid='"+data.getVbindid()+"',hbindid="+data.getHbindid()+",defaultValue='"+data.getDefaultValue()+"' where id="+data.getId();
			super.excecleDDL(sql);
			return data.getId()+"";
		}else{
			return super.insert(data);
		}
	}

	@Override
	public HVConfigCellDValuePO getCellValue(String reportBindid, long hbindid,
			long vbindid) {
		super.openSession();
		HVConfigCellDValuePO po = (HVConfigCellDValuePO) session.createQuery("from HVConfigCellDValuePO as po where po.reportBindid='"+reportBindid +"' and po.hbindid="+hbindid+" and po.vbindid="+vbindid).uniqueResult();
		return po;
	}

	@Override
	public List<HVConfigCellDValuePO> getListOfReportId(String reportbindid) {
		super.openSession();
		List<HVConfigCellDValuePO> relist = session.createQuery("from HVConfigCellDValuePO as po where po.reportBindid='"+reportbindid+"'").list();
		super.closeSession(); 
		return relist;
	}

	


}
