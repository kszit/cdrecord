package com.kszit.CDReport.cor.dao.graph;

import java.util.List;

import com.kszit.CDReport.cor.dao.DaoParent;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;

public interface GraphConfigSubDao extends DaoParent{
	
	public String saveOrUpdate(GraphConfigSubPO graphCMain);
	
	public void delete(long bindId);
	
	public void deleteByGraphBindid(long graphBindid);
	
	public List<GraphConfigSubPO> getListOfAll();
	
	public List<GraphConfigSubPO> getListOfMainBindid(long mainBindid);
	
	public GraphConfigSubPO getByBindId(long bindId);
	
	
}
