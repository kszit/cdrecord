package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;

public interface DictionaryDao extends DaoParent{

	public String insert(DictionaryPO dictionary);
	
	public String saveOrUpdate(DictionaryPO dictionary);
	
	public void update(DictionaryPO dictionary);
	
	public void delete(long id);
	
	public void deletes(String ids);
	
	public List<DictionaryPO> getListByType(long type);
	
	public List<DictionaryPO> getListOffAll();
	
	public DictionaryPO getDicById(long id);
	
	public DictionaryPO getDicByBindid(String bindid);
	
}
