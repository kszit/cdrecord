package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DBQueryUtilDao;
import com.kszit.CDReport.cor.dao.hibernate.DBQueryUtilDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HVConfigCellDValueService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HVConfigUIModelService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.cor.ui.IUIModel;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.ClassInstallUtil;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;

class TableContentOfTreeGrid extends TableContent{

	public TableContentOfTreeGrid() {
	}

	public TableContentOfTreeGrid(String reportBindid, String dataId,
			TableHeader tableHeader, TableColumn tableColumn,
			Table inputTable) {
		super(reportBindid, dataId,tableHeader, tableColumn,inputTable);
		initDataTable();
		initDataFrom();
		initUIModel();
		initCellDefaultValue();
	}
	
	@Override
	String getRowJSON(String colName, TableColumnCell column,
			DepartmentToReport dept) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		
		sb.append("\"id\":"+column.getVerticalColumnConfigPO().getBindId()+",");
		List<TableHeaderCell> headerCellList = tableHeader
				.getInputHeaderCellList();// 表头
		StringBuffer submitItemIds = new StringBuffer();
		for (int i = 0, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
			TableHeaderCell headerCell = headerCellList.get(i);
			if (headerCell.isLeaf()) {
				HeaderRowConfigPO headerPo = headerCell.getHeaderRowConfigPO();
				String cellDataValue = getCellData(headerCell, column,dept);
				if(i==0){
					cellDataValue = colName;
				}
				if(cellDataValue==null){
					cellDataValue = "";
				}
				//处理特殊字符（json）
				cellDataValue = StringUtil.string2Json(cellDataValue);
				//添加<br>
				cellDataValue = StringUtil.TextAddBr(cellDataValue,headerPo.getWidth());
				
				sb.append("\""+headerPo.getBindId()+"\":\""+cellDataValue+"\",");
				
				HVConfigDataFromPO dataFrom = super.getCellDataFromWithHV(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO());
				if(dataFrom!=null){
					if(dataFrom.isSetInPage()){
						submitItemIds.append(headerPo.getBindId()).append("|");
					}
				}
			}
		}
		
		sb.append("\"submitItemIds\":\""+submitItemIds.toString()+"\",");
		
		if(column.getParent()!=null){
			long parentId = column.getParent().getVerticalColumnConfigPO().bindId;
			sb.append("\"_parentId\":"+parentId+",");
		}
		if(column.getChilds()!=null && column.getChilds().size()>0){
			sb.append("\"state\":\"closed\",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("},");
		return sb.toString();
	}

	@Override
	String getOneInputRow(String colName, TableColumnCell column,
			DepartmentToReport dept) {
		// TODO Auto-generated method stub
		return null;
	}

}
