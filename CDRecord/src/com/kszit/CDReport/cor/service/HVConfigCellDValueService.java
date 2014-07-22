package com.kszit.CDReport.cor.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.HVConfigCellDValueDao;
import com.kszit.CDReport.cor.dao.hibernate.HVConfigCellDValueDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigCellDValuePO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.HVConfigCellDValueModel;
import com.kszit.CDReport.cor.model.HVConfigDataFromModel;
import com.kszit.CDReport.cor.service.reportData.CellDefaultValueTable;

/**
 * 单元格默认值设定服务类
*  
* 项目名称：CDRecord  
* 类名称：HVConfigCellDValueService  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月8日 下午5:48:29  
* 修改人：hanxiaowei  
* 修改时间：2014年3月8日 下午5:48:29  
* 修改备注：  
* @version  
*
 */
public class HVConfigCellDValueService {
	
	HVConfigCellDValueDao hvDataFromDao = null;
	List<VerticalColumnConfigPO> vConfigList = null;
	public HVConfigCellDValueService(){
		hvDataFromDao = new HVConfigCellDValueDaoHiberImpl();
	}

	/**
	 *  添加
	 */
	public String save(HVConfigCellDValuePO po){
		po.initBindId();
		if(po.getId()==0){
			po.setId(null);
		}
		return hvDataFromDao.insertOrUpdate(po);
	}
	/**
	 *  添加
	 */
	public String save(HVConfigCellDValueModel model){
		HVConfigCellDValuePO po = new HVConfigCellDValuePO(model);
		return this.save(po);
	}
	
	/**
	 *  更新
	 */
	public String update(HVConfigCellDValuePO po){
		return hvDataFromDao.insertOrUpdate(po);
	}
	
	/**
	 * 查询单个单元格的默认值
	 *
	* @since  -
	 */
	public HVConfigCellDValuePO getCellDefaultValue(String reportBindid,long hbindid,long vbindid){
		return hvDataFromDao.getCellValue(reportBindid, hbindid, vbindid);
	}
	
	/**
	 * 查询表格所有单元格的默认值 
	 */
	public List<HVConfigCellDValuePO> getCellDefaultValues(String reportbindid){
		
		List<HVConfigCellDValuePO> poList = hvDataFromDao.getListOfReportId(reportbindid);
		
		return poList;
	}
	
	/**
	 * 查询表格所有单元格的默认值 ，以hbindid#vbindid形式为主键
	 * @param reportbindid
	 * @return
	 */
	public Map<String,HVConfigCellDValuePO> getDataFromMap(String reportbindid){
		Map<String,HVConfigCellDValuePO> dataFromMap = new HashMap<String,HVConfigCellDValuePO>();
		List<HVConfigCellDValuePO> dataFromPoList = getCellDefaultValues(reportbindid);
		for(HVConfigCellDValuePO po:dataFromPoList){
			dataFromMap.put(po.getHbindid()+"#"+po.getVbindid(), po);
		}
		return dataFromMap;
	}
	
	/**
	 * 查询表格所有单元格的默认值 ，以hbindid#vbindid形式为主键
	 * @param reportbindid
	 * @return
	 */
	public Map<String,String> getCellDefaultStringValues(String reportbindid){
		Map<String,String> dataFromMap = new HashMap<String,String>();
		List<HVConfigCellDValuePO> dataFromPoList = getCellDefaultValues(reportbindid);
		for(HVConfigCellDValuePO po:dataFromPoList){
			dataFromMap.put(po.getHbindid()+"#"+po.getVbindid(), po.getDefaultValue());
		}
		return dataFromMap;
	}
	
	
	/**
	 * 查看表格 单元格默认值    配置情况
	 * @param reportbindid
	 * @return
	 */
	public String seeCellDefaultValueTable(String reportbindid){
		CellDefaultValueTable dataFromTable = new CellDefaultValueTable(reportbindid);
		return dataFromTable.outTable();
	}
	
	/**
	 * 
	* @since  -
	 */
	public void deletes(String reportBindid){
//		hvDataFromDao.deletesByReportId(reportBindid);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 复制
	 * @param oldConfigPo
	 * @param newConfigPo
	 */
//	public void copyDataFrom(ReportConfigPO oldConfigPo,ReportConfigPO newConfigPo){
//		String oldReportBindid = oldConfigPo.getReportBindidLink();
//		String newReportBindid = newConfigPo.getReportBindidLink();
//		List<HVConfigDataFromPO> oldDataFrom = getDataFromList(oldReportBindid);
//		for(HVConfigDataFromPO datafromPo:oldDataFrom){
//			HVConfigDataFromPO newDataFrom = new HVConfigDataFromPO(datafromPo);
//			newDataFrom.setReportBindid(newReportBindid);
//			if(newDataFrom.getDataTerm()!=null){
//				newDataFrom.setDataTerm(newDataFrom.getDataTerm().replaceAll(oldReportBindid, newReportBindid));
//			}
//			save(newDataFrom);
//		}
//	}
	
	/**
	 * 
	 * @param reportbindid
	 * @param hbindid
	 * @return
	 */
	public List<HVConfigDataFromModel> getDataFromList(String reportbindid,long hbindid){
		
//		initDataFroms(reportbindid,hbindid);
//		
//		List<HVConfigDataFromPO> poList = hvDataFromDao.getListOfReportIdAndHid(reportbindid, hbindid);
//		List<HVConfigDataFromModel> moList = new ArrayList<HVConfigDataFromModel>();
//		for(HVConfigDataFromPO po:poList){
//			HVConfigDataFromModel model = new HVConfigDataFromModel(po);
//			for(VerticalColumnConfigPO vpo:vConfigList){
//				if(model.getVbindid() == vpo.getBindId()){
//					model.setvName(vpo.getContent());
//					model.set_id(vpo.getBindId().intValue());
//					model.set_parent(vpo.get_parent());
//					model.set_is_leaf(vpo.is_is_leaf());
//					model.setOrderIndexStr(vpo.getOrderIndexStr());
//				}
//			}
//			moList.add(model);
//		}
//		
//		ComparatorDataFromModel comparator=new ComparatorDataFromModel();
//		Collections.sort(moList,comparator);
//		
//		return moList;
		return null;
	}
	

	
	/**
	 * 数据库中未保存，则初始化
	 * @param reportBindid
	 * @param hbindid
	 */
//	public void initDataFroms(String reportBindid,long hbindid){
//		VerticalColumnService verticalColumnService = new VerticalColumnService();
//		vConfigList = verticalColumnService.getVerticalColumnCByReportOfPO(reportBindid+"");
//		
//		List<HVConfigDataFromPO> dataFromList = hvDataFromDao.getListOfReportIdAndHid(reportBindid, hbindid);
//		
//		for(VerticalColumnConfigPO vPO:vConfigList){
//			HVConfigDataFromPO datafromPO = new HVConfigDataFromPO();
//			datafromPO.setReportBindid(reportBindid);
//			datafromPO.setHbindid(hbindid);
//			datafromPO.setVbindid(vPO.getBindId());
//			
//			if(!dataFromList.contains(datafromPO)){
//				save(datafromPO);
//			}
//		}
//	}
	
	
}


