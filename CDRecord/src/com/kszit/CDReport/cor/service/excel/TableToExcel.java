/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;

/**
 * 数据库配置文件生成excel文件
 * 
 * @author Administrator
 */
public class TableToExcel extends ExcelTableModel {

	//
	Sheet sheet = null;

	public TableToExcel(String tableBindid) {
		super(tableBindid);
		
		 List<HeaderRowConfigPO> hList = this.headerCellListWithNO();
		super.table.tableHeader = new TableHeader(hList);
		
		wb = new HSSFWorkbook();
		//
		sheet = wb.createSheet("new sheet");
	}

	public boolean createExcel(String filepathAndName) {
		boolean createSuc = false;

		createTitleRow();// 表体

		createHeaderRow();// 表头

		createMoldRow();// 数据类型行
		
		createHbindidRow();// hbindid行
		

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

	/**
	 * 根据树形结构，递归调用，生成excel的表头
	 * 
	 * @param cells
	 * @param rowNum
	 */
	void createHeaderRowTreeFormation(List<TableHeaderCell> cells, int rowNum) {
		if (this.rowNum < rowNum) {
			this.rowNum = rowNum;
		}
		if (cells == null || cells.size() == 0) {
			return;
		}
		Row row = sheet.getRow(rowNum);
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		int jumpCols = 0;// 因为有子节点，下一个要输出的单元格必须跳过一些单元格
		for (TableHeaderCell headerCell : cells) {

			int col = col = headerCell.getExcelCurrentCol() + jumpCols;// 列号
			Cell cell = row.createCell(col);
			cell.setCellValue(headerCell.getName());
			// System.out.println(headerCell.getName()+"=====》row:" + rowNum +
			// ";col:" + col);
			int colspan = headerCell.getColspan();
			int rowspan = headerCell.getRowspan();
			// System.out.println(headerCell.getName()+"=====》colspan:" +
			// colspan + ";rowspan:" + rowspan);
			if (colspan != 1) {
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col,
						col + colspan - 1));
				List<TableHeaderCell> childCells = headerCell.getChilds();
				List<TableHeaderCell> childCellsSort = table.tableHeader
						.sortCell(childCells);
				int childIndex = col;
				for (TableHeaderCell childCell : childCellsSort) {
					childCell.setExcelCurrentCol(childIndex++);
					jumpCols += childCell.getColspan();
				}
				jumpCols--;
			}
			if (rowspan != 1 && rowspan != 0) {
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum
						+ rowspan - 1, col, col));
			}
			createHeaderRowTreeFormation(headerCell.getChilds(), rowNum + 1);
		}
	}

	/**
	 * 生成表头
	 */
	void createHeaderRow() {
		List<TableHeaderCell> cells = table.tableHeader.allHeaderCellTree;
		cells = table.tableHeader.sortCell(cells);// 排序
		int begigCol = 0;
		for (TableHeaderCell headerCell : cells) {
			headerCell.setExcelCurrentCol(begigCol++);
		}
		

		createHeaderRowTreeFormation(cells, rowNum);// 生成表头
	}

	/**
	 * 
	* createHbindidRow(        生成列表头的hbindid行            ) <br> 
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
	private void createHbindidRow(){
		Row row = sheet.createRow(rowNum++);
		List<TableHeaderCell> inputHeaderCellList = tableHeader.getInputHeaderCellList();//tableHeader 对象未添加no列头
		int col = 0;
		
		for (TableHeaderCell cell : inputHeaderCellList) {
			if (cell.isLeaf()) {
				if(col==0){
					Cell tableCell = row.createCell(col);
					tableCell.setCellValue(super.BEGIN_ROW_ONECELL);
					col++;
					tableCell = row.createCell(col);
					tableCell.setCellValue("");
					col++;
					continue;
				}
			
				Cell tableCell = row.createCell(col);
				tableCell.setCellValue(cell.getHeaderRowConfigPO().getBindId());
				col++;
			}
		}
	}
	/**
	 * 按照表头的配置生成每列数据类型,tableHeader
	 */
	private void createMoldRow() {
		Row row = sheet.createRow(rowNum++);
		List<TableHeaderCell> inputHeaderCellList = tableHeader.getInputHeaderCellList();//tableHeader 对象未添加no列头
		int col = 0;
		
		for (TableHeaderCell cell : inputHeaderCellList) {
			if(col==1){
				Cell tableCell = row.createCell(col);
				tableCell.setCellValue("");
				col++;
			}
			if (cell.isLeaf()) {
				Cell tableCell = row.createCell(col);
				tableCell.setCellValue(cell.getHeaderRowConfigPO().getDataTypeExplain());
				col++;
			}
		}
	}

	private void createExcelContent(List<TableColumnCell> columnCellList,
			int leve) {
		String leveStr = "";
		for (int i = 0; i < leve; i++) {
			leveStr += "---";
		}
		int col = 0;
		
		for (TableColumnCell column : columnCellList) {
			col = 0;
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(col++);
			cell.setCellValue(leveStr + column.getName());

			cell = row.createCell(col++);
			cell.setCellValue(column.getVerticalColumnConfigPO().getBindId());

			List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();// 表头
			for (int i = 1, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
				TableHeaderCell headerCell = headerCellList.get(i);
				if (headerCell.isLeaf()) {
					cell = row.createCell(col++);
					cell.setCellValue("");
				}
			}

			List<TableColumnCell> childs = column.getChilds();
			if (childs != null) {
				createExcelContent(childs, column.getCurrentLayer());
			}
		}
	}

	void createTitleRow() {

		// 父类定义了标题的跨行数，但此处未使用 titleRowNum = 1;

		Row row = sheet.createRow(rowNum);
		Cell cell = row.createCell(0);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 1));
		cell.setCellValue("版本号：" + table.reportConfigPo.getRtversion2());

		cell = row.createCell(2);
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 2,
				allColnum - 1));
		cell.setCellValue(table.reportConfigPo.getReportName());
		rowNum++;
	}
	
	/**
	 * 
	* headerCellListWithNO(   <span font='red'>excel列表头的配置中 添加行表头的行号列 </span>      ) <br> 
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
	private List<HeaderRowConfigPO> headerCellListWithNO(){
		HeaderRowService hService = new HeaderRowService();
		List<HeaderRowConfigPO> hList = hService.getHeaderRowCByReportOfPO(super.tableBindid);
		
		HeaderRowConfigPO noCell = null;
		for(HeaderRowConfigPO hpo:hList){
			if(hpo.is_is_leaf()){
				noCell = new HeaderRowConfigPO(hpo);
				break;
			}
		}
		noCell.setOrderIndexStr("a");
		noCell.setContent("NO");
		hList.add(noCell);
		
		return hList;
	}

}
