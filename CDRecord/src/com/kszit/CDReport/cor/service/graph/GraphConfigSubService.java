package com.kszit.CDReport.cor.service.graph;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.graph.GraphConfigSubDao;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigSubDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;
import com.kszit.CDReport.cor.model.graph.GraphConfigMainModel;
import com.kszit.CDReport.cor.model.graph.GraphConfigSubModel;

public class GraphConfigSubService {
	GraphConfigSubDao graphConfigSubDao = new GraphConfigSubDaoHiberImpl();
	
	public String insertOrUpdate(GraphConfigSubModel model){
		GraphConfigSubPO po = new GraphConfigSubPO(model);
		return insertOrUpdate(po);
		
	}
	
	
	public String insertOrUpdate(GraphConfigSubPO po){
		po.initBindId();
		return graphConfigSubDao.saveOrUpdate(po);
	}
	
	public String delete(long bindId){
		graphConfigSubDao.delete(bindId);
		return null;
	}
	
	public GraphConfigSubModel getGrapConfigByBindIdOfModel(long bindId){
		GraphConfigSubPO po = getGrapConfigByBindId(bindId);
		GraphConfigSubModel model = new GraphConfigSubModel(po);
		return model;
	}
	
	public GraphConfigSubPO getGrapConfigByBindId(long bindId){
		return graphConfigSubDao.getByBindId(bindId);
	}
	
	public List<GraphConfigSubModel> getAllGraphConfigOfModel(){
		List<GraphConfigSubModel> re = new ArrayList<GraphConfigSubModel>();
		List<GraphConfigSubPO> poList = getAllGraphConfig();
		for(GraphConfigSubPO po:poList){
			GraphConfigSubModel model = new GraphConfigSubModel(po);
			re.add(model);
		}
		return re;
	}
	
	public List<GraphConfigSubModel> getGraphConfigSubOfMainBindidModel(long mainBindid){
		List<GraphConfigSubModel> re = new ArrayList<GraphConfigSubModel>();
		List<GraphConfigSubPO> poList = getGraphConfigSubOfMainBindid(mainBindid);
		for(GraphConfigSubPO po:poList){
			GraphConfigSubModel model = new GraphConfigSubModel(po);
			re.add(model);
		}
		return re;
	}
	
	public List<GraphConfigSubPO> getAllGraphConfig(){
		return graphConfigSubDao.getListOfAll();
	}
	
	public List<GraphConfigSubPO> getGraphConfigSubOfMainBindid(long mainBindid){
		return graphConfigSubDao.getListOfMainBindid(mainBindid);
	}

}
