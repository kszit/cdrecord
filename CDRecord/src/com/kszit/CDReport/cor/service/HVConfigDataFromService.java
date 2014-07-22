package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.HVConfigDataFromDao;
import com.kszit.CDReport.cor.dao.hibernate.HVConfigDataFromDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.HVConfigDataFromModel;
import com.kszit.CDReport.cor.service.reportData.CellDefaultValueTable;
import com.kszit.CDReport.cor.service.reportData.DataFromTable;
/**
 * 数据来源（数据关系）服务类
 * @author Administrator
 *
 */
public class HVConfigDataFromService {
	
	HVConfigDataFromDao hvDataFromDao = null;
	List<VerticalColumnConfigPO> vConfigList = null;
	public HVConfigDataFromService(){
		hvDataFromDao = new HVConfigDataFromDaoHiberImpl();
	}
	
	
	public HVConfigDataFromPO getCellDataFrom(String reportBindid,long hbindid,long vbindid){
		return hvDataFromDao.getCellDataFromHVBindid(reportBindid, hbindid, vbindid);
	}
	
	
	
	/**
	 * 查看表格 数据来源 配置情况
	 * @param reportbindid
	 * @return
	 */
	public String seeDataFromAllTable(String reportbindid){
		DataFromTable dataFromTable = new DataFromTable(reportbindid);
		return dataFromTable.outTable();
	}
	
	public void deletes(String reportBindid){
		hvDataFromDao.deletesByReportId(reportBindid);
	}
	
	/**
	 * 复制
	 * @param oldConfigPo
	 * @param newConfigPo
	 */
	public void copyDataFrom(ReportConfigPO oldConfigPo,ReportConfigPO newConfigPo){
		String oldReportBindid = oldConfigPo.getReportBindidLink();
		String newReportBindid = newConfigPo.getReportBindidLink();
		List<HVConfigDataFromPO> oldDataFrom = getDataFromList(oldReportBindid);
		for(HVConfigDataFromPO datafromPo:oldDataFrom){
			HVConfigDataFromPO newDataFrom = new HVConfigDataFromPO(datafromPo);
			newDataFrom.setReportBindid(newReportBindid);
			if(newDataFrom.getDataTerm()!=null){
				newDataFrom.setDataTerm(newDataFrom.getDataTerm().replaceAll(oldReportBindid, newReportBindid));
			}
			save(newDataFrom);
		}
	}
	
	/**
	 * 
	 * @param reportbindid
	 * @param hbindid
	 * @return
	 */
	public List<HVConfigDataFromModel> getDataFromList(String reportbindid,long hbindid){
		
		initDataFroms(reportbindid,hbindid);
		
		List<HVConfigDataFromPO> poList = hvDataFromDao.getListOfReportIdAndHid(reportbindid, hbindid);
		List<HVConfigDataFromModel> moList = new ArrayList<HVConfigDataFromModel>();
		for(HVConfigDataFromPO po:poList){
			HVConfigDataFromModel model = new HVConfigDataFromModel(po);
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
		
		ComparatorDataFromModel comparator=new ComparatorDataFromModel();
		Collections.sort(moList,comparator);
		
		return moList;
	}
	
	public List<HVConfigDataFromPO> getDataFromList(String reportbindid){
		
		List<HVConfigDataFromPO> poList = hvDataFromDao.getListOfReportId(reportbindid);
		
		return poList;
	}
	/**
	 * 每个单元格个的数据来源
	 * @param reportbindid
	 * @return
	 */
	public Map<String,HVConfigDataFromPO> getDataFromMap(String reportbindid){
		Map<String,HVConfigDataFromPO> dataFromMap = new HashMap<String,HVConfigDataFromPO>();
		List<HVConfigDataFromPO> dataFromPoList = getDataFromList(reportbindid);
		for(HVConfigDataFromPO po:dataFromPoList){
			dataFromMap.put(po.getHbindid()+"#"+po.getVbindid(), po);
		}
		return dataFromMap;
	}
	
	/**
	 * 数据库中未保存，则初始化
	 * @param reportBindid
	 * @param hbindid
	 */
	public void initDataFroms(String reportBindid,long hbindid){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		vConfigList = verticalColumnService.getVerticalColumnCByReportOfPO(reportBindid+"");
		
		List<HVConfigDataFromPO> dataFromList = hvDataFromDao.getListOfReportIdAndHid(reportBindid, hbindid);
		
		for(VerticalColumnConfigPO vPO:vConfigList){
			HVConfigDataFromPO datafromPO = new HVConfigDataFromPO();
			datafromPO.setReportBindid(reportBindid);
			datafromPO.setHbindid(hbindid);
			datafromPO.setVbindid(vPO.getBindId());
			
			if(!dataFromList.contains(datafromPO)){
				save(datafromPO);
			}
		}
	}
	
	public String save(String jsonData){
		HVConfigDataFromModel model = new HVConfigDataFromModel(jsonData);
		return save(model);
	}
	
	public String save(HVConfigDataFromModel model){
		HVConfigDataFromPO po = new HVConfigDataFromPO(model);
		return save(po);
	}
	
	public String save(HVConfigDataFromPO po){
		po.initBindId();
		return hvDataFromDao.insertOrUpdate(po);
	}

	public String update(HVConfigDataFromPO po){
		return hvDataFromDao.insertOrUpdate(po);
	}
}

class ComparatorDataFromModel implements Comparator<HVConfigDataFromModel>{

	public int compare(HVConfigDataFromModel o1, HVConfigDataFromModel o2) {
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


