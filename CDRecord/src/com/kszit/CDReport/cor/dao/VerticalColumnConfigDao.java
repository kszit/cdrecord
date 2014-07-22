package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;

public interface VerticalColumnConfigDao extends DaoParent{

	public String insert(VerticalColumnConfigPO vConfig);
	
	public void update(VerticalColumnConfigPO vConfig);
	
	public String saveOrUpdate(VerticalColumnConfigPO vConfig);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public boolean deleteByBindids(String reportBindid);
	
	public List<VerticalColumnConfigPO> getList();
	
	public List<VerticalColumnConfigPO> getList(String reportBindid);
	
	public List<VerticalColumnConfigPO> getListChilds(String reportBindid,int parentid);
	
	public VerticalColumnConfigPO getOneById(long id);
	
	public VerticalColumnConfigPO getOneByBindid(long bindid);
	
	public VerticalColumnConfigPO getOneByReportIdAndBindid(String reportBindid,long bindid);
	
	public int childCount(long id);
	
}
