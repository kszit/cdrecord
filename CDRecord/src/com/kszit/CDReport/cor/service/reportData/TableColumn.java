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
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.util.Constants;


public class TableColumn {
	/**
	 * 列配置，内存缓存
	 */
	public static Map<String,TableColumn> tableColumMap = new HashMap<String,TableColumn>();
	/**
	 * 根据reportBindid和verson 获得列配置信息
	 * @param reportbindid
	 * @return
	 */
	public static TableColumn getTableColumn(String reportbindid){
		
		
		if(Constants.IS_DEVELEP){
			VerticalColumnService vService = new VerticalColumnService();
			List<VerticalColumnConfigPO> vList = vService.getVerticalColumnCByReportOfPO(reportbindid);
			TableColumn tableColumn = new TableColumn(vList);
			tableColumMap.put(reportbindid, tableColumn);
			return tableColumn;
		}else{
			if(tableColumMap.containsKey(reportbindid)){
				return tableColumMap.get(reportbindid);
			}else{
				VerticalColumnService vService = new VerticalColumnService();
				List<VerticalColumnConfigPO> vList = vService.getVerticalColumnCByReportOfPO(reportbindid);
				TableColumn tableColumn = new TableColumn(vList);
				tableColumMap.put(reportbindid, tableColumn);
				return tableColumn;
			}
		}
	}
	/**
	 * 获得 报表配置行
	 * @param reportbindid 
	 * @param bindid 报表行bindid
	 * @return
	 */
	public static TableColumnCell getOneColumn(String reportbindid,String bindid){
		TableColumn tableColums = getTableColumn(reportbindid);
		for(TableColumnCell colum:tableColums.allHeaderCellList){
			String columnBindid = colum.getVerticalColumnConfigPO().getBindId()+"";
			if(columnBindid.equals(bindid)){
				return colum;
			}
		}
		return null;
	}
	
	/**
	 * 树形结构的总层数
	 */
	private int headerRowNum;
	
	private String headerRowCssClass = "content_header_tr";
	
	private int headerInputWidth;
	
	private int headerInputHeight;
	
	private int headerAllWidth;
	
	private int headerAllHeight;
	
	private List<TableColumnCell> allHeaderCellTree = new ArrayList<TableColumnCell>();
	private List<TableColumnCell> allHeaderCellList = new ArrayList<TableColumnCell>();
	
	private List<TableColumnCell> inputHeaderCellTree = new ArrayList<TableColumnCell>();
	private List<TableColumnCell> inputHeaderCellList = new ArrayList<TableColumnCell>();
	
	public TableColumn(List<VerticalColumnConfigPO> hList){
		
		initByReportConfigs(hList,null,null);
		
		//initInputHeaderByReportConfigs(hconfig,null,null);
		initInputHeaderByAllHeaderCellTree();
		//System.out.println("<table border=1>"+getInputHeaderHtmlCode()+"</table>");
		//System.out.println(allHeaderCellTree.size());
	}
	
	/**
	 * 
	 * @param hconfigs 
	 * @param currentConfig 
	 * @param currentcell  
	 */
	public void initByReportConfigs(List<VerticalColumnConfigPO> hList,VerticalColumnConfigPO currentConfig,TableColumnCell currentcell){
		if(currentConfig!=null && currentConfig.is_is_leaf()){
			return;
		}
		
		int rid = 0;
		if(currentConfig!=null){
			rid = currentConfig.getBindId().intValue();
		}
		
		for(VerticalColumnConfigPO config:hList){
			if(config.get_parent() == rid){
				TableColumnCell newTableColumnCell = new TableColumnCell(this,config);
				allHeaderCellList.add(newTableColumnCell);
				if(currentcell==null){
					allHeaderCellTree.add(newTableColumnCell);
				}else{
					currentcell.addChild(newTableColumnCell);
				}
				initByReportConfigs(hList,config,newTableColumnCell);
			}
		}
	}
	
	/**
	 * 
	 */
	public void initInputHeaderByAllHeaderCellTree(){
		initInputHeaderByAllHeaderCellTree(allHeaderCellTree,null);
	}
	
	/**
	 * 
	 * @param allTreeList
	 * @param parentCell
	 */
	private void initInputHeaderByAllHeaderCellTree(List<TableColumnCell> allTreeList,TableColumnCell parentCell){
		Iterator<TableColumnCell> allCellTreeIterator = allTreeList.iterator();
		while(allCellTreeIterator.hasNext()){
			TableColumnCell cell = allCellTreeIterator.next();
			if(cell.isInputCell()){
				TableColumnCell newCell = new TableColumnCell(cell);
				inputHeaderCellList.add(newCell);
				if(parentCell == null){
					inputHeaderCellTree.add(newCell);
				}else{
					parentCell.addChild(newCell);
				}
				if(cell.getChilds() != null){
					initInputHeaderByAllHeaderCellTree(cell.getChilds(),newCell);
				}
			}
		}
	}
	
	public String getNamPathWithParent(TableColumnCell cell){
		String splitchar = "/";
		String return_s = cell.getName();
		if(cell.getParent()!=null){
			return_s = splitchar + getNamPathWithParent(cell.getParent())+splitchar+return_s;
		}
		return return_s;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAllHeaderHtmlCode(){
		return getHtmlCodeByCellList(allHeaderCellList);
	}
	
	/**
//	 * 
	 * @return
	 */
	public String getInputHeaderHtmlCode(){
		return getHtmlCodeByCellList(inputHeaderCellList);
	}
	
	
	/**
//	 * 
	 * @return
	 */
	public String getInputHeaderHtmlCodeNoTr(){
		
		Hashtable<Integer, List<TableColumnCell>> tableHeaderRowsTable = new Hashtable<Integer, List<TableColumnCell>>();
		Iterator<TableColumnCell> iter = inputHeaderCellList.iterator();
		while(iter.hasNext()){
			TableColumnCell headerCell = iter.next();
			Integer currKey = new Integer(headerCell.getTrRowIndex());
			if(tableHeaderRowsTable.containsKey(currKey)){
				tableHeaderRowsTable.get(currKey).add(headerCell);
			}else{
				List<TableColumnCell> newRowList = new ArrayList<TableColumnCell>();
				newRowList.add(headerCell);
				tableHeaderRowsTable.put(currKey, newRowList);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		Map.Entry[] set = getSortedHashtable(tableHeaderRowsTable);
		for (int i = 0; i < set.length; i++) {
			if(i!=0){
				sb.append("<tr class='content_header_tr' id='onlyinputhead' style='display:none;'>");
			}
			Iterator<TableColumnCell> rowCellIter = ((List<TableColumnCell>)set[i].getValue()).iterator();
			while(rowCellIter.hasNext()){
				TableColumnCell headerCell = rowCellIter.next();
				sb.append(headerCell.getHtmlCode());

			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	/**
	 * 
	* changeColumnName( 修改列名称为 包括父子项路径的名称 ) <br> 
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
	public void changeColumnName(){
		changeColumnName(allHeaderCellTree,"");
	}
	
	private void changeColumnName(List<TableColumnCell> list,String name){
		if(list==null || list.size()==0){
			return;
		}
		Iterator<TableColumnCell> colIter = list.iterator();
		while(colIter.hasNext()){
			TableColumnCell cCell = colIter.next();
			cCell.setName(name+cCell.getName()+"/");
			if(cCell.getChilds()!=null && cCell.getChilds().size()!=0){
				changeColumnName(cCell.getChilds(),cCell.getName());
			}
		}
		
	}
	
	public void ridRelationship(){
		Iterator<TableColumnCell> colIter = allHeaderCellList.iterator();
		while(colIter.hasNext()){
			TableColumnCell cCell = colIter.next();
			cCell.setParent(null);
			cCell.setChilds(null);
		}
	}
	/**
	 * 
	 * @param cellList
	 * @return
	 */
	private String getHtmlCodeByCellList(List<TableColumnCell> cellList){
		Hashtable<Integer, List<TableColumnCell>> tableHeaderRowsTable = new Hashtable<Integer, List<TableColumnCell>>();
		Iterator<TableColumnCell> iter = cellList.iterator();
		while(iter.hasNext()){
			TableColumnCell headerCell = iter.next();
			Integer currKey = new Integer(headerCell.getTrRowIndex());
			if(tableHeaderRowsTable.containsKey(currKey)){
				tableHeaderRowsTable.get(currKey).add(headerCell);
			}else{
				List<TableColumnCell> newRowList = new ArrayList<TableColumnCell>();
				newRowList.add(headerCell);
				tableHeaderRowsTable.put(currKey, newRowList);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		Map.Entry[] set = getSortedHashtable(tableHeaderRowsTable);
		for (int i = 0; i < set.length; i++) {
			sb.append("<tr class='"+this.headerRowCssClass+"'>");
			Iterator<TableColumnCell> rowCellIter = ((List<TableColumnCell>)set[i].getValue()).iterator();
			while(rowCellIter.hasNext()){
				TableColumnCell headerCell = rowCellIter.next();
				sb.append(headerCell.getHtmlCode());

			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	/**
	 * 
	* removeFromAllHeaderCellList(这里用一句话描述这个方法的作用) <br> 
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
	public void removeFromAllHeaderCellList(String colunBindid){
		List<TableColumnCell> list = allHeaderCellList;
		if(list==null || list.size()==0){
			return;
		}
		Iterator<TableColumnCell> colunIter = list.iterator();
		while(colunIter.hasNext()){
			TableColumnCell colunCell = colunIter.next();
			if(colunBindid.equals(colunCell.getVerticalColumnConfigPO().getBindId()+"")){
				colunIter.remove();
			}
		}
	}
	
	/**
	 * 
	* removeFromAllHeaderCellTree(  从allHeaderCellTree中删除指定bindid的数据，移除前会根据参数判定是否将孩子节点放list中，会循环到孩子节点) <br> 
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
	public void removeFromAllHeaderCellTree(String colunBindid){
		removeFromAllHeaderCellTree(this.allHeaderCellTree,colunBindid);
	}
	
	/**
	 * 
	* removeFromAllHeaderCellTree(递归调用此方法，从数型结构中删除指定bindid的行，移除前会根据参数判定是否将孩子节点放list中) <br> 
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
	public void removeFromAllHeaderCellTree(List<TableColumnCell> list,String colunBindid){
		if(list==null || list.size()==0){
			return;
		}
		Iterator<TableColumnCell> colunIter = list.iterator();
		while(colunIter.hasNext()){
			TableColumnCell colunCell = colunIter.next();
			if(colunBindid.equals(colunCell.getVerticalColumnConfigPO().getBindId()+"")){
				colunIter.remove();
			}
			if(colunCell.getChilds()!=null && colunCell.getChilds().size()!=0){
				removeFromAllHeaderCellTree(colunCell.getChilds(),colunBindid);
			}
		}
	}
	
	/**
	 * 
	 * @param h
	 * @return
	 */
	public Map.Entry[] getSortedHashtable(Hashtable<Integer, List<TableColumnCell>> h){
		Set set = h.entrySet();

		Map.Entry[] entries = (Map.Entry[])set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries,new Comparator(){
			public int compare(Object arg0, Object arg1){
				Object key1 = ((Map.Entry)arg0).getKey(); 
				Object key2 = ((Map.Entry)arg1).getKey();
				return ((Comparable)key1).compareTo(key2); 
			}

		});
		return entries; 
	}
	/**
//	 * 
	 * @return
	 */
	public int getHeaderRowNum() {
		return headerRowNum;
	}

	public void setHeaderRowNum(int headerRowNum) {
		this.headerRowNum = headerRowNum;
	}

	public int getHeaderInputWidth() {
		if(this.headerInputWidth == 0){
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

	private void initHeaderInputWidth(){
		int tempWidth = 0;
		Iterator<TableColumnCell> allCellTreeIterator = inputHeaderCellTree.iterator();
		while(allCellTreeIterator.hasNext()){
			TableColumnCell cell = allCellTreeIterator.next();
			tempWidth += cell.getWidth();
		}
		this.headerInputWidth = tempWidth;
	}

	public List<TableColumnCell> getAllHeaderCellTree() {
		return allHeaderCellTree;
	}

	public void setAllHeaderCellTree(List<TableColumnCell> allHeaderCellTree) {
		this.allHeaderCellTree = allHeaderCellTree;
	}

	public List<TableColumnCell> getAllHeaderCellList() {
		return allHeaderCellList;
	}
	
	
}
