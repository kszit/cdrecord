package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;


public class TableColumnCell {
	
	private String headerNo;
	
	private String headerOrder;
	
	private String name;
	
	private String cssClassName = "content_header_td";
	
	/**
	 * 输出单元格的宽度，包括跨列的情况
	 */
	private int width;
	
	/**
	 * 配置中单元格的宽度
	 */
	private int configWidth;
	
	private int height;
	
	/**
	 * 
	 */
	private int colspan;
	
	/**
	 * 
	 */
	private int rowspan = 0;
	/**
	 * 
	 */
	private int trRowIndex = 0;
	/**
	 * 
	 */
	private int colrows = 1;
	/**
	 * 
	 */
	private int currentLayer = 1;
	
	private TableColumnCell parent;
	
	private List<TableColumnCell> childs;
	
	private TableColumn tableColumn;
	
	private boolean isLeaf;
	
	private boolean isInputCell;

	private VerticalColumnConfigPO verticalColumnConfigPO = null;
	
	public TableColumnCell(){}
	
	public TableColumnCell(TableColumnCell copyCell){
		this.tableColumn = copyCell.tableColumn;
		this.setHeaderNo(copyCell.getHeaderNo());
		this.setHeaderOrder(copyCell.getHeaderOrder());
		this.setName(copyCell.getName());
		this.setWidth(copyCell.isLeaf,copyCell.getConfigWidth());
		this.setLeaf(copyCell.isLeaf);
		this.setInputCell(copyCell.isInputCell);
	}
	
	public TableColumnCell(TableColumn tableColumn,VerticalColumnConfigPO config){
		this.verticalColumnConfigPO = config;
		this.tableColumn = tableColumn;
		this.setHeaderNo("");
		this.setHeaderOrder(config.getOrderIndexStr());
		this.setName(config.getContent());
		this.setWidth(config.is_is_leaf(),config.getWidth());
		this.setConfigWidth(config.getWidth());
		this.setLeaf(config.is_is_leaf());
		this.setInputCell(true);
	}
	
	public String getHtmlCode(){
		StringBuffer sb = new StringBuffer();
		sb.append("<td class='"+this.getCssClassName()+"' width='"+this.getWidth()+"' colSpan='"+this.getColspan()+"' rowSpan='"+this.getRowspan()+"'>");
		sb.append(this.name);
		sb.append("</td>");
		return sb.toString();
	}
	
	public String getHeaderNo() {
		return headerNo;
	}

	public void setHeaderNo(String headerNo) {
		this.headerNo = headerNo;
	}

	public String getHeaderOrder() {
		return headerOrder;
	}

	public void setHeaderOrder(String headerOrder) {
		this.headerOrder = headerOrder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClassName() {
		return cssClassName;
	}

	public void setCssClassName(String cssClassName) {
		this.cssClassName = cssClassName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setWidth(boolean isleaf,int width) {
		if(isleaf){
			this.width = width;
		}
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColspan() {
		if(colspan==0){
			if(this.childs == null){
				colspan = 1;
			}else{
				colspan = childnum(this.childs);
			}
		}
		
		return colspan;
	}
	
	private int childnum(List<TableColumnCell> child){
		int allchildNum = 0;
		List<TableColumnCell> grandsonList = new ArrayList<TableColumnCell>();
		Iterator<TableColumnCell> iter = child.iterator();
		while(iter.hasNext()){
			TableColumnCell childcell = iter.next();
			if(childcell.childs!=null){
				grandsonList.addAll(childcell.childs);
			}
			if(childcell.isLeaf){
				allchildNum++;
			}
		}
		if(grandsonList.size()!=0){
			allchildNum += childnum(grandsonList);
		}
		return allchildNum;
	}
	
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	/**
	 * ��Ԫ�������
	 * @return
	 */
	public int getRowspan() {
		if(rowspan==0){
			if(this.parent==null){
				return this.tableColumn.getHeaderRowNum()-this.colrows+1;
			}else if(this.childs==null){
				if(this.colrows==this.currentLayer){
					return 1;
				}else{
					return this.tableColumn.getHeaderRowNum()-(this.colrows-this.currentLayer);
				}
			}else{
				return 1;
			}
		}
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public TableColumnCell getParent() {
		return parent;
	}

	public void setParent(TableColumnCell parent) {
		this.parent = parent;
	}

	public List<TableColumnCell> getChilds() {
		return childs;
	}

	public void setChilds(List<TableColumnCell> childs) {
		this.childs = childs;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	

	
	public int getCurrentLayer() {
		return currentLayer;
	}

	public void setCurrentLayer(int currentLayer) {
		this.currentLayer = currentLayer;
	}
	
	public int getColrows() {
		return colrows;
	}

	public void setColrows(int colrows) {
		this.colrows = colrows;
	}
	public void setColrows(boolean isSetParent,int colrows) {

		this.colrows = colrows;
		if(isSetParent && this.parent!=null && this.parent.getColrows()<this.colrows){
			this.parent.setColrows(isSetParent,colrows);
		}
	}
	public void addColrow(){
		if(this.colrows<this.currentLayer+1){
			this.setColrows(true,this.colrows+1);
			if(this.colrows>this.tableColumn.getHeaderRowNum()){
				this.tableColumn.setHeaderRowNum(this.colrows);
			}
		}
	}
	
	public void addChild(TableColumnCell childheadercell){
		childheadercell.setParent(this);
		childheadercell.setCurrentLayer(this.getCurrentLayer()+1);
		
		if(this.childs == null){
			this.childs = new ArrayList<TableColumnCell>();
			this.addColrow();
		}
		
		childheadercell.setColrows(this.colrows);
		
		this.childs.add(childheadercell);
		this.addWidth(childheadercell.getWidth());
		
		if(childheadercell.isInputCell){
			childheadercell.setParentInputCellTrue();
		}
		
	}
	
	public void addWidth(int addwidth){
		this.width += addwidth;
		if(this.parent != null){
			this.parent.addWidth(addwidth);
		}
	}

	public int getTrRowIndex() {
		if(trRowIndex==0){
			trRowIndex = this.getCurrentLayer();
			//System.out.println("ddd:"+)
			if(this.isLeaf() && trRowIndex!=1){
				//trRowIndex = tableHeader.getHeaderRowNum();
				trRowIndex = tableColumn.getHeaderRowNum();
				if(this.getRowspan()>1){
					trRowIndex -= (this.getRowspan()-1);
				}
			}
		}
		return trRowIndex;
	}

	public void setTrRowIndex(int trRowIndex) {
		this.trRowIndex = trRowIndex;
	}

	public boolean isInputCell() {
		return isInputCell;
	}

	public void setInputCell(boolean isInputCell) {
		this.isInputCell = isInputCell;
	}
	
	public void setParentInputCellTrue() {
		this.isInputCell = true;
		if(this.parent != null){
			this.parent.setParentInputCellTrue();
		}
	}

	public int getConfigWidth() {
		return configWidth;
	}

	public void setConfigWidth(int configWidth) {
		this.configWidth = configWidth;
	}

	public VerticalColumnConfigPO getVerticalColumnConfigPO() {
		return verticalColumnConfigPO;
	}

	public void setVerticalColumnConfigPO(
			VerticalColumnConfigPO verticalColumnConfigPO) {
		this.verticalColumnConfigPO = verticalColumnConfigPO;
	}

	@Override
	public boolean equals(Object obj) {
		TableColumnCell o = (TableColumnCell)obj;
		return this.verticalColumnConfigPO.getBindId().longValue()==o.verticalColumnConfigPO.getBindId().longValue();
		//return o.getName().equals(o.getName());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.verticalColumnConfigPO.getBindId().hashCode();
	}
	
	
	
	
}
