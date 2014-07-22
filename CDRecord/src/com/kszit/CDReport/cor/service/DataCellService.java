package com.kszit.CDReport.cor.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DataCellDao;
import com.kszit.CDReport.cor.dao.hibernate.DataCellDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.util.StaticVaribles;

public class DataCellService {
	DataCellDao dataCellDao = null;
	int dataBindid = 0;
	public DataCellService(){}
	
	/**
	 * 
	* 创建一个新的实例 DataCellService.  
	*  
	* @param reportBindid 配置报表的reportLinkid，
	* @param dataBindid
	 */
	public DataCellService(String reportBindid,int dataBindid){
		if(reportBindid.contains("-")){
			reportBindid = reportBindid.split("-")[0];
		}
		dataCellDao = new DataCellDaoHiberImpl(StaticVaribles.dataTableNameFore+reportBindid);
		this.dataBindid = dataBindid;
	}
	
	/**
	 * 保存表格的单元格列表数据,必须先初始化dataBindid对象
	 * @param dataMap
	 * @param dataBindid
	 * @return
	 */
	public String saveTableCellsData(Map<String,Object> dataMap){
		deleteCellData();
		
		List<DataCellPO> datapos = new ArrayList<DataCellPO>();
		for(Object key:dataMap.keySet()){
			String keyStr = key.toString();
			String value = (String)dataMap.get(keyStr);
			if(keyStr.contains("#")){
				String[] hvid = keyStr.split("#");
				DataCellPO po = new DataCellPO();
				po.sethId(Integer.parseInt(hvid[0]));
				po.setvId(Integer.parseInt(hvid[1]));
				po.setDatavalue(value);
				po.setDataId(dataBindid);
				datapos.add(po);
			}
		}
		dataCellDao.inserts(datapos);
		return "";
	}
	
	/**
	 * 删除报表的所有数据
	 * 需要初始化 dataBindid 变量
	 */
	public void deleteCellData(){
		dataCellDao.deletesByDataBindid(dataBindid+"");
	}
	
	/**
	 * 获得报表的所有数据
	 * 需要初始化 dataBindid 变量
	 * @return
	 */
	public List<DataCellPO> getDataCellList(){
		return dataCellDao.getListOfReportId(dataBindid);
	}
	
	/**
	 * 获得某单元格的数据
	 * @param hBindid
	 * @param vBindid
	 * @return
	 */
	public DataCellPO getOneDataCell(long hBindid,long vBindid){
		return dataCellDao.getOneHidAndVid(dataBindid, hBindid, vBindid);
	}
	
	public int getDataBindid() {
		return dataBindid;
	}
	public void setDataBindid(int dataBindid) {
		this.dataBindid = dataBindid;
	}
	
}
