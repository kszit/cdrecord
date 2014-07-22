package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.DataCellService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HVConfigUIModelService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 报表填报和修改页面
*  
* 项目名称：CDRecord  
* 类名称：InputTableContent  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月9日 下午12:11:14  
* 修改人：hanxiaowei  
* 修改时间：2014年3月9日 下午12:11:14  
* 修改备注：  
* @version  
*
 */
public class InputTableContent extends TableContent{


	public InputTableContent() {
	}

	public InputTableContent(String reportBindid, String dataId,
			TableHeader tableHeader, TableColumn tableColumn,
			Table inputTable) {
		super(reportBindid, dataId,tableHeader, tableColumn,inputTable);
		initDataTable();
		initDataFrom();
		initUIModel();
		initCellDefaultValue();
	}


	/**
	 * 生成一行数据
	 * 
	 * @param colName
	 * @param column
	 * @return
	 */
	String getOneInputRow(String colName, TableColumnCell column,DepartmentToReport dept) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		sb.append("<td class='seeReportTableTd'>" + colName + "</td>");
		List<TableHeaderCell> headerCellList = tableHeader
				.getInputHeaderCellList();// 表头
		for (int i = 1, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
			TableHeaderCell headerCell = headerCellList.get(i);
			if (headerCell.isLeaf()) {
				String cellDataValue = "";
				
				//HVConfigDataFromPO dataFromPo = getCellDataFromWithHV(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO());
				//String dataFromType = dataFromPo.getDataFromType();
				/*
				if (dataFromType != null && dataFromType.equals(StaticVaribles.DataFrom_LowerLevelCell+"")) {
					
					cellDataValue = getCellDataFromOthorTable(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO(),dept);
				}else if(dataFromType != null && dataFromType.equals(StaticVaribles.DataFrom_BrothersReportData+"")) {
					
					cellDataValue = getCellDataFromOthorTable(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO(),dept);
				}else if(dataFromType != null && dataFromType.equals(StaticVaribles.DataFrom_ServiceTable+"")) {
					cellDataValue = getCellDataFrombusinessTable(dept.getBindid(),dataFromPo.getFormula());
					//cellDataValue = getCellDataFromOthorTable(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO(),dept);
				}else{
					
					cellDataValue = getCellData(headerCell, column,dept);
				}
				*/
				cellDataValue = getCellData(headerCell, column,dept);
				sb.append(headerCell.getInputHTMLCode(column, cellDataValue,
						getCellDataFrom(headerCell, column),
						getCellUIModel(headerCell, column),dept));// 输出每个单元格数据
			}
		}
		return sb.toString();
	}

	@Override
	String getRowJSON(String colName, TableColumnCell column,
			DepartmentToReport dept) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
