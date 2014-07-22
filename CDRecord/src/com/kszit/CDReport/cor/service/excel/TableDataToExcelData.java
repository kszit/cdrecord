/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 导出excel数据文件
 * 
 * @author Administrator
 */
public class TableDataToExcelData extends TableToExcel {

	// 数据值
	Map<String, String> dataMap = new HashMap<String, String>();

	public TableDataToExcelData(DataReportModel dataReportModel) {
		super(dataReportModel.getRepotBindid());
		
		dataMap = new ReportDataService(dataReportModel.getRepotBindid())
				.getDataReportCellDatas(dataReportModel.getBindId() + "");
	}

	public boolean createExcel(String filepathAndName) {
		boolean createSuc = false;

		createTitleRow();// 表体

		createHeaderRow();// 表头

		createExcelContent(tableColumn.getAllHeaderCellTree(), 0);// excel表体

		try {
			FileOutputStream fileOut = new FileOutputStream(filepathAndName);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return createSuc;
	}

	private void createExcelContent(List<TableColumnCell> columnCellList,
			int leve) {
		String leveStr = "";
		for (int i = 0; i < leve; i++) {
			leveStr += "---";
		}
		int col = 0;
		for (TableColumnCell column : columnCellList) {
			
			String columnName = column.getName();
			
			// 单位列表
			if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_Org) {
				List<DepartmentToReport> deptList = new DepartmentUtil()
						.getDepartmentService().getDepartmentListOfChild("0");
				for (DepartmentToReport dept : deptList) {
					String colName = leveStr + dept.getDepartmentName();
					String colBindid = dept.getBindid()+"";
					createOnewRow(colName,colBindid);
				}
			} else {
				String colName = leveStr + column.getName();
				String colBindid = column.getVerticalColumnConfigPO().getBindId()+"";
				createOnewRow(colName,colBindid);
			}
			
			
	
			
			
			List<TableColumnCell> childs = column.getChilds();
			if (childs != null) {
				createExcelContent(childs, column.getCurrentLayer());
			}
		}
	}
	
	private Row createOnewRow(String columnName,String vbindid){
		int col = 0;
		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(col++);
		cell.setCellValue(columnName);
		List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();// 表头
		for (int i = 1, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
			TableHeaderCell headerCell = headerCellList.get(i);
			if (headerCell.isLeaf()) {
				cell = row.createCell(col++);
				cell.setCellValue(dataMap.get(headerCell
						.getHeaderRowConfigPO().getBindId()
						+ "#"
						+ vbindid));
			}
		}
		
		return row;
	}

}
