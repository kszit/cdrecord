package com.kszit.CDReport.cor.service.reportData;

import com.kszit.CDReport.cor.service.reportData.html.HtmlCodeUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.StaticVaribles;


public class TableHeaderCell {
	
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
	 * 跨列数
	 */
	private int colspan;
	
	/**
	 * 跨行数
	 */
	private int rowspan = 0;
	/**
	 * 输出表格时，在第几行输出
	 */
	private int trRowIndex = 0;
	/**
	 * 列总层数
	 */
	private int colrows = 1;
	/**
	 * 当前格所在层
	 */
	private int currentLayer = 1;
        /**
         * 在excel表格中表头的第几行输出
         */
        private int excelCurrentCol = 0;
        
	/**
	 * 父节点
	 */
	private TableHeaderCell parent;
	/**
	 * 孩子节点
	 */
	private List<TableHeaderCell> childs;
	
	private TableHeader tableHeader;
	
	private boolean isLeaf;
	
	private boolean isInputCell;
	//部分复制单元格时，此单元格是否被选中
	private boolean isSelected;

	private HeaderRowConfigPO headerRowConfigPO = null;
	
	public TableHeaderCell(){}
	
	public TableHeaderCell(TableHeaderCell copyCell){
		this.tableHeader = copyCell.tableHeader;
		this.setHeaderNo(copyCell.getHeaderNo());
		this.setHeaderOrder(copyCell.getHeaderOrder());
		this.setName(copyCell.getName());
		this.setWidth(copyCell.isLeaf,copyCell.getConfigWidth());
		this.setLeaf(copyCell.isLeaf);
		this.setInputCell(copyCell.isInputCell);
		this.setHeaderRowConfigPO(copyCell.getHeaderRowConfigPO());
	}
	
	public TableHeaderCell(TableHeader tableHeader,HeaderRowConfigPO config){
		this.headerRowConfigPO = config;
		this.tableHeader = tableHeader;
		this.setHeaderNo("");
		this.setHeaderOrder(config.getOrderIndexStr());
		this.setName(config.getContent());
		this.setWidth(config.is_is_leaf(),config.getWidth());
		this.setConfigWidth(config.getWidth());
		this.setLeaf(config.is_is_leaf());
		this.setInputCell(true);
	}

	/**
	 * 输入框的html代码
	 * @return
	 */
	public String getInputHTMLCode(TableColumnCell column,String dataValue,HVConfigDataFromPO cellDataFrom,HVConfigUIModelPO cellUIModel,DepartmentToReport dept){
		StringBuffer sb = new StringBuffer();
		String inputHtmlCode = new HtmlCodeUtil(headerRowConfigPO,column.getVerticalColumnConfigPO(),cellDataFrom,cellUIModel,dept).getInputHtmlCode(true, dataValue);
		sb.append("<td class='seeReportTableTd' width='"+headerRowConfigPO.getWidth()+"'  style='text-align:right;'>"+inputHtmlCode+"</td>");
		return sb.toString();
	}
	/**
	 * 输出表格头部 table 表格
	 * @return
	 */
	public String getHeaderHtmlCode(){
		StringBuffer sb = new StringBuffer();
		sb.append("<td nowrap class='"+this.getCssClassName()+"' width='"+this.getWidth()+"' colSpan='"+this.getColspan()+"' rowSpan='"+this.getRowspan()+"'>");
		sb.append(this.name);
		sb.append("</td>");
		return sb.toString();
	}
	
	/**
	 * 输出表格头部 grid表格  填报表
	 * @return
	 */
	public String getHeaderHtmlCodeOfGrid(){
		HeaderRowConfigPO headerConfigPo = this.getHeaderRowConfigPO();
		String editor = "";
		if(headerConfigPo.getUIModule()!=StaticVaribles.HTMLUIModule_NullBindid){
			if(headerConfigPo.getDataType()==StaticVaribles.DataType_TextBindId){
				editor = ",editor:'text'";
			}else if(headerConfigPo.getDataType()==StaticVaribles.DataType_NumberBindId){
				editor = ",editor:'numberbox'";
			}else if(headerConfigPo.getDataType()==StaticVaribles.DataType_DateBindId){
				editor = ",editor:'datebox'";
			}
		}
		StringBuffer sb = new StringBuffer();//
		sb.append("<th data-options=\"field:'"+headerConfigPo.getBindId()+"',width:"+this.getWidth()+editor+"\" colSpan='"+this.getColspan()+"' rowSpan='"+this.getRowspan()+ "' >");
		sb.append(this.name);
		sb.append("</th>");
		return sb.toString();
	}
	
	/**
	 * 输出表格头部 grid表格  查看表
	 * @return
	 */
	public String getHeaderHtmlCodeOfGridNoEdit(){
		HeaderRowConfigPO headerConfigPo = this.getHeaderRowConfigPO();
		String editor = "";
		StringBuffer sb = new StringBuffer();//
		sb.append("<th data-options=\"field:'"+headerConfigPo.getBindId()+"',width:"+this.getWidth()+editor+"\" colSpan='"+this.getColspan()+"' rowSpan='"+this.getRowspan()+ "' >");
		sb.append(this.name);
		sb.append("</th>");
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
	
	private int childnum(List<TableHeaderCell> child){
		int allchildNum = 0;
		List<TableHeaderCell> grandsonList = new ArrayList<TableHeaderCell>();
		Iterator<TableHeaderCell> iter = child.iterator();
		while(iter.hasNext()){
			TableHeaderCell childcell = iter.next();
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
	 * 单元格垮层数
	 * @return
	 */
	public int getRowspan() {
            /*
                int outIndex = rowspan;
		if(rowspan==0){
			if(this.parent==null){//根节点
				outIndex = this.tableHeader.getHeaderRowNum()-this.colrows+1;
			}else if(this.childs==null || this.childs.size()==0){//叶子节点
				if(this.colrows==this.currentLayer){//单元格所在列的总列数      当前单元格的所在层
					outIndex = 1;
				}else{
					outIndex = this.tableHeader.getHeaderRowNum()-(this.colrows-this.currentLayer);
				}
			}else{
                            //outIndex = this.tableHeader.getHeaderRowNum()-(this.colrows-this.currentLayer);
				outIndex = 1;
			}
		}
                
		return outIndex;
                */
		if(this.rowspan==0){
			return 1;
		}else{
			return this.rowspan;
		}
	}

        /**
         * 计算父节点和祖父节点垮列数
         * @param cell
         * @return 
         */
        public int getParentRowspan(TableHeaderCell cell){
            int parentRowspan = 0;
            if(cell==null){
                return parentRowspan;
            }else{
                parentRowspan = cell.getRowspan()+getParentRowspan(cell.getParent());
            }
            return   parentRowspan;
            
            
            
        }
        
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public TableHeaderCell getParent() {
		return parent;
	}

	public void setParent(TableHeaderCell parent) {
		this.parent = parent;
	}

	public List<TableHeaderCell> getChilds() {
		return childs;
	}

	public void setChilds(List<TableHeaderCell> childs) {
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
        
        /**
         * 
         * @param isSetParent
         * @param colrows 
         */
	public void setParentColrows(boolean isSetParent,int colrows) {

		this.colrows = colrows;
		if(isSetParent && this.parent!=null && this.parent.getColrows()<this.colrows){
			this.parent.setParentColrows(isSetParent,colrows);
		}
	}
        
        /**
         * 添加列的总层数
         */
	public void addColrow(){
		if(this.colrows<=this.currentLayer+1){
			this.setParentColrows(true,this.colrows+1);
			if(this.colrows>this.tableHeader.getHeaderRowNum()){
				this.tableHeader.setHeaderRowNum(this.colrows);//设置表头的总行数
			}
		}
                
	}
	
	public void addChild(TableHeaderCell childheadercell){
		childheadercell.setParent(this);
		childheadercell.setCurrentLayer(this.getCurrentLayer()+1);//设置子节点所在层
		
		if(this.childs == null){
			this.childs = new ArrayList<TableHeaderCell>();
			this.addColrow();
		}
		
		childheadercell.setColrows(this.colrows);
		
		this.childs.add(childheadercell);//父节点添加子节点
		this.addWidth(childheadercell.getWidth());//增加父节点的宽度
		
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

        /**
         * 在第几行输出此单元格
         * @return 
         */
	public int getTrRowIndex() {
		if(trRowIndex==0){
                    /*
                        trRowIndex = this.getCurrentLayer();//与所在层数相同，并减去父节点
			
			//System.out.println("ddd:"+)
			if(this.isLeaf() && trRowIndex!=1){
				//trRowIndex = tableHeader.getHeaderRowNum();
				trRowIndex = tableHeader.getHeaderRowNum();//表头的总层数
				if(this.getRowspan()>1){//跨行数大于一
					trRowIndex -= (this.getRowspan()-1);
				}
			}
                        */
                    trRowIndex = this.getCurrentLayer()+allParentRowspan(this.parent);
                        
		}
		return trRowIndex;
	}
        
        /**
         * 父节点及祖父节点的跨行数
         * @param cell
         * @return 
         */
        public int allParentRowspan(TableHeaderCell cell){
            int allRowspan = 0;
            if(cell!=null){
                allRowspan += (cell.getRowspan()-1)+cell.allParentRowspan(cell.parent);
            }
            return allRowspan;
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

	public HeaderRowConfigPO getHeaderRowConfigPO() {
		return headerRowConfigPO;
	}

	public void setHeaderRowConfigPO(HeaderRowConfigPO headerRowConfigPO) {
		this.headerRowConfigPO = headerRowConfigPO;
	}

    public int getExcelCurrentCol() {
        return excelCurrentCol;
    }

    public void setExcelCurrentCol(int excelCurrentCol) {
        this.excelCurrentCol = excelCurrentCol;
    }

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	@Override
	public boolean equals(Object obj) {
		TableHeaderCell o = (TableHeaderCell)obj;
		return this.headerRowConfigPO.getBindId().longValue()==o.headerRowConfigPO.getBindId().longValue();
		//return o.getName().equals(o.getName());
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.headerRowConfigPO.getBindId().hashCode();
	}
	

	
}
