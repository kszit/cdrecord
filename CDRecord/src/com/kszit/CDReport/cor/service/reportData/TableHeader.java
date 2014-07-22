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

public class TableHeader {

	public static Map<String, TableHeader> tableHeaderMap = new HashMap<String, TableHeader>();

	/**
	 * 获得报表的横向配置信息；本方法添加了缓存设置
	 * 
	 * @param reportbindid
	 * @return
	 */
	public static TableHeader getTableHeader(String reportbindid) {
		if (Constants.IS_DEVELEP) {
			HeaderRowService hService = new HeaderRowService();
			List<HeaderRowConfigPO> hList = hService
					.getHeaderRowCByReportOfPO(reportbindid);
			TableHeader tableHeader = new TableHeader(hList);
			return tableHeader;
		} else {
			if (tableHeaderMap.containsKey(reportbindid)) {
				return tableHeaderMap.get(reportbindid);
			} else {
				HeaderRowService hService = new HeaderRowService();
				List<HeaderRowConfigPO> hList = hService
						.getHeaderRowCByReportOfPO(reportbindid);
				TableHeader tableHeader = new TableHeader(hList);
				tableHeaderMap.put(reportbindid, tableHeader);
				return tableHeader;
			}
		}

	}

	/**
	 * 得到单元格的配置信息，本方法设置了缓存
	 * 
	 * @param reportbindid
	 * @param bindid
	 * @return
	 */
	public static TableHeaderCell getOneHeader(String reportbindid,
			String bindid) {

		TableHeader tableHeader = null;
		if (Constants.IS_DEVELEP) {
			HeaderRowService hService = new HeaderRowService();
			List<HeaderRowConfigPO> hList = hService
					.getHeaderRowCByReportOfPO(reportbindid);
			tableHeader = new TableHeader(hList);
		} else {
			tableHeader = getTableHeader(reportbindid);
		}

		for (TableHeaderCell header : tableHeader.getAllHeaderCellList()) {
			String headerBindid = header.getHeaderRowConfigPO().getBindId()
					+ "";
			if (headerBindid.equals(bindid)) {
				return header;
			}
		}
		return null;

	}

	/**
	 * 表头跨行数，实际的跨行数需要在此值上加一
	 */
	private int headerRowNum;

	private String headerRowCssClass = "content_header_tr";

	private int headerInputWidth;

	private int headerInputHeight;

	private int headerAllWidth;

	private int headerAllHeight;

	public List<TableHeaderCell> allHeaderCellTree = new ArrayList<TableHeaderCell>();
	public List<TableHeaderCell> allHeaderCellList = new ArrayList<TableHeaderCell>();

	public List<TableHeaderCell> inputHeaderCellTree = new ArrayList<TableHeaderCell>();
	public List<TableHeaderCell> inputHeaderCellList = new ArrayList<TableHeaderCell>();

	public TableHeader(List<HeaderRowConfigPO> hList) {

		initByReportConfigs(hList, null, null);

		createColRows(allHeaderCellTree, 0);

		createRowspan(allHeaderCellTree);

		initInputHeaderByAllHeaderCellTree();

	}

	/**
	 * 生成内存中的树形结构，会循环调用此方法。
	 * 
	 * @param hconfigs
	 *            表头配置列表
	 * @param currentConfig
	 *            查找此节点是否有子节点
	 * @param currentcell
	 *            向此节点中添加孩子节点
	 */
	public void initByReportConfigs(List<HeaderRowConfigPO> hList,
			HeaderRowConfigPO currentConfig, TableHeaderCell currentcell) {
		if (currentConfig != null && currentConfig.is_is_leaf()) {// 若是叶子节点 返回
			return;
		}

		int rid = 0;// 父id
		if (currentConfig != null) {
			rid = currentConfig.getBindId().intValue();
		}

		for (HeaderRowConfigPO config : hList) {
			if (config.get_parent() == rid) {
				TableHeaderCell newTableHeaderCell = new TableHeaderCell(this,
						config);
				allHeaderCellList.add(newTableHeaderCell);
				if (currentcell == null) {
					allHeaderCellTree.add(newTableHeaderCell);
				} else {
					currentcell.addChild(newTableHeaderCell);
				}
				initByReportConfigs(hList, config, newTableHeaderCell);
			}
		}
	}

	/**
	 * 生成每个节点垮行数
	 * 
	 * @param allHeaderCellTree
	 *            h
	 */
	private void createRowspan(List<TableHeaderCell> allHeaderCellTree) {
		if (allHeaderCellTree == null || allHeaderCellTree.size() == 0) {
			return;
		}
		for (TableHeaderCell childCell : allHeaderCellTree) {
			if (childCell.getParent() == null) {
				int rowSpan = headerRowNum - childCell.getColrows() + 1;
				childCell.setRowspan(rowSpan);
			} else if (childCell.getChilds() == null
					|| childCell.getChilds().size() == 0) {
				int parentRowspan = childCell.getParentRowspan(childCell
						.getParent());
				childCell.setRowspan(headerRowNum - parentRowspan);
			} else {
				childCell.setRowspan(1);
			}

			createRowspan(childCell.getChilds());
		}
	}

	/**
	 * 循环树形结构，重新设置单元格的所在列的列数，
	 * 
	 * @param allHeaderCellTree
	 * @param parentCols
	 */
	public void createColRows(List<TableHeaderCell> allHeaderCellTree,
			int parentCols) {
		if (allHeaderCellTree == null || allHeaderCellTree.size() == 0) {
			return;
		}
		for (TableHeaderCell childCell : allHeaderCellTree) {
			if (parentCols != 0) {
				childCell.setColrows(parentCols);
			}
			createColRows(childCell.getChilds(), childCell.getColrows());
		}

	}

	/**
	 * 
	 */
	public void initInputHeaderByAllHeaderCellTree() {
		initInputHeaderByAllHeaderCellTree(allHeaderCellTree, null);
	}

	/**
	 * 初始化末节点列表
	 * 
	 * @param allTreeList
	 * @param parentCell
	 */
	private void initInputHeaderByAllHeaderCellTree(
			List<TableHeaderCell> allTreeList, TableHeaderCell parentCell) {
		Iterator<TableHeaderCell> allCellTreeIterator = allTreeList.iterator();
		while (allCellTreeIterator.hasNext()) {
			TableHeaderCell cell = allCellTreeIterator.next();
			if (cell.isInputCell()) {
				TableHeaderCell newCell = new TableHeaderCell(cell);
				inputHeaderCellList.add(newCell);
				if (parentCell == null) {
					inputHeaderCellTree.add(newCell);
				} else {
					parentCell.addChild(newCell);
				}
				if (cell.getChilds() != null) {
					initInputHeaderByAllHeaderCellTree(cell.getChilds(),
							newCell);
				}
			}
		}
	}

	/**
	 * 全部表头，包括一级、二级、或者三级表头
	 * 
	 * @return
	 */
	public String getAllHeaderHtmlCode() {
		return getHtmlCodeByCellList(allHeaderCellList);
	}

	/**
	 * 只有输入项的表头
	 * 
	 * @return
	 */
	public String getInputHeaderHtmlCode() {
		return getHtmlCodeByCellList(inputHeaderCellList);
	}

	/**
	 * 
	 * @return
	 */
	public String getInputHeaderHtmlCodeNoTr() {

		Hashtable<Integer, List<TableHeaderCell>> tableHeaderRowsTable = new Hashtable<Integer, List<TableHeaderCell>>();
		Iterator<TableHeaderCell> iter = inputHeaderCellList.iterator();
		while (iter.hasNext()) {
			TableHeaderCell headerCell = iter.next();
			Integer currKey = new Integer(headerCell.getTrRowIndex());
			if (tableHeaderRowsTable.containsKey(currKey)) {
				tableHeaderRowsTable.get(currKey).add(headerCell);
			} else {
				List<TableHeaderCell> newRowList = new ArrayList<TableHeaderCell>();
				newRowList.add(headerCell);
				tableHeaderRowsTable.put(currKey, newRowList);
			}
		}

		StringBuffer sb = new StringBuffer();
		Map.Entry[] set = getSortedHashtable(tableHeaderRowsTable);
		for (int i = 0; i < set.length; i++) {
			if (i != 0) {
				sb.append("<tr class='content_header_tr' id='onlyinputhead' style='display:none;'>");
			}
			Iterator<TableHeaderCell> rowCellIter = ((List<TableHeaderCell>) set[i]
					.getValue()).iterator();
			while (rowCellIter.hasNext()) {
				TableHeaderCell headerCell = rowCellIter.next();
				sb.append(headerCell.getHeaderHtmlCode());

			}
			sb.append("</tr>");
		}
		return sb.toString();
	}

	/**
	 * 拼装表头
	 * 
	 * @param cellList
	 * @return
	 */
	private String getHtmlCodeByCellList(List<TableHeaderCell> cellList) {
		Hashtable<Integer, List<TableHeaderCell>> tableHeaderRowsTable = packageTable(cellList);

		StringBuffer sb = new StringBuffer();
		Map.Entry[] set = getSortedHashtable(tableHeaderRowsTable);
		for (int i = 0; i < set.length; i++) {
			sb.append("<tr class='" + this.headerRowCssClass + "'>");
			Iterator<TableHeaderCell> rowCellIter = ((List<TableHeaderCell>) set[i]
					.getValue()).iterator();
			while (rowCellIter.hasNext()) {
				TableHeaderCell headerCell = rowCellIter.next();
				sb.append(headerCell.getHeaderHtmlCode());

			}
			sb.append("</tr>");
		}
		return sb.toString();
	}

	/**
	 * 重新组装，为生成表头做准备
	 * 
	 * @return
	 */
	public Hashtable<Integer, List<TableHeaderCell>> packageTable(
			List<TableHeaderCell> cellList) {
		Hashtable<Integer, List<TableHeaderCell>> tableHeaderRowsTable = new Hashtable<Integer, List<TableHeaderCell>>();
		Iterator<TableHeaderCell> iter = cellList.iterator();
		while (iter.hasNext()) {
			TableHeaderCell headerCell = iter.next();
			Integer currKey = new Integer(headerCell.getTrRowIndex());
			if (tableHeaderRowsTable.containsKey(currKey)) {
				tableHeaderRowsTable.get(currKey).add(headerCell);
			} else {
				List<TableHeaderCell> newRowList = new ArrayList<TableHeaderCell>();
				newRowList.add(headerCell);
				tableHeaderRowsTable.put(currKey, newRowList);
			}
		}
		return tableHeaderRowsTable;
	}

	public String getNamPathWithParent(TableHeaderCell cell) {
		String splitchar = "/";
		String return_s = cell.getName();
		if (cell.getParent() != null) {
			return_s = splitchar + getNamPathWithParent(cell.getParent())
					+ splitchar + return_s;
		}
		return return_s;
	}

	/**
	 * 排序
	 * 
	 * @param h
	 * @return
	 */
	public Map.Entry[] getSortedHashtable(
			Hashtable<Integer, List<TableHeaderCell>> h) {
		Set set = h.entrySet();

		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set
				.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Object key1 = ((Map.Entry) arg0).getKey();
				Object key2 = ((Map.Entry) arg1).getKey();
				return ((Comparable) key1).compareTo(key2);
			}
		});
		return entries;
	}

	/**
	 * 按照配置表中的排序字段排序，字段的值类似：a#b#
	 * 
	 * @param cellList
	 * @return
	 */
	public List<TableHeaderCell> sortCell(List<TableHeaderCell> cellList) {
		List<TableHeaderCell> cells = cellList;
		Collections.sort(cells, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				String key1 = ((TableHeaderCell) arg0).getHeaderRowConfigPO()
						.getOrderIndexStr();
				String key2 = ((TableHeaderCell) arg1).getHeaderRowConfigPO()
						.getOrderIndexStr();
				int value = key1.compareTo(key2);
				return value;
			}
		});
		return cells;
	}

	/**
	 * 实际的表头跨行数需要在此值上加一
	 * 
	 * @return
	 */
	public int getHeaderRowNum() {
		return headerRowNum;
	}

	/**
	 * 表头实际跨行数
	 * 
	 * @return
	 */
	public int getHeaderRowNumReality() {
		return headerRowNum;
	}

	public void setHeaderRowNum(int headerRowNum) {
		this.headerRowNum = headerRowNum;
	}

	public int getHeaderInputWidth() {
		if (this.headerInputWidth == 0) {
			initHeaderInputWidth();
		}
		return headerInputWidth;
	}

	public int getHeaderInputHeight() {
		return headerInputHeight;
	}

	public void setHeaderInputHeight(int headerInputHeight) {
		this.headerInputHeight = headerInputHeight;
	}

	public int getHeaderAllWidth() {
		return headerAllWidth;
	}

	public void setHeaderAllWidth(int headerAllWidth) {
		this.headerAllWidth = headerAllWidth;
	}

	public int getHeaderAllHeight() {
		return headerAllHeight;
	}

	public void setHeaderAllHeight(int headerAllHeight) {
		this.headerAllHeight = headerAllHeight;
	}

	public void setHeaderInputWidth(int headerInputWidth) {
		this.headerInputWidth = headerInputWidth;
	}

	public String getHeaderRowCssClass() {
		return headerRowCssClass;
	}

	public void setHeaderRowCssClass(String headerRowCssClass) {
		this.headerRowCssClass = headerRowCssClass;
	}

	private void initHeaderInputWidth() {
		int tempWidth = 0;
		Iterator<TableHeaderCell> allCellTreeIterator = inputHeaderCellTree
				.iterator();
		while (allCellTreeIterator.hasNext()) {
			TableHeaderCell cell = allCellTreeIterator.next();
			tempWidth += cell.getWidth();
		}
		this.headerInputWidth = tempWidth;
	}

	public List<TableHeaderCell> getInputHeaderCellList() {
		return inputHeaderCellList;
	}

	public List<TableHeaderCell> getAllHeaderCellList() {
		return allHeaderCellList;
	}

}
