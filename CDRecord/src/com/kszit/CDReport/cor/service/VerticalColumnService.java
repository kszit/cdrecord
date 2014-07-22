package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kszit.CDReport.cor.dao.HVConfigDataFromDao;
import com.kszit.CDReport.cor.dao.VerticalColumnConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.HVConfigDataFromDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.VerticalColumnConfigDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.VerticalColumnConfigModel;
import com.kszit.CDReport.cor.ui.IUIModel;
import com.kszit.CDReport.util.ClassInstallUtil;

public class VerticalColumnService {
	static Logger logger = LoggerFactory.getLogger(VerticalColumnService.class);
	VerticalColumnConfigDao vccDao = null;
	public VerticalColumnService(){
		vccDao = new VerticalColumnConfigDaoHiberImpl();
	}
	
	public String createLeftItemFromDictOrBuess(String dictid,String reportLinkId){
		
		DictionaryService dicService = new DictionaryService();
		DictionaryPO dicPo = dicService.getOneByBindid(dictid);
		IUIModel uiModel = (IUIModel)ClassInstallUtil.installClass(dicPo.getDescribe2());
		Map<String, String> areMap = uiModel.tableLeftOfDynamicsCell();	
		
		Iterator<String> iter = areMap.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String value = areMap.get(key);
			
			VerticalColumnConfigPO vPO = new VerticalColumnConfigPO();
			vPO.setBindId(Long.parseLong(key));
			vPO.setContent(value);
			vPO.setReportBindid(reportLinkId);
			
			vPO.set_is_leaf(true);
			vPO.setWidth(100);
			vPO.setHeight(100);
			vPO.setDataType(103);
			vPO.setUIModule(141);
			vPO.setDataFrom(112);
			vPO.setUIModuleDS("182");
			vPO.setOrderIndexStr("a");
			
			vccDao.saveOrUpdate(vPO);
			
		}
		
		return "";
	}
	
	/**
	 * 保存数据
	 * @param jsondata
	 * @return
	 */
	public String saveOrUpdate(String jsondata){
		VerticalColumnConfigModel model = new VerticalColumnConfigModel(jsondata);
		return this.saveOrUpdate(model);
	}
	/**
	 * 保存数据
	 * @param model
	 * @return
	 */
	public String saveOrUpdate(VerticalColumnConfigModel model){
		VerticalColumnConfigPO po = new VerticalColumnConfigPO(model);
		po.initBindId();
		return vccDao.saveOrUpdate(po);
	}
	
	/**
	 * 保存数据
	 * @param model
	 * @return
	 */
	public String saveOrUpdate(VerticalColumnConfigPO po){
		po.initBindId();
		return vccDao.saveOrUpdate(po);
	}
	
	public String saveOrUpdate(List<VerticalColumnConfigModel> models){
		
		return "";
	}
	/**
	 * 由bindid获得对象
	 * @param bindid
	 * @return
	 */
	public VerticalColumnConfigModel getVerticalColByBindid(long bindid){
		VerticalColumnConfigPO po = vccDao.getOneByBindid(bindid);
		return new VerticalColumnConfigModel(po);
	}
	/**
	 * 保存uimodel数据
	 * @param model
	 * @return
	 */
	public String updateUIModel(VerticalColumnConfigModel model){
		VerticalColumnConfigModel newModel = getOneByBindid(model.getBindId());
		newModel.setUIModule(model.getUIModule());
		newModel.setUIModuleDS(model.getUIModuleDS());
		return saveOrUpdate(newModel);
	}
	
	public String copyVerticalColumn(ReportConfigPO oldConfigPo,ReportConfigPO newConfigPo){
		List<VerticalColumnConfigPO> oldColumnList = vccDao.getList(oldConfigPo.getReportBindidLink());
		for(VerticalColumnConfigPO oldrowpo:oldColumnList){
			VerticalColumnConfigPO newColmnPo = new VerticalColumnConfigPO(oldrowpo);
			newColmnPo.setReportBindid(newConfigPo.getReportBindidLink());
			//newColmnPo.setDataRuleItems(oldrowpo.getd.getDataRuleItems().replaceAll(oldConfigPo.getReportBindidLink(), newConfigPo.getReportBindidLink()));
			saveOrUpdate(newColmnPo);
		}
		return "";
		
	}
	/**
	 * 更改是否为叶子节点
	 * @param id
	 */
	public void changeIsLeaf(long id){
		VerticalColumnConfigPO po = vccDao.getOneByBindid(id);
		if(po.is_is_leaf()){
			po.set_is_leaf(false);
		}else{
			po.set_is_leaf(true);
		}
		vccDao.update(po);
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id){
		return vccDao.delete(Long.parseLong(id));
	}
	/**
	 * 删除
	 * @param ids  3,5,6
	 * @return
	 */
	public boolean deletes(String ids){
		List<VerticalColumnConfigPO> deleteObj = new ArrayList<VerticalColumnConfigPO>();
		String[] array_ids = ids.split("\\,");
		for(int i=0,ids_length=array_ids.length;i<ids_length;i++){
			long id = Long.parseLong(array_ids[i]);
			VerticalColumnConfigPO po = vccDao.getOneById(id);
			deleteObj.add(po);
		}
		boolean result = vccDao.deletes(ids);
		HVConfigDataFromDao hvDataFromDao = new HVConfigDataFromDaoHiberImpl();
		for(VerticalColumnConfigPO p:deleteObj){
			hvDataFromDao.deletesByReportIdAndVid(p.getReportBindid(), p.getBindId());//删除对应的数据关系项
			int count = vccDao.childCount(p.get_parent());
			if(count==0){
				changeIsLeaf(p.get_parent());
			}
		}
		
		return result;
	}
	
	/**
	 * 删除
	 * @param ids  3,5,6
	 * @return
	 */
	public void deletesByReport(String reportBindid){
		vccDao.deleteByBindids(reportBindid);
	}
	/**
	 * list 所有数据
	 * @return
	 */
	public List<VerticalColumnConfigModel> getAllVerticalColumnC(){
		List<VerticalColumnConfigModel> headerRows = new ArrayList<VerticalColumnConfigModel>();
		List<VerticalColumnConfigPO> pos = vccDao.getList();
		for(VerticalColumnConfigPO po:pos){
			VerticalColumnConfigModel model = new VerticalColumnConfigModel(po);
			headerRows.add(model);
		}
		return headerRows;
	}
	/**
	 * list  报表bindid
	 * @param reportid
	 * @return
	 */
	public List<VerticalColumnConfigPO> getVerticalColumnCByReportOfPO(String reportBindid){
		return vccDao.getList(reportBindid);
	}
	/**
	 * list 对象数组
	 * @param reportid
	 * @return
	 */
	public List<VerticalColumnConfigModel> getVerticalColumnCByReport(String reportBindid){
		List<VerticalColumnConfigModel> headerRows = new ArrayList<VerticalColumnConfigModel>();
		List<VerticalColumnConfigPO> pos = vccDao.getList(reportBindid);
		for(VerticalColumnConfigPO po:pos){
			VerticalColumnConfigModel model = new VerticalColumnConfigModel(po);
			headerRows.add(model);
		}
		return headerRows;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNewVerticalColumnId(){
		VerticalColumnConfigPO po = new VerticalColumnConfigPO();
		return this.getNewVerticalColumnId(po);
	}
	/**
	 * 初始化新纪录，获得id
	 * @return
	 */
	public String getNewVerticalColumnId(VerticalColumnConfigPO po){
		po.initBindId();
		return vccDao.insert(po);
	}
	
	/**
	 * 新叶子节点的排序号
	 * @param parentid
	 * @return
	 */
	public String nextVerticalColumnIndexOrder(String reportBindid,String parentid){
		String nextIndexOrder = "";
		List<VerticalColumnConfigPO> pos = vccDao.getListChilds(reportBindid,Integer.parseInt(parentid));
		if(!pos.isEmpty()){
			String indexOrder = pos.get(pos.size()-1).getOrderIndexStr();
			char currentChar = indexOrder.charAt(indexOrder.length()-1);
			currentChar++;
			StringBuilder strb = new StringBuilder(indexOrder);
			strb.replace(indexOrder.length()-1, indexOrder.length(), currentChar+"");
			nextIndexOrder = strb.toString();
		}else{
			if("0".equals(parentid)){
				nextIndexOrder = "a";
			}else{
				VerticalColumnConfigPO parent = vccDao.getOneByBindid(Long.parseLong(parentid));
				if(parent!=null){
					changeIsLeaf(Long.parseLong(parentid));
					nextIndexOrder = parent.getOrderIndexStr()+"#a";
				}else{
					nextIndexOrder = "a";
				}
			}

		}
		return nextIndexOrder;
	}
	/**
	 * 获得数据对象
	 * @param bindid
	 * @return
	 */
	public VerticalColumnConfigModel getOneByBindid(long bindid){
		VerticalColumnConfigPO po = vccDao.getOneByBindid(bindid);
		return new VerticalColumnConfigModel(po);
	}
	
	/**
	 * 获得数据对象
	 * @param bindid
	 * @return
	 */
	public VerticalColumnConfigPO getOneByReportIdAndBindid(String reportBindid,long bindid){
		
		VerticalColumnConfigPO po = vccDao.getOneByReportIdAndBindid(reportBindid, bindid);
		return po;
	}
}
