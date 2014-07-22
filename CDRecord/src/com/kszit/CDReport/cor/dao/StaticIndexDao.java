package com.kszit.CDReport.cor.dao;

import com.kszit.CDReport.cor.dao.hibernate.po.StaticIndexPO;

public interface StaticIndexDao extends DaoParent{

	public String update(StaticIndexPO staticIndex);

	public StaticIndexPO getOneByName(String name);
}
