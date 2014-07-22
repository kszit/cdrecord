package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.html.HtmlCodeUtil;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 
 * 报表查看页面
*  
* 项目名称：CDRecord  
* 类名称：SeeTableContent  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月9日 下午12:53:33  
* 修改人：hanxiaowei  
* 修改时间：2014年3月9日 下午12:53:33  
* 修改备注：  
* @version  
*
 */
public class SeeTableContent extends TableContent{
	
	
	
	public SeeTableContent(){}
	
	public SeeTableContent(String reportBindid,String dataId,TableHeader tableHeader,TableColumn tableColumn,
			Table inputTable){
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
				
				if (column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_Org || 
						column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_AreaList ) {
					
					cellDataValue = getCellData(headerCell.getHeaderRowConfigPO().getBindId()+"",dept.getBindid());
				}else{
					cellDataValue = getCellData(headerCell,column,null);
				}
				
				sb.append("<td class='seeReportTableTd'>");
				if(headerCell.getHeaderRowConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_Picture){
					sb.append("<a href='"+cellDataValue+"'  target='blank'><img src='"+cellDataValue+"' width='40' height='40' ></a>");
				}else{
					sb.append(cellDataValue==null?"":cellDataValue);//输出每个单元格数据
				}
				
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
