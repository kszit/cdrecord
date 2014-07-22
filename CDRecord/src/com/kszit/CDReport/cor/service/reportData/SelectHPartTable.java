package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.VerticalColumnService;

/**
 * 
 * 从已有报表中选择部分横向配置
 * @author Administrator
 *
 */
public class SelectHPartTable extends Table {
	//1:checkbox,2:radio
	int type;
	public SelectHPartTable(String repartLinkId,int type){
		super(repartLinkId,true);
		this.type = type;

	}
	//报表体
	@Override
	String getTableContent() {
		StringBuffer sb = new StringBuffer();
		List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();//表头
		sb.append("<tr></tr>");
		sb.append("<tr class='content_header_tr'>");
		for(int i=0,headerlength=headerCellList.size();i<headerlength;i++){//除第一列外的其他单元格
			TableHeaderCell headerCell = headerCellList.get(i);
			if(headerCell.isLeaf()){
				sb.append("<td class='seeReportTableTd'>");
				HeaderRowConfigPO po = headerCell.getHeaderRowConfigPO();
				//String checkBoxName = po.getBindId()+"#"+po.get_parent();
				String checkBoxName = po.getReportBindid()+"$"+po.getBindId()+"#0";
				
				String htmltype = "checkbox";
				if(type==1){
					htmltype = "checkbox";
				}else if(type==2){
					htmltype = "radio";
				}
				
				sb.append("<input name='tableHVCell' onclick type='"+htmltype+"' value='"+checkBoxName+"' title='"+po.getContent()+"'></input>");//输出每个单元格数据
				sb.append("</td>");
			}
		}
		sb.append("</tr>");
		return sb.toString();
	}
	
	/**
	 * 
	 */
	public String getTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader());
		sb.append(getTableContent());
		return sb.toString();
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
}

