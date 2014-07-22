package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DBQueryUtilDao;
import com.kszit.CDReport.cor.dao.HVConfigUIModelDao;
import com.kszit.CDReport.cor.dao.hibernate.DBQueryUtilDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.HVConfigUIModelDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.HVConfigUIModelModel;
import com.kszit.CDReport.util.StaticVaribles;
/**
 * 数据来源（数据关系）服务类
 * @author Administrator
 *
 */
public class HVConfigUIModelService {
	
	HVConfigUIModelDao hvUIModelDao = null;
	List<VerticalColumnConfigPO> vConfigList = null;
	public HVConfigUIModelService(){
		hvUIModelDao = new HVConfigUIModelDaoHiberImpl();
	}
	/**
	 * 
	 * @param reportbindid
	 * @param hbindid
	 * @return
	 */
	public List<HVConfigUIModelModel> getUIModelList(String reportbindid,long hbindid){
		
		initDataFroms(reportbindid,hbindid);
		
		List<HVConfigUIModelPO> poList = hvUIModelDao.getListOfReportIdAndHid(reportbindid, hbindid);
		List<HVConfigUIModelModel> moList = new ArrayList<HVConfigUIModelModel>();
		for(HVConfigUIModelPO po:poList){
			HVConfigUIModelModel model = new HVConfigUIModelModel(po);
			for(VerticalColumnConfigPO vpo:vConfigList){
				if(model.getVbindid() == vpo.getBindId()){
					model.setvName(vpo.getContent());
					model.set_id(vpo.getBindId().intValue());
					model.set_parent(vpo.get_parent());
					model.set_is_leaf(vpo.is_is_leaf());
					model.setOrderIndexStr(vpo.getOrderIndexStr());
				}
			}
			moList.add(model);
		}
		
		ComparatoUIModel comparator=new ComparatoUIModel();
		Collections.sort(moList,comparator);
		
		return moList;
	}
	
	public void deletes(String reportBindid){
		hvUIModelDao.deletesByReportId(reportBindid);
	}
	
	/**
	 * 复制
	 * @param oldConfigPo
	 * @param newConfigPo
	 */
	public void copyUIModel(ReportConfigPO oldConfigPo,ReportConfigPO newConfigPo){
		String oldReportBindid = oldConfigPo.getReportBindidLink();
		String newReportBindid = newConfigPo.getReportBindidLink();
		List<HVConfigUIModelPO> UIModelList = getUIModelList(oldReportBindid);
		for(HVConfigUIModelPO uiModel:UIModelList){
			HVConfigUIModelPO newUIModel = new HVConfigUIModelPO(uiModel);
			newUIModel.setReportBindid(newReportBindid);
			save(newUIModel);
		}
	}
	
	/**
	 * 
	 * @param reportbindid
	 * @param hbindid
	 * @return
	 */
	public List<HVConfigUIModelPO> getUIModelList(String reportbindid){
		List<HVConfigUIModelPO> poList = hvUIModelDao.getListOfReportId(reportbindid);
		return poList;
	}
	/**
	 * 数据库中未保存，则初始化
	 * @param reportBindid
	 * @param hbindid
	 */
	public void initDataFroms(String reportBindid,long hbindid){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		vConfigList = verticalColumnService.getVerticalColumnCByReportOfPO(reportBindid+"");
		
		List<HVConfigUIModelPO> dataFromList = hvUIModelDao.getListOfReportIdAndHid(reportBindid, hbindid);
		
		for(VerticalColumnConfigPO vPO:vConfigList){
			HVConfigUIModelPO uiModelPO = new HVConfigUIModelPO();
			uiModelPO.setReportBindid(reportBindid);
			uiModelPO.setHbindid(hbindid);
			uiModelPO.setVbindid(vPO.getBindId());
			
			if(!dataFromList.contains(uiModelPO)){
				save(uiModelPO);
			}
		}
	}
	
	public Map<String,HVConfigUIModelPO> getUIModelMap(String reportbindid){
		Map<String,HVConfigUIModelPO> uiModelMap = new HashMap<String,HVConfigUIModelPO>();
		List<HVConfigUIModelPO> uiModelPoList = getUIModelList(reportbindid);
		for(HVConfigUIModelPO po:uiModelPoList){
			uiModelMap.put(po.getHbindid()+"#"+po.getVbindid(), po);
		}
		return uiModelMap;
	}
	

	public String save(String jsonData){
		HVConfigUIModelModel model = new HVConfigUIModelModel(jsonData);
		return save(model);
	}
	
	public String save(HVConfigUIModelModel model){
		HVConfigUIModelPO po = new HVConfigUIModelPO(model);
		return save(po);
	}
	
	public String save(HVConfigUIModelPO po){
		po.initBindId();
		return hvUIModelDao.insertOrUpdate(po);
	}
	
	/**
	 * 根据横向、纵向、单元格配置情况获得单元格的数据来源 
	 * @param headerConfig  横行配置信息
	 * @param columnConfig  纵向配置信息
	 * @param cellConfig    单元格的UIModel配置信息
	 * @return
	 */
        /*
	public static HVConfigUIModelPO getCellUIModel(HeaderRowConfigPO headerConfig,VerticalColumnConfigPO columnConfig,HVConfigUIModelPO cellConfig){
		HVConfigUIModelPO return_uimodel = null;
		if(headerConfig!=null){
			return_uimodel = new HVConfigUIModelPO(headerConfig);
		}
		
		if(cellConfig!=null && cellConfig.getUIModule()!=0 && cellConfig.getUIModule()!=StaticVaribles.HTMLUIModule_NullBindid){
			return_uimodel = cellConfig;
		}
		return return_uimodel;
	}
        */
}

class ComparatoUIModel implements Comparator<HVConfigUIModelModel>{

	public int compare(HVConfigUIModelModel o1, HVConfigUIModelModel o2) {
		String order1 = o1.getOrderIndexStr();
		String order2 = o2.getOrderIndexStr();
		char[] order1s = order1.toCharArray();
		char[] order2s = order2.toCharArray();
		int order1s_length = order1s.length;
		int order2s_length = order2s.length;
		if(order1s_length>order2s_length){
			for(int l=0;l<order2s_length;l++){
				char c1 = order1s[l];
				char c2 = order2s[l];
				if(c1>c2){
					return 1;
				}
				if(c1<c2){
					return -1;
				}
			}
			return 1;
		}else if(order1s_length<order2s_length){
			for(int l=0;l<order1s_length;l++){
				char c1 = order1s[l];
				char c2 = order2s[l];
				if(c1>c2){
					return 1;
				}
				if(c1<c2){
					return -1;
				}
			}
			return -1;
		}else{
			for(int l=0;l<order1s_length;l++){
				char c1 = order1s[l];
				char c2 = order2s[l];
				if(c1>c2){
					return 1;
				}
				if(c1<c2){
					return -1;
				}
			}
			return 0;
		}
	}
}


