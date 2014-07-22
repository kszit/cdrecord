package com.kszit.CDReport.cor.service.reportData;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;


public class SelectCellTable extends Table {
	/**
	 * 只输出表头
	 */
	public static final int HEADER_ONLY = 1;
	/**
	 * 只输出左侧列
	 */
	public static final int COLUMN_ONLY  = 2;
	/**
	 * 输出整个表格
	 */
	public static final int TABLE_ALL  = 3;
	
	private int outType;
	/**
	 * 带设置的横向或纵向的bindid
	 */
	private long setBindid;
	/**
	 * 已经设置的项
	 */
	private String selecedCells;
	
	public SelectCellTable(){}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * 
	 */
	public SelectCellTable(String reportBindid,long setbindid,int outType,String selectedCells){
		super(reportBindid,true);
		this.outType = outType;
		this.setBindid = setbindid;
		this.selecedCells = selectedCells;
	}
	
	public SelectCellTable(String reportBindid,String selectedCells){
		super(reportBindid,true);
		this.selecedCells = selectedCells;
	}
	
	/**
	 * 拼表体
	 * @return
	 */
	public String getTableContent(){
		
		List<TableColumnCell> columnCellList = tableColumn.getAllHeaderCellTree();
		
		VerticalColumnConfigPO vpo = new VerticalColumnConfigPO();
		vpo.setBindId(0L);
		TableColumnCell tableCCell = new TableColumnCell();
		tableCCell.setVerticalColumnConfigPO(vpo);
		tableCCell.setName("全部");
		columnCellList.add(tableCCell);
		
		
		
		return getHtmlCodeByColumnList(columnCellList,0);
	}
	
	private String getHtmlCodeByColumnList(List<TableColumnCell> columnCellList,int leve){
		StringBuffer sb = new StringBuffer();
		String leveStr = "";
		for(int i=0;i<leve;i++){
			leveStr += "---";
		}
		for(TableColumnCell column:columnCellList){
			sb.append("<tr>");
			sb.append("<td>"+leveStr+column.getName()+"</td>");
			List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();//表头
			
			for(int i=1,headerlength=headerCellList.size();i<headerlength;i++){//除第一列外的其他单元格
				TableHeaderCell headerCell = headerCellList.get(i);
				if(headerCell.isLeaf()){
					sb.append("<td>");
					String cellValue = headerCell.getHeaderRowConfigPO().getBindId()+"#"+column.getVerticalColumnConfigPO().getBindId();
					String cellName = headerCell.getName()+"#"+column.getName();
					String value = cellName+"|"+cellValue;
					sb.append("<input name='tablecell' onclick='clickCell(this);' value='"+value+"' type='checkbox'/>");
					sb.append("</td>");
				}
			}
			sb.append("<td>");
			String cellValue = "0#"+column.getVerticalColumnConfigPO().getBindId();
			String cellName = "全部#"+column.getName();
			String value = cellName+"|"+cellValue;
			sb.append("<input name='tablecell' onclick='clickCell(this);' value='"+value+"' type='checkbox'/>");
			sb.append("</td>");
			List<TableColumnCell> childs = column.getChilds();
			if(childs!=null){
				sb.append(getHtmlCodeByColumnList(childs,column.getCurrentLayer()));
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	private String getColumHtmlCodeByColumnList(List<TableColumnCell> columnCellList,int leve){
		StringBuffer sb = new StringBuffer();
		String leveStr = "";
		for(int i=0;i<leve;i++){
			leveStr += "---";
		}
		for(TableColumnCell column:columnCellList){
			sb.append("<tr>");
			sb.append("<td>"+leveStr+column.getName()+"</td>");
			
			sb.append("<td>");
			String cellValue = "0"+"#"+column.getVerticalColumnConfigPO().getBindId();
			String cellName = column.getName();
			String value = cellName+"|"+cellValue;
			sb.append("<input name='tablecell' onclick='clickCell(this);' value='"+value+"' type='checkbox'/>");
			sb.append("</td>");
			
			List<TableColumnCell> childs = column.getChilds();
			if(childs!=null){
				sb.append(getColumHtmlCodeByColumnList(childs,column.getCurrentLayer()));
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	/**
	 * 只输出横向表头，用于设置横向的数据关系
	 * @return
	 */
	public String outHeaderOnly(){
		
		HeaderRowService heaerRowSerivce = new HeaderRowService();
		String backRefBindids = heaerRowSerivce.dataFromBackRefBindids(setBindid);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(getTableHeader());//生成表头
		
		sb.append("<tr>");
		sb.append("<td>选择</td>");
		List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();//表头
		for(int i=1,headerlength=headerCellList.size();i<headerlength;i++){//除第一列外的其他单元格
			TableHeaderCell headerCell = headerCellList.get(i);
			if(headerCell.isLeaf()){
				if(headerCell.getHeaderRowConfigPO().getBindId() == setBindid){
					sb.append("<td></td>");
				}else if(backRefBindids != null && backRefBindids.contains(headerCell.getHeaderRowConfigPO().getBindId()+"")){
					sb.append("<td></td>");
				}else{
					sb.append("<td>");
					String cellValue = headerCell.getHeaderRowConfigPO().getBindId()+"#"+"0";
					String cellName = headerCell.getName();
					String value = cellName+"|"+cellValue;
					sb.append("<input name='tablecell' onclick='clickCell(this);' value='"+value+"' type='checkbox'/>");
					sb.append("</td>");

				}

			}
		}
			sb.append("</tr>");
		return sb.toString();
	}
	
	public String outColumnOnly(){
		List<TableColumnCell> columnCellList = tableColumn.getAllHeaderCellTree();
		return getColumHtmlCodeByColumnList(columnCellList,0);
	}

	public String outAllTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader());
		sb.append(getTableContent());
		return sb.toString();
	}
	
	public String getTable(){
		String returnString = "";
		switch(outType){
		case HEADER_ONLY:
			returnString = outHeaderOnly();
			break;
		case COLUMN_ONLY:
			returnString = outColumnOnly();
			break;
		case TABLE_ALL:
			returnString = outAllTable();
			break;
		}
		return returnString;
	}
}
