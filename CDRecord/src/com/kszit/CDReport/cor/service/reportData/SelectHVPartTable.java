package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.VerticalColumnService;

/**
 * 上级从下级已有报表中选择部分报表指标 形成上级的汇总报表的一部分；
 * 下级的报表的横向和纵向组合成一个表头,
 * 目前，此类只支持纵向配置作为表头，横行配置的子节点作为新表头的叶子节点
 * @author Administrator
 *
 */
public class SelectHVPartTable extends Table {

	
	public SelectHVPartTable(String repartLinkId){
		super.reportBindid = repartLinkId;
		
		VerticalColumnService vService = new VerticalColumnService();
		List<VerticalColumnConfigPO> vList = vService.getVerticalColumnCByReportOfPO(reportBindid);
		HeaderRowService hService = new HeaderRowService();
		List<HeaderRowConfigPO> hList = hService.getHeaderRowCByReportOfPO(reportBindid);
		
		//所有纵向配置和横向配置的叶子节点 生成新的报表顶部树形头部的数据源
		List<HeaderRowConfigPO> hvList = new ArrayList<HeaderRowConfigPO>();
		for(VerticalColumnConfigPO vPo:vList){//循环纵向配置
			HeaderRowConfigPO hPo = new HeaderRowConfigPO(vPo);
			hvList.add(hPo);
				for(HeaderRowConfigPO oldHPo:hList){
					HeaderRowConfigPO newHRConifgPo = new HeaderRowConfigPO(oldHPo);
					hPo.set_is_leaf(false);
					newHRConifgPo.set_parent(hPo.getBindId().intValue());
					hvList.add(newHRConifgPo);
				}
			
		}

		this.tableHeader = new TableHeader(hvList);
		
		this.tableColumn = TableColumn.getTableColumn(reportBindid);
	}
	//报表体
	@Override
	String getTableContent() {
		StringBuffer sb = new StringBuffer();
		List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();//表头
		sb.append("<tr></tr>");
		sb.append("<tr class='content_header_tr'>");
		for(int i=1,headerlength=headerCellList.size();i<headerlength;i++){//除第一列外的其他单元格
			TableHeaderCell headerCell = headerCellList.get(i);
			if(headerCell.isLeaf()){
				sb.append("<td class='seeReportTableTd'>");
				HeaderRowConfigPO po = headerCell.getHeaderRowConfigPO();
				String checkBoxName = po.getBindId()+"#"+po.get_parent();
				sb.append("<input name='tableHVCell' type='checkbox' value='"+checkBoxName+"' />");//输出每个单元格数据
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

