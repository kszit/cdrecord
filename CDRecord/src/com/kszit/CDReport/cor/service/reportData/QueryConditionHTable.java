package com.kszit.CDReport.cor.service.reportData;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 
 * 从已有报表中选择部分横向配置
 * @author Administrator
 *
 */
public class QueryConditionHTable extends Table {
	//1:checkbox,2:radio
	int type;
	public QueryConditionHTable(String repartLinkId){
		super(repartLinkId,true);
		this.type = 1;

	}
	
	
	/**
	 * 
	 */
	public String getTable(){
		return getTableContent();
	}
	
	/**
	 * 设置选中的单元格的父节点也被选中，
	 * 
	 * @param selectedCell
	 */
	public void setCellParentSelected(TableHeaderCell selectedCell){
		TableHeaderCell selectedCellParent = selectedCell.getParent();
		while(selectedCellParent!=null){
			selectedCellParent.setSelected(true);
			selectedCellParent = selectedCellParent.getParent();
		}
	}
	
	
	@Override
	String getTableContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table align='center' class='seeReportTable'>");
		List<TableHeaderCell> columnCellList = tableHeader.allHeaderCellList;
		sb.append(getHtmlCodeByColumnList(columnCellList));
		sb.append("</table>");
		return sb.toString();
	}

	
	/**
	 * 递归调用此方法，生成树形的行数据
	 * 
	 * @param columnCellList
	 * @param leve
	 * @return
	 */
	public String getHtmlCodeByColumnList(List<TableHeaderCell> columnCellList) {
		StringBuffer sb = new StringBuffer();
		
		
		boolean isFirst = true;
		for (TableHeaderCell column : columnCellList) {
			
			if(isFirst){
				isFirst = false;
				continue;
			}
			if(!column.isLeaf()){
				continue;
			}
			sb.append("<tr class='content_header_tr'>");
			HeaderRowConfigPO headerPo = column.getHeaderRowConfigPO();
			String colName = tableHeader.getNamPathWithParent(column);;
			
			sb.append("<td class='seeReportTableTd'>");
			sb.append(colName);
			sb.append("</td>");
			sb.append("<td class='seeReportTableTd'>");
			
			
			
			int dataType = headerPo.getDataType();
			if(StaticVaribles.DataType_DateBindId==dataType){//日期
				
				sb.append("大于：<input type='text' name='greater"+headerPo.getBindId()+"'></input>");
				sb.append("小于：<input type='text' name='less"+headerPo.getBindId()+"'></input>");
			}else if(StaticVaribles.DataType_NumberBindId==dataType){//数值
				sb.append("<input type='hidden' id='greater"+headerPo.getBindId()+"' value='"+colName+"大于：'></input>");
				sb.append("大于：<input type='text' name='greater"+headerPo.getBindId()+"'></input>");
				
				sb.append("<input type='hidden' id='less"+headerPo.getBindId()+"' value='"+colName+"小于：'></input>");
				sb.append("小于：<input type='text' name='less"+headerPo.getBindId()+"'></input>");
			}else if(StaticVaribles.DataType_TextBindId==dataType){//文本
				sb.append("包含：<input type='text' name='like"+headerPo.getBindId()+"'></input>");
			} 
			
			
			sb.append("</td>");

			sb.append("</tr>");
		}

		return sb.toString();
	}

}

