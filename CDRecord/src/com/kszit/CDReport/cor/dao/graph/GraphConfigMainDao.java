package com.kszit.CDReport.cor.dao.graph;

import java.util.List;

import com.kszit.CDReport.cor.dao.DaoParent;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;

public interface GraphConfigMainDao extends DaoParent{
	
	public String saveOrUpdate(GraphConfigMainPO graphCMain);
	
	public void delete(long bindId);
	
	public List<GraphConfigMainPO> getListOffAll();
	
	public GraphConfigMainPO getByBindId(long bindId);
	
	
}
