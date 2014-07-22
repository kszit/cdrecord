package com.kszit.CDReport.cor.service.reportData;

import java.util.List;

import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.StaticVaribles;


public class SelectHVCellPartTableContent extends TableContent{
	
	String selectCells = "";
	
	public SelectHVCellPartTableContent(){}
	
	public SelectHVCellPartTableContent(String reportBindid,String dataId,TableHeader tableHeader,TableColumn tableColumn,
			Table inputTable,String selectCells){
		super(reportBindid, dataId,tableHeader, tableColumn,inputTable);
		//initDataTable();
		initDataFrom();
		initUIModel();
		this.selectCells =  selectCells;
	}
	

	
	
	/**
	 * 生成一行数据
	 * 
	 * @param colName
	 * @param column
	 * @return
	 */
	String getOneInputRow(String colName, TableColumnCell column,DepartmentToReport dept) {
		String vbindid = column.getVerticalColumnConfigPO().getBindId()+"";
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		sb.append("<td class='seeReportTableTd'>" + colName + "</td>");
		List<TableHeaderCell> headerCellList = tableHeader
				.getInputHeaderCellList();// 表头
		for (int i = 1, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
			TableHeaderCell headerCell = headerCellList.get(i);
			if (headerCell.isLeaf()) {
				String hbindid = "";
				
				if (column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_Org || 
						column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_AreaList ) {

					vbindid = dept.getBindid(); 
					
				}
				
				hbindid = headerCell.getHeaderRowConfigPO().getBindId()+"";
				
				String hvbindid = super.reportBindid+"$"+hbindid+"#"+vbindid;
				String ischekced = "";
				if(selectCells.contains(hvbindid)){
					ischekced = "checked";
				}
				sb.append("<td class='seeReportTableTd'>");
				sb.append("<input name='tableHVCell' onclick type='checkbox' value='"+hvbindid+"' "+ischekced+" ></input>");//输出每个单元格数据
				sb.append("</td>");
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
