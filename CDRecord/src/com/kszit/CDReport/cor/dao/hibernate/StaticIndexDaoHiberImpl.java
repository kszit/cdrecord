package com.kszit.CDReport.cor.dao.hibernate;


import com.kszit.CDReport.cor.dao.StaticIndexDao;
import com.kszit.CDReport.cor.dao.hibernate.po.StaticIndexPO;

public class StaticIndexDaoHiberImpl extends HibernateActionParent implements StaticIndexDao {

	public String update(StaticIndexPO staticIndex) {
		super.update(staticIndex);
		return "";
	}

	public StaticIndexPO getOneByName(String name) {
            super.openSession();
		StaticIndexPO po = (StaticIndexPO) session.createQuery("from StaticIndexPO as po where po.name=:sname").setString("sname",name).uniqueResult();
		return po;
	}

}
