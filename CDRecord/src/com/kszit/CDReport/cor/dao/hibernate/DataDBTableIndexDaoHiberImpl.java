package com.kszit.CDReport.cor.dao.hibernate;


import java.util.List;

import org.hibernate.Query;

import com.kszit.CDReport.cor.dao.DataDBTableIndexDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DataDBTableIndexPO;

public class DataDBTableIndexDaoHiberImpl extends HibernateActionParent implements DataDBTableIndexDao {

	public String insert(DataDBTableIndexPO table) {
		return super.insert(table);
	}

	public String update(DataDBTableIndexPO table) {
		super.update(table);
		return table.getId()+"";
	}

	public String saveOrUpdate(DataDBTableIndexPO table) {
		if(table.getId()!=null){
			return this.update(table);
		}else{
			return super.insert(table);
		}
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}

	public boolean deleteByReportId(int reportid) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete DataDBTableIndexPO po where po.reportId="+reportid;
		session.createQuery(hql).executeUpdate();
		return true;
	}
	
	public boolean deleteByReportIds(String reportids) {
		this.openSession();
		this.beginTransaction();
		String hql= "delete DataDBTableIndexPO po where "+deletesCondition("po.reportId",reportids);
		session.createQuery(hql).executeUpdate();
		return true;
	}
	
	public boolean deletes(String ids) {
		super.deleteByIds("DataDBTableIndexPO", ids);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<DataDBTableIndexPO> getList() {
		super.openSession();
		List<DataDBTableIndexPO> relist = session.createQuery("from DataDBTableIndexPO as po").list();
		super.closeSession(); 
		return relist;
	}

	public DataDBTableIndexPO getOneById(long id) {
		super.openSession();
		DataDBTableIndexPO po = (DataDBTableIndexPO) session.get(DataDBTableIndexPO.class,new Long(id));
		return po;
	}

	public int getMaxId() {
		super.openSession();
		Query q = session.createQuery("select max(po.id) from DataDBTableIndexPO po");
		Object obj = q.uniqueResult(); //获取唯一的返回值并强转 
		long maxid = 1;
		if(obj != null){
			maxid = (Long)obj;
		}
		return (int)maxid;
	}

	public DataDBTableIndexPO getOneByReportid(long reportId) {
		super.openSession();
		DataDBTableIndexPO po = (DataDBTableIndexPO)session.createQuery("from DataDBTableIndexPO as po where po.reportId="+reportId).uniqueResult();
		return po;
	}
}
