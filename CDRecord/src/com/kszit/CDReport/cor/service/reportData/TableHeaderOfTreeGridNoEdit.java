package com.kszit.CDReport.cor.service.reportData;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;

/**
 * see TreeGrid 的头部，
*  
* 项目名称：CDRecord  
* 类名称：TableHeaderOfTreeGrid  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月25日 下午10:07:26  
* 修改人：hanxiaowei  
* 修改时间：2014年3月25日 下午10:07:26  
* 修改备注：  
* @version  
*
 */
public class TableHeaderOfTreeGridNoEdit extends TableHeaderOfGrid{

	
	public TableHeaderOfTreeGridNoEdit(List<HeaderRowConfigPO> hList) {
		super(hList);
	}


	/**
	 * 调用此方法，生成grid的头部；
	 * 全部表头，包括一级、二级、或者三级表头
	 * 
	 * @return
	 */
	public String getAllHeaderHtmlCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table id='roleEditGrid' data-options=\"toolbar:toolbar,striped:true\" pagination='false' idField='id' rownumbers='true'	fitColumns='true' singleSelect='true' fit='true' border='0' >	<thead>");
		sb.append(this.getHtmlCodeByCellList(allHeaderCellList));
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
				sb.append(headerCell.getHeaderHtmlCodeOfGridNoEdit());
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}

}
