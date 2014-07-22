package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;

public interface HeaderRowConfigDao extends DaoParent{

	public String insert(HeaderRowConfigPO headerRowConfig);
	
	public void update(HeaderRowConfigPO headerRowConfig);
	
	public String saveOrUpdate(HeaderRowConfigPO headerRowConfig);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public boolean deleteByBindids(String reportBindid,String ids);
	
	public boolean deleteByBindids(String reportBindid);
	
	public List<HeaderRowConfigPO> getList();
	
	public List<HeaderRowConfigPO> getDataFromBackRefList(long bindid);
	
	public List<HeaderRowConfigPO> getList(String reportBindid);
	
	public List<HeaderRowConfigPO> getListChilds(String reportBindid,int parentid);
	
	public HeaderRowConfigPO getOneById(long id);
	
	public HeaderRowConfigPO getOneByBindid(String reportBindid,long bindid);
	
	public int childCount(long id);
	
}
