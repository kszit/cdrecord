package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import com.kszit.CDReport.cor.dao.HeaderRowConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;

public class HeaderRowConfigDaoHiberImpl extends HibernateActionParent implements
		HeaderRowConfigDao {


	public String insert(HeaderRowConfigPO headerRowConfig) {
		String newid = super.insert(headerRowConfig);
		return newid;
	}

	public void update(HeaderRowConfigPO headerRowConfig) {
		super.update(headerRowConfig);
	}

	public String saveOrUpdate(HeaderRowConfigPO headerRowConfig) {
		super.saveOrUpdate(headerRowConfig);
		return "";
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}

	public boolean deletes(String ids) {
		super.deleteByIds("HeaderRowConfigPO", ids);
		return true;
	}
	
	
	public boolean deleteByBindids(String reportBindid,String bindids) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete HeaderRowConfigPO po where po.reportBindid='"+reportBindid+"' and "+deletesCondition("po.bindId",bindids);
		session.createQuery(hql).executeUpdate();
		return true;
	}
	
	public boolean deleteByBindids(String reportBindid) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete HeaderRowConfigPO po where po.reportBindid='"+reportBindid+"'";
		session.createQuery(hql).executeUpdate();
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<HeaderRowConfigPO> getList() {
		super.openSession();
		List<HeaderRowConfigPO> relist = session.createQuery("from HeaderRowConfigPO po order by po.orderIndexStr").list();
		return relist;
	}

	@SuppressWarnings("unchecked")
	public List<HeaderRowConfigPO> getList(String reportBindid) {
		super.openSession();
		List<HeaderRowConfigPO> relist = session.createQuery("from HeaderRowConfigPO po where po.reportBindid='"+reportBindid+"' order by po.orderIndexStr").list();
		return relist;
	}

	public HeaderRowConfigPO getOneById(long id) {
		super.openSession();
		HeaderRowConfigPO headerRowC = (HeaderRowConfigPO) session.get(HeaderRowConfigPO.class,new Long(id));
		return headerRowC;
	}

	public int childCount(long id) {
		super.openSession();
		int count = ((Number)session.createQuery("select count(*) from HeaderRowConfigPO po where _parent="+id).uniqueResult()).intValue(); 
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<HeaderRowConfigPO> getListChilds(String reportBindid,int parentid) {
		super.openSession();
		List<HeaderRowConfigPO> relist = session.createQuery("from HeaderRowConfigPO po where _parent="+parentid + " and reportBindid='"+reportBindid+"' order by po.orderIndexStr").list();
		return relist;
	}

	public HeaderRowConfigPO getOneByBindid(String reportBindid,long bindid) {
		super.openSession();
		HeaderRowConfigPO po = (HeaderRowConfigPO)session.createQuery("from HeaderRowConfigPO as po where po.bindId="+bindid+" and po.reportBindid='"+reportBindid+"'").uniqueResult();
                return po;
	}

	public List<HeaderRowConfigPO> getDataFromBackRefList(long bindid) {
		super.openSession();
		@SuppressWarnings("unchecked")
		List<HeaderRowConfigPO> relist = session.createQuery("from HeaderRowConfigPO po where dataRuleItems like '%"+bindid+"%'").list();
		return relist;
	}


}
