package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.util.Constants;

import java.util.Collections;

public class TableHeaderOfGrid extends TableHeader{

	
	public TableHeaderOfGrid(List<HeaderRowConfigPO> hList) {
		super(hList);
	}


	/**
	 * 全部表头，包括一级、二级、或者三级表头
	 * 
	 * @return
	 */
	public String getAllHeaderHtmlCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table id='roleEditGrid' 	toolbar='#roleEditToolbar' pagination='true' idField='id' rownumbers='true'	fitColumns='true' singleSelect='true' fit='true' border='0' >	<thead>");
		sb.append(getHtmlCodeByCellList(allHeaderCellList));
		sb.append("</thead></table>");
		return sb.toString(); 
	}

	/**
	 * 拼装表头
	 * 
	 * @param cellList
	 * @return
	 */
	protected String getHtmlCodeByCellList(List<TableHeaderCell> cellList) {
		Hashtable<Integer, List<TableHeaderCell>> tableHeaderRowsTable = packageTable(cellList);

		StringBuffer sb = new StringBuffer();
		Map.Entry[] set = getSortedHashtable(tableHeaderRowsTable);
		for (int i = 0; i < set.length; i++) {
			sb.append("<tr>");
			Iterator<TableHeaderCell> rowCellIter = ((List<TableHeaderCell>) set[i]
					.getValue()).iterator();
			while (rowCellIter.hasNext()) {
				TableHeaderCell headerCell = rowCellIter.next();
				sb.append(headerCell.getHeaderHtmlCodeOfGrid());
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
}
