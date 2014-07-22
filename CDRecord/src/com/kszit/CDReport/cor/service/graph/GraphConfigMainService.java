package com.kszit.CDReport.cor.service.graph;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.graph.GraphConfigMainDao;
import com.kszit.CDReport.cor.dao.graph.GraphConfigSubDao;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigMainDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigSubDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.model.graph.GraphConfigMainModel;
import com.kszit.CDReport.cor.model.graph.GraphConfigSubModel;

public class GraphConfigMainService {
	GraphConfigMainDao graphConfigMainDao = new GraphConfigMainDaoHiberImpl();
	
	public String insertOrUpdate(GraphConfigMainModel model){
		 /*
		GraphConfigMainPO po = null;
		if(model.getBindId()!=0){
			po = getGrapConfigByBindId(model.getBindId());
			po.setName(model.getName());
			po.setGraphType(model.getGraphType());
			po.setStartDay(model.getStartDay());
			po.setEndDay(model.getEndDay());
		}else{
			po = new GraphConfigMainPO(model);
			
		}
		*/
		GraphConfigMainPO po = new GraphConfigMainPO(model);
		return insertOrUpdate(po);
		
	}
	
	public String insertOrUpdateConfigSub(GraphConfigSubModel model){
		return null;
	}
	
	public String insertOrUpdate(GraphConfigMainPO po){
		po.initBindId();
		graphConfigMainDao.saveOrUpdate(po);
		return po.getBindId()+"";
	}
	
	public String delete(long bindId){
		//删除子配置信息
		GraphConfigSubDao graphConfigSubDao = new GraphConfigSubDaoHiberImpl();
		graphConfigSubDao.deleteByGraphBindid(bindId);
		
		graphConfigMainDao.delete(bindId);
		return null;
	}
	
	public GraphConfigMainModel getGrapConfigByBindIdOfModel(long bindId){
		GraphConfigMainPO po = getGrapConfigByBindId(bindId);
		GraphConfigMainModel model = new GraphConfigMainModel(po);
		return model;
	}
	
	public GraphConfigMainPO getGrapConfigByBindId(long bindId){
		return graphConfigMainDao.getByBindId(bindId);
	}
	
	public List<GraphConfigMainModel> getAllGraphConfigOfModel(){
		List<GraphConfigMainModel> re = new ArrayList<GraphConfigMainModel>();
		List<GraphConfigMainPO> poList = getAllGraphConfig();
		for(GraphConfigMainPO po:poList){
			GraphConfigMainModel model = new GraphConfigMainModel(po);
			re.add(model);
		}
		return re;
	}
	
	public List<GraphConfigMainPO> getAllGraphConfig(){
		return graphConfigMainDao.getListOffAll();
	}
}
