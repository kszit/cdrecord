package com.kszit.CDReport.cor.service.dataFrom;

import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;

public abstract class CalculateAbatract {
	/**
	 * 在本表中不能获取到公式子项值时，返回此值
	 */
	public static String LAST_CALCULATE = "next";
	/**
	 * 计算数据时，计算的数据已经在方法内添加到了数据列表中
	 */
	public static String NOT_PUT_DATA = "notPut";
	
	
	HVConfigDataFromPO datafromPo = null;
	Map<String,Object> dataMap = null;
	
	TableHeader tableHeader = null;
	TableColumn tableColum = null;
	DataReportModel dataReportModel = null;
	DataFrom dataFrom = null;
	
	public CalculateAbatract(HVConfigDataFromPO datafromPo,Map<String,Object> dataMap,TableHeader tableHeader ,TableColumn tableColum ,DataReportModel dataReportModel,DataFrom dataFrom){
		this.datafromPo = datafromPo;
		this.dataMap = dataMap;
		this.tableHeader = tableHeader;
		this.tableColum = tableColum;
		this.dataReportModel = dataReportModel;
		this.dataFrom = dataFrom;
	}
	
	public CalculateAbatract(){}
	
	public abstract String calculate();
	
	//列表头设计
	public abstract String getDataFromHtml(String reportLinkid);
	
	//单元格设计
	public abstract String getCellDataFromHtml(String reportLinkid);
	
	/**
	 * 
	* getCurrentTableColumnCell(获得当前单元格的行表头，表头中已经包括了父子项关系) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param name  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public TableColumnCell getCurrentTableColumnCell(){
		long vbindid = datafromPo.getVbindid();
		List<TableColumnCell> columCells = tableColum.getAllHeaderCellList();
		for(TableColumnCell cCell:columCells){
			long currenBindid = cCell.getVerticalColumnConfigPO().getBindId();
			if(currenBindid==vbindid){
				return cCell;
			}
		}
		return null;
	}
	
	
}
