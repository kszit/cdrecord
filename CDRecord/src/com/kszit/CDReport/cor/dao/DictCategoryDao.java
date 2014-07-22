package com.kszit.CDReport.cor.dao;

import java.util.List;


import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;

public interface DictCategoryDao extends DaoParent{
	
	public String insert(DictCategoryPO dicCategory);
	
	public void update(DictCategoryPO dicCategory);
	
	public void saveOrUpdate(DictCategoryPO dicCategory);
	
	public void delete(long id);
	
	public void deletes(String ids);
	
	public List<DictCategoryPO> getListByParent(long parentid);
	
	public DictCategoryPO getDicById(long id);
	
	public DictCategoryPO getDicByBindid(String bindid);
	
}
