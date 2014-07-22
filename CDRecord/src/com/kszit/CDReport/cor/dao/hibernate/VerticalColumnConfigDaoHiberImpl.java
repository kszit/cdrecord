package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import com.kszit.CDReport.cor.dao.VerticalColumnConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;

public class VerticalColumnConfigDaoHiberImpl extends HibernateActionParent implements
	VerticalColumnConfigDao {


	public String insert(VerticalColumnConfigPO vConfig) {
		String newid = super.insert(vConfig);
		return newid;
	}

	public void update(VerticalColumnConfigPO vConfig) {
		super.update(vConfig);
	}

	public String saveOrUpdate(VerticalColumnConfigPO vConfig) {
		super.saveOrUpdate(vConfig);
		return "";
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}

	public boolean deletes(String ids) {
		super.deleteByIds("VerticalColumnConfigPO", ids);
		return true;
	}
	
	public boolean deleteByBindids(String reportBindid) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete VerticalColumnConfigPO po where po.reportBindid='"+reportBindid+"'";
		session.createQuery(hql).executeUpdate();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<VerticalColumnConfigPO> getList() {
		super.openSession();
		List<VerticalColumnConfigPO> relist = session.createQuery("from VerticalColumnConfigPO po order by po.orderIndexStr").list();
		return relist;
	}

	@SuppressWarnings("unchecked")
	public List<VerticalColumnConfigPO> getList(String reportBindid) {
		super.openSession();
		List<VerticalColumnConfigPO> relist = session.createQuery("from VerticalColumnConfigPO po where po.reportBindid='"+reportBindid+"' order by po.orderIndexStr").list();
		return relist;
	}

	@SuppressWarnings("unchecked")
	public List<VerticalColumnConfigPO> getListChilds(String reportBindid,int parentid) {
		super.openSession();
		List<VerticalColumnConfigPO> relist = session.createQuery("from VerticalColumnConfigPO po where _parent="+parentid +" and reportBindid='"+reportBindid+"' order by po.orderIndexStr").list();
		return relist;
	}

	public VerticalColumnConfigPO getOneById(long id) {
		super.openSession();
		VerticalColumnConfigPO headerRowC = (VerticalColumnConfigPO) session.get(VerticalColumnConfigPO.class,new Long(id));
		return headerRowC;
	}

	public int childCount(long id) {
		super.openSession();
		int count = ((Number)session.createQuery("select count(*) from VerticalColumnConfigPO po where _parent="+id).uniqueResult()).intValue(); 
		return count;
	}

	public VerticalColumnConfigPO getOneByBindid(long bindid) {
		super.openSession();
		VerticalColumnConfigPO po = (VerticalColumnConfigPO)session.createQuery("from VerticalColumnConfigPO as po where po.bindId="+bindid).uniqueResult();
		return po;
	}

	@Override
	public VerticalColumnConfigPO getOneByReportIdAndBindid(
			String reportBindid, long bindid) {
		super.openSession();
		VerticalColumnConfigPO po = (VerticalColumnConfigPO)session.createQuery("from VerticalColumnConfigPO as po where po.bindId="+bindid +" and po.reportBindid='"+reportBindid+"'").uniqueResult();
		return po;
	}
}
