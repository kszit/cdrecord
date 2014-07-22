package com.kszit.CDReport.cor.service;

import com.kszit.CDReport.cor.dao.DataCellDao;
import com.kszit.CDReport.cor.dao.DataDBTableIndexDao;
import com.kszit.CDReport.cor.dao.hibernate.DataCellDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.DataDBTableIndexDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataDBTableIndexPO;
import com.kszit.CDReport.util.StaticVaribles;

public class DataDBTableIndexService {
	DataDBTableIndexDao tableIndexDao = null;
	DataCellDao tableDao = null;
	public DataDBTableIndexService(){
		tableIndexDao = new DataDBTableIndexDaoHiberImpl();
		tableDao = new DataCellDaoHiberImpl("");
	}
	
	/**
	 * 动态生成保存数据的数据库表，保存报表对应关系表
	 * @param model
	 * @return
	 */
	public String insert(String reportBindId){
		tableDao.createTable(StaticVaribles.dataTableNameFore+reportBindId);
		DataDBTableIndexPO oldPo = tableIndexDao.getOneByReportid(Long.parseLong(reportBindId));
		if(oldPo == null){
			DataDBTableIndexPO po = new DataDBTableIndexPO();
			po.setReportId(Integer.parseInt(reportBindId));
			po.setTableName(StaticVaribles.dataTableNameFore+reportBindId);
			return tableIndexDao.insert(po);
		}
		return "";
	}
	
	public void dropTable(String reportBindid){
		tableDao.dropTable(StaticVaribles.dataTableNameFore+reportBindid);
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id){
		return tableIndexDao.delete(Long.parseLong(id));
	}
	/**
	 * 通过报表id删除
	 * @param id
	 * @return
	 */
	public boolean deleteByReportid(String reportId){
		return tableIndexDao.deleteByReportId(Integer.parseInt(reportId));
	}
	/**
	 * 通过报表id删除
	 * @param id
	 * @return
	 */
	public boolean deleteByReportids(String reportIds){
		return tableIndexDao.deleteByReportIds(reportIds);
	}
	
	
	

}
